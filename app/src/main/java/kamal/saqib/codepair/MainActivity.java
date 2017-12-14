package kamal.saqib.codepair;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView codechef,spoj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codechef=(ImageView) findViewById(R.id.codechef);
        spoj=(ImageView) findViewById(R.id.spoj);

        codechef.setOnClickListener(this);
        spoj.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        if(v==codechef){
            Intent codeforcesIntent = new Intent(getApplicationContext(),Codechef.class);
            startActivity(codeforcesIntent);
        }
        else if(v==spoj){
            Intent codeforcesIntent = new Intent(getApplicationContext(),Spoj.class);
            startActivity(codeforcesIntent);
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
