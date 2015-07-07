package com.example.anthony.refree;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class TestActivity extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    //Spinner Mannschaften
    Spinner spinner;
    Spinner spinner2;

    // views
    private TextView txtDate;
    private TextView txtTime;
    private Button btnChangeDate;
    private Button btnChangeTime;

    // variables to store the selected date and time
    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedDay;
    private int mSelectedHour;
    private int mSelectedMinutes;

    //Zeitformat
    private SimpleDateFormat dateFormatter;

    // CallBacks for date and time pickers
    private DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // update the current variables ( year, month and day)
            mSelectedDay = dayOfMonth;
            mSelectedMonth = monthOfYear;
            mSelectedYear = year;

            // update txtDate with the selected date
            updateDateUI();
        }
    };

    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // update the current variables (hour and minutes)
            mSelectedHour = hourOfDay;
            mSelectedMinutes = minute;

            // update txtTime with the selected time
            updateTimeUI();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Parameter
        txtDate = (TextView)findViewById(R.id.txt_date);
        txtTime = (TextView)findViewById(R.id.txt_time);
        Button submitbutton = (Button)findViewById(R.id.submitbutton);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 =(Spinner)findViewById(R.id.spinner2);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextScreen= new Intent(getApplicationContext(),RunGame.class);

                //Daten übergeben
                nextScreen.putExtra("Datum", txtDate.getText().toString());
                nextScreen.putExtra("Spielbeginn",txtTime.getText().toString());

                //Spinner
                String mannschaft1 = spinner.getSelectedItem().toString();
                nextScreen.putExtra("Mannschaft1",mannschaft1);

                String mannschaft2 = spinner2.getSelectedItem().toString();
                nextScreen.putExtra("Mannschaft2",mannschaft2);

                //Log
                Log.e("n",txtDate.getText()+ ".");

                startActivity(nextScreen);

            }
        });

        //Datum umstellen auf Europa
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        // retrieve views
        this.txtDate = (TextView) findViewById(R.id.txt_date);
        this.txtTime = (TextView) findViewById(R.id.txt_time);
        this.btnChangeDate = (Button) findViewById(R.id.btn_show_date_picker);
        this.btnChangeTime = (Button) findViewById(R.id.btn_show_time_picker);
        this.btnChangeDate.setOnClickListener(this);
        this.btnChangeTime.setOnClickListener(this);

        // initialize the current date
        Calendar calendar = Calendar.getInstance();
        this.mSelectedYear = calendar.get(Calendar.YEAR);
        this.mSelectedMonth = calendar.get(Calendar.MONTH);
        this.mSelectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.mSelectedHour = calendar.get(Calendar.HOUR_OF_DAY);
        this.mSelectedMinutes = calendar.get(Calendar.MINUTE);

        // set the current date and time on TextViews
        updateDateUI();
        updateTimeUI();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);

        // Loading spinner data from database
        loadSpinnerData();

        //Notification Android wear
        Button mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.drawable.androidsoccer)
                        .setContentTitle("Referee App")
                        .setContentText("Referee App starten?")
                        .extend(
                                new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                        .build();

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

                int notificationId = 1;
                notificationManager.notify(notificationId, notification);
            }
        });

    }

    private void updateDateUI() {
        String month = ((mSelectedMonth+1) > 9) ? ""+(mSelectedMonth+1): "0"+(mSelectedMonth+1) ;
        String day = ((mSelectedDay) < 10) ? "0"+mSelectedDay: ""+mSelectedDay ;
        txtDate.setText(day+"/"+month+"/"+mSelectedYear);
    }

    private void updateTimeUI() {
        String hour = (mSelectedHour > 9) ? ""+mSelectedHour: "0"+mSelectedHour ;
        String minutes = (mSelectedMinutes > 9) ?""+mSelectedMinutes : "0"+mSelectedMinutes;
        txtTime.setText(hour+":"+minutes);
    }

    // initialize the DatePickerDialog
    private DatePickerDialog showDatePickerDialog(int initialYear, int initialMonth, int initialDay, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog dialog = new DatePickerDialog(this, listener, initialYear, initialMonth, initialDay);
        dialog.show();
        return dialog;
    }

    // initialize the TimePickerDialog
    private TimePickerDialog showTimePickerDialog(int initialHour, int initialMinutes, boolean is24Hour, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog dialog = new TimePickerDialog(this, listener, initialHour, initialMinutes, is24Hour);
        dialog.show();
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_date_picker:
                showDatePickerDialog(mSelectedYear, mSelectedMonth, mSelectedDay, mOnDateSetListener);
                break;
            case R.id.btn_show_time_picker:
                showTimePickerDialog(mSelectedHour, mSelectedMinutes, true, mOnTimeSetListener);
                break;
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> labels = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Mannschaft: " + label,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}