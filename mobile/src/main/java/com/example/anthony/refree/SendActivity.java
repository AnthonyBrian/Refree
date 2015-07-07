package com.example.anthony.refree;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SendActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        //Zuordnen
        TextView mannschaft = (TextView)findViewById(R.id.mannschaften);
        TextView ergebnis = (TextView)findViewById(R.id.ergebnis);

        Intent i = getIntent();

        //Arraylist holen und als String übergeben

        ArrayList<String> list = new ArrayList<String>();
        list=i.getStringArrayListExtra("Ergebnis");

        final StringBuilder builder = new StringBuilder();
        for(String details : list){
            builder.append(details +"\n");
        }
        //Anzeigen Test
        //test.setText(builder.toString());

        //Restliche Daten holen
        final String mannschaft1 = i.getStringExtra("Mannschaft1");
        final String mannschaft2 = i.getStringExtra("Mannschaft2");
        final String datum = i.getStringExtra("Datum");
        final String spielbeginn = i.getStringExtra("Spielbeginn");
        final String heimtor = i.getStringExtra("Heimtor");
        final String gasttor = i.getStringExtra("Gastor");

        //Anzeigen
        mannschaft.setText(mannschaft1 + ": " + heimtor);
        ergebnis.setText(mannschaft2 + ": " + gasttor);

        //Email verschicken
        Button startBtn = (Button) findViewById(R.id.sendEmail);
        final ArrayList<String> finalList = list;
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail(mannschaft1,mannschaft2,datum,spielbeginn, finalList,heimtor,gasttor);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send, menu);
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

    protected void sendEmail(String mannschaft1, String mannschaft2, String datum, String spielbeginn, ArrayList<String> finalList, String heimtor, String gasttor) {

        //ArrayList holen
        final StringBuilder builder = new StringBuilder();
        for(String details : finalList){
            builder.append(details +"\n");
        }
        final String test;
        test = builder.toString();

        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Spielbericht ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Das Ergebnis\n" + mannschaft1 +": " + heimtor + "\n" + mannschaft2 + ": " + gasttor + "\n\n" + "Datum: " + datum + "\nSpielbeginn: " + spielbeginn +  "\n\nSpielverlauf:\n"  + test +"\n");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Email versendet", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
