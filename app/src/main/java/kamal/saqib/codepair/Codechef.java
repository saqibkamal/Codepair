package kamal.saqib.codepair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Codechef extends AppCompatActivity implements View.OnClickListener {

    Button compare;
    EditText user1, user2;
    String username1, username2, user1detail, user2detail;
    ArrayList<String> user1question, user2question, notdonequestion;
    ArrayAdapter<String> adapter;
    TextView message;
    ListView list;
    ImageView swap;
    SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codechef);

        shared = this.getSharedPreferences("kamal.saqib.codepair", Context.MODE_PRIVATE);


        user1 = (EditText) findViewById(R.id.user1);
        user2 = (EditText) findViewById(R.id.user2);
        compare = (Button) findViewById(R.id.compare);
        message = (TextView) findViewById(R.id.message);
        list = (ListView) findViewById(R.id.list);
        swap = (ImageView) findViewById(R.id.exchange);

        user1question = new ArrayList<>();
        user2question = new ArrayList<>();
        notdonequestion = new ArrayList<>();

        compare.setOnClickListener(this);
        swap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == compare) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Comparing ....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            username1 = user1.getText().toString();
            username2 = user2.getText().toString();

            if (username2.length() == 0 || username1.length() == 0) {
                Toast.makeText(getApplicationContext(), "Enter the username first...!", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                return;
            }


            user1detail = null;
            Ion.with(getApplicationContext()).load("https://www.codechef.com/users/" + username1).
                    asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {

                    String error="Could not find page you requested for.";
                    if (result.contains(error)) {
                        Toast.makeText(getApplicationContext(), "Enter a valid usernamme of second user..", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        return;
                    }

                    Log.i("USer1", result);
                    user1detail = result;

                    Pattern p1 = Pattern.compile("," + username1 + "\">" + "(.*?)" + "</a>");
                    Matcher m1 = p1.matcher(user1detail);
                    user1question.clear();

                    while (m1.find()) {
                        user1question.add(m1.group(1));
                        Log.i("Question", m1.group(1));
                    }
                }

            });
            user2detail = null;
            Ion.with(getApplicationContext()).load("https://www.codechef.com/users/" + username2).asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {

                    String error="Could not find page you requested for.";
                    if (result.contains(error)) {
                        Toast.makeText(getApplicationContext(), "Enter a valid usernamme of second user..", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        return;
                    }

                    Log.i("USer2", result);
                    user2detail = result;
                    user2question.clear();

                    Pattern p1 = Pattern.compile("," + username2 + "\">" + "(.*?)" + "</a>");
                    Matcher m1 = p1.matcher(user2detail);

                    while (m1.find()) {
                        user2question.add(m1.group(1));
                        Log.i("Question", m1.group(1));
                    }

                    if(user1question.size()==0) {
                        return;
                    }

                    notdonequestion.clear();
                    for (int i = 0; i < user2question.size(); i++) {
                        if (!user1question.contains(user2question.get(i)))
                            notdonequestion.add(user2question.get(i));
                    }
                    for (int i = 0; i < notdonequestion.size(); i++) {
                        Log.i("NOT_DONE", notdonequestion.get(i));
                    }

                    if (notdonequestion.size() == 0 ) {
                        list.setVisibility(View.INVISIBLE);
                        message.setVisibility(View.VISIBLE);
                        message.setText("Congratulation , You Have done all the questions");
                        progressDialog.hide();
                        message.setTextSize(30);
                    } else {
                        message.setVisibility(View.INVISIBLE);
                        list.setVisibility(View.VISIBLE);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, notdonequestion);
                        list.setAdapter(adapter);
                        progressDialog.hide();
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String x = notdonequestion.get(position);
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.codechef.com/problems/" + x));
                                startActivity(browserIntent);
                            }
                        });

                        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                new android.support.v7.app.AlertDialog.Builder(Codechef.this).
                                        setTitle("Add").
                                        setMessage("Do you want to add this to your todo list").
                                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Set<String> s = shared.getStringSet("codechef", null);
                                                ArrayList<String> array = new ArrayList<String>();
                                                if (s != null) {
                                                    array.addAll(s);
                                                    s.clear();
                                                    Log.i("Insert ", "Ok");
                                                } else
                                                    s = new HashSet<String>();
                                                String p = notdonequestion.get((position));
                                                if (!array.contains(p))
                                                    array.add(notdonequestion.get(position));
                                                s.addAll(array);
                                                shared.edit().remove("codechef").apply();
                                                shared.edit().putStringSet("codechef", s).apply();

                                            }
                                        }).
                                        setNegativeButton("No", null).show();
                                return true;
                            }
                        });
                    }
                }
            });
            return;
        } else if (v == swap) {
            username1 = user1.getText().toString();
            username2 = user2.getText().toString();

            user1.setText(username2);
            user2.setText(username1);


            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.todo) {
            Intent codeforcesIntent = new Intent(getApplicationContext(), todo_list.class);
            startActivity(codeforcesIntent);
            return true;
        }
        return false;
    }


}
