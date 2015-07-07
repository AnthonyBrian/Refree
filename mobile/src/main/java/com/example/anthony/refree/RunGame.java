package com.example.anthony.refree;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.app.ListActivity;

import java.util.ArrayList;
import android.os.Handler;
import android.widget.Toast;


public class RunGame extends ListActivity  {

    //NumberPicker
    NumberPicker pick = null;

    //ArrayList for Listview
    ArrayList<String> list=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    //Stoppuhr
    private TextView textTimer;
    private Button startButton;
    private Button pauseButton;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;

    //Tore
    private EditText edittor;
    private EditText edittorgast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_game);


        //Daten holen
        TextView Datum = (TextView)findViewById(R.id.Datum);
        TextView Spielbeginn = (TextView)findViewById(R.id.Spielbeginn);
        TextView Mannschaft = (TextView)findViewById(R.id.mannschaft1);

        //Stoppuhrdaten

        textTimer = (TextView) findViewById(R.id.textTimer);
        startButton = (Button) findViewById(R.id.btnstart);
        pauseButton = (Button) findViewById(R.id.btnpause);

        Intent i = getIntent();

        final String datum=i.getStringExtra("Datum");
        Datum.setText("Datum:" + datum);

        final String spielbeginn=i.getStringExtra("Spielbeginn");
        Spielbeginn.setText("Spielbeginn:" + spielbeginn);

        final String mannschaft1=i.getStringExtra("Mannschaft1");
        final String mannschaft2 = i.getStringExtra("Mannschaft2");
        Mannschaft.setText("Heim: " + mannschaft1 + " Gast: " + mannschaft2);

        Button btnlist = (Button)findViewById(R.id.btnadd);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        //Tore
        edittor =(EditText)findViewById(R.id.editText);
        final String[] heimtor = new String[1];

        edittorgast = (EditText)findViewById(R.id.editText2);
        final String[] gasttor = new String[1];


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                heimtor[0] = edittor.getText().toString();
                gasttor[0] =edittorgast.getText().toString();
                list.add("Tor! Heim: " + mannschaft1 + " Gast: " + mannschaft2 + " Zeit: " + textTimer.getText().toString() + " Stand: " + heimtor[0] + ":"+gasttor[0]);
                adapter.notifyDataSetChanged();

            }
        };

        btnlist.setOnClickListener(listener);

        setListAdapter(adapter);

        //Stoppuhr

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);

            }
        });



        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

            }
        });


        final StringBuilder sb=new StringBuilder();
        for (String s : list)
        {
            sb.append(s);
            sb.append("\t");
        }



     Button buttonsenden=(Button)findViewById(R.id.btnsend);


        buttonsenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Test-Toast
               // Toast.makeText(getApplicationContext(), sb.toString(),Toast.LENGTH_LONG).show();

                Intent nextScreen = new Intent(getApplicationContext(), SendActivity.class);

                //Daten übergeben
                //nextScreen.putExtra("Ergebniss", ergbeniss);
               nextScreen.putStringArrayListExtra("Ergebnis",list);
               nextScreen.putExtra("Mannschaft1", mannschaft1);
               nextScreen.putExtra("Mannschaft2", mannschaft2);
               nextScreen.putExtra("Datum", datum);
               nextScreen.putExtra("Spielbeginn",spielbeginn);
               nextScreen.putExtra("Heimtor",heimtor[0]);
               nextScreen.putExtra("Gastor",gasttor[0]);


                startActivity(nextScreen);

            }
        });



    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            textTimer.setText("" + minutes + ":"
            + String.format("%02d", seconds) + ":"
            + String.format("%03d", milliseconds));
            myHandler.postDelayed(this, 0);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_run_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
