package com.example.zlata.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zlata.quiz.Server.ServerGetPlayersAT;
import com.example.zlata.quiz.Server.ServerSaveResultAT;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    public static final String GUESSES = "guesses";

    ListView resultsListView;

    ArrayAdapter<String> adapter;

    ArrayList<String> results;
    TextView resultsTextView;
    Button saveResultsButton;

    private ArrayList<String> resFromServer;

    SQLiteDatabase db;

    private boolean isRenew = false;

    int correctAnswersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        correctAnswersCount = intent.getIntExtra(GUESSES, 0);

        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        resultsTextView.setText(String.valueOf(correctAnswersCount));

        saveResultsButton = (Button) findViewById(R.id.saveResultsButton);
        saveResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);

                builder.setTitle(R.string.input_name);
                final EditText nameEditText = new EditText(ResultActivity.this);
                builder.setView(nameEditText);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String name = nameEditText.getText().toString();
                        if (name.equals("")) {
                            name = "Anonymous";
                        }
                        ServerSaveResultAT saveResultAT = new ServerSaveResultAT();
                        saveResultAT.execute(name, String.valueOf(correctAnswersCount));
                        ResultActivity.this.ReadResultsFromServer();

                        adapter.notifyDataSetChanged();


                    }
                });

                builder.create().show();
                ((Button) view).setEnabled(false);
            }
        });

        results = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, R.layout.results_item, results);
        resultsListView = (ListView) findViewById(R.id.resultsListView);
        resultsListView.setAdapter(adapter);

        this.ReadResultsFromServer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main, menu);

         if (isRenew) {
             menu.findItem(R.id.action_f5).setEnabled(false);
         }
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.action_f5:
                 ResultActivity.this.ReadResultsFromServer();
                 adapter.notifyDataSetChanged();
                 Toast.makeText(getApplicationContext(),"refreshed",Toast.LENGTH_SHORT).show();
         }
         return super.onOptionsItemSelected(item);
     }

    private void ReadResultsFromServer(){
        ServerGetPlayersAT getPlayersAT=new ServerGetPlayersAT(this);
        getPlayersAT.execute();
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
        adapter.clear();
        for(String line: resFromServer){
            adapter.add(line);
        }
    }

    public ArrayList<String> getResFromServer() {
        return resFromServer;
    }



    public void setResFromServer(ArrayList<String> resFromServer) {
        this.resFromServer = resFromServer;
    }
}
