package com.example.anthony.refree;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class OptionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        Button buttonCreateTeam = (Button) findViewById(R.id.createteam);
        buttonCreateTeam.setOnClickListener(new OnClickListenerCreateTeam());

        countRecords();
        readRecords();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_option, menu);
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
    public void countRecords() {

        int recordCount = new TableControllerTeam(this).count();

        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " Eintraege gefunden.");
    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectTeam> teamList = new TableControllerTeam(this).read();

        if (teamList.size() > 0) {

            for (ObjectTeam obj : teamList) {

                int id = obj.id;
                String TeamFirstname = obj.teamname;


                String textViewContents = TeamFirstname ;

                TextView textViewLocationItem = new TextView(this);
                textViewLocationItem.setPadding(0, 10, 0, 10);
                textViewLocationItem.setText(textViewContents);
                textViewLocationItem.setTag(Integer.toString(id));

                linearLayoutRecords.addView(textViewLocationItem);


                textViewLocationItem.setOnLongClickListener(new OnLongClickListenerTeamRecord());
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("Keine Eintraege bisher.");

            linearLayoutRecords.addView(locationItem);


        }


    }
}
