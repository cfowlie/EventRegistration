package ca.mcgill.ecse321.eventregistration_android;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;

public class MainActivity extends AppCompatActivity {
    private RegistrationManager rm = null;
    private String fileName;
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Initialize file name and XStream
        fileName = getFilesDir().getAbsolutePath() + "/eventregistration.xml";
        rm = PersistenceXStream.initializeModelManager(fileName);
        refreshData();
    }

    private void refreshData() {
        TextView tv = (TextView) findViewById(R.id.newparticipant_name);
        tv.setText("");

        // Initialize the data in the participant spinner
        Spinner spinner = (Spinner) findViewById(R.id.participantspinner);
        ArrayAdapter<CharSequence> participantAdapter = new ArrayAdapter<CharSequence>(this, android.
                R.layout.simple_spinner_item);
        participantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Participant p: rm.getParticipants() ) {
            participantAdapter.add(p.getName());
        }
        spinner.setAdapter(participantAdapter);

        //Initialize data in event spinner
        Spinner eventSpinner = (Spinner) findViewById(R.id.eventspinner);
        ArrayAdapter<CharSequence> eventAdapter = new ArrayAdapter<CharSequence>(this, android.
                R.layout.simple_spinner_item);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Event e: rm.getEvents() ) {
            participantAdapter.add(e.getName());
        }
        eventSpinner.setAdapter(participantAdapter);



    }

    public void addParticipant(View v) {

        TextView tv = (TextView) findViewById(R.id.newparticipant_name);

        EventRegistrationController pc = new EventRegistrationController(rm);

        try {

            pc.createParticipant(tv.getText().toString());

        } catch (InvalidInputException e) {

            error = e.getMessage();

        }




        refreshData();

    }




    /* Method adds event based on chosen name, date, start time, and end time
     * Uses id to recover that info and uses Bundles to cast to Date and Time variables
      * used by method created in tutorial 1 */
    public void addEvent(View v) {      //String eventName, Time startTime, Time endTime, Date eventDate

        EventRegistrationController ec = new EventRegistrationController(rm);

        TextView tv2 = (TextView) findViewById(R.id.newevent_name); //get event
        TextView tv3 = (TextView) findViewById(R.id.newevent_date_value); //get date
        TextView tv4 = (TextView) findViewById(R.id.newevent_start_time_value); //get start time
        TextView tv5 = (TextView) findViewById(R.id.newevent_end_time_value); //get end time


        String eventName = tv2.getText().toString();

        Bundle date = getDateFromLabel(tv3.getText());
        Bundle startTime= getTimeFromLabel(tv4.getText());
        Bundle endTime = getTimeFromLabel(tv5.getText());

        /* use bundle methods Date() and Time() to recover individual elements from date and times */
        Date chosenDate = new Date(date.getInt("year"), date.getInt("month"), date.getInt("day"));
        Time chosenStartTime =  new Time(startTime.getInt("hour"), startTime.getInt("minute"),
                startTime.getInt("second"));
        Time chosenEndTime =  new Time(endTime.getInt("hour"), endTime.getInt("minute"),
                endTime.getInt("second"));

        /* Check for exceptions */
        try {
            ec.createEvent(eventName,chosenDate,chosenStartTime, chosenEndTime);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

        refreshData();

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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);

        return rtn;
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }


}
