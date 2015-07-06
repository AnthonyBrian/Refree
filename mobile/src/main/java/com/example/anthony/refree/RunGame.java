package com.example.anthony.refree;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.ListActivity;

import java.util.ArrayList;


public class RunGame extends  ListActivity {

    ArrayList<String> list=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_game);

        //Daten holen
        TextView Datum = (TextView)findViewById(R.id.Datum);
        TextView Spielbeginn = (TextView)findViewById(R.id.Spielbeginn);
        TextView Mannschaft = (TextView)findViewById(R.id.mannschaft1);

        Intent i = getIntent();

        final String datum=i.getStringExtra("Datum");
        Datum.setText("Datum:" + datum);

        String spielbeginn=i.getStringExtra("Spielbeginn");
        Spielbeginn.setText("Spielbeginn:" + spielbeginn);

        final String mannschaft1=i.getStringExtra("Mannschaft1");
        final String mannschaft2 = i.getStringExtra("Mannschaft2");
        Mannschaft.setText("Partie zwischen " + mannschaft1 + " und " + mannschaft2);

        Button btnlist = (Button)findViewById(R.id.btnadd);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add("Tor! Neuer Spielstand" + mannschaft1 + " und " + mannschaft2 + "am " + datum);
                adapter.notifyDataSetChanged();
            }
        };

        btnlist.setOnClickListener(listener);

        setListAdapter(adapter);

    }

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
