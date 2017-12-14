package kamal.saqib.codepair;

import android.content.Intent;
import android.media.ImageWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class todo_list extends AppCompatActivity implements View.OnClickListener {

    ImageView codechef,spoj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        codechef=(ImageView) findViewById(R.id.codechef);
        spoj=(ImageView) findViewById(R.id.spoj);

        codechef.setOnClickListener(this);
        spoj.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v==codechef){
            Intent codeforcesIntent = new Intent(getApplicationContext(),Codechef_todo_list.class);
            startActivity(codeforcesIntent);
        }

        else if(v==spoj){
            Intent codeforcesIntent = new Intent(getApplicationContext(),spoj_todo_list.class);
            startActivity(codeforcesIntent);
        }

    }
}
