package kamal.saqib.codepair;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class Codechef_todo_list extends AppCompatActivity {

    SharedPreferences shared;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codechef_todo_list);
        listView=(ListView) findViewById(R.id.listview);

        shared=this.getSharedPreferences("kamal.saqib.codepair", Context.MODE_PRIVATE);
        final Set<String> s= shared.getStringSet("codechef",null);
        final ArrayList<String> array=new ArrayList<String>();
        if(s!=null) {
            array.addAll(s);
            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String x=array.get(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.codechef.com/problems/"+x));
                    startActivity(browserIntent);
                }
            });


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    new android.support.v7.app.AlertDialog.Builder(Codechef_todo_list.this).
                            setTitle("Remove").
                            setMessage("Do you want to remove this to your todo list").
                            setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    array.remove(position);
                                    s.clear();
                                    s.addAll(array);
                                    shared.edit().remove("codechef").apply();
                                    shared.edit().putStringSet("codechef", s).apply();
                                    ArrayAdapter adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
                                    listView.setAdapter(adapter1);

                                }
                            }).
                            setNegativeButton("No", null).show();
                    return true;
                }
            });
        }

    }
}
