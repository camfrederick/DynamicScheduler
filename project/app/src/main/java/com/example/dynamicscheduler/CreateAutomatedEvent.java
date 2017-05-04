package com.example.dynamicscheduler;

import android.accounts.Account;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.app.DialogFragment;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class CreateAutomatedEvent extends AppCompatActivity {
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    private EditText event_location;
    //private EditText event_date;
    private EditText event_name;
    private EditText event_desc;

    private Button create_event;
    //private Button event_length;
    private Button event_deadline;
    private NumberPicker num_pick_hours;
    private NumberPicker num_pick_minutes;

    private Spinner busyTimes;

    private TextView timepressed;
    private String starttime;
    private String endtime;

    GoogleAccountCredential mCredential;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final ArrayList<BusyTime> busyTime_list = new ArrayList<BusyTime>(); //TODO get current user busytimes user.schedule.
       // busyTime_list.add(new BusyTime(1400,1700,"everyday","Homework"));
        final ArrayList<BusyTime> busyTime_list = GoogleCalendarTest.getUser().getSchedule().getBusyTimes();
        if(busyTime_list.size() == 0){
            //TODO throw error for not having a busytime
        }

        ArrayList<String> spinnerArray = new ArrayList<String>();
        for(BusyTime bt : busyTime_list){
            spinnerArray.add(bt.getTitle());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_automated_event);

        num_pick_hours = (NumberPicker)findViewById(R.id.np_hours);
        num_pick_hours.setMinValue(0);
        num_pick_hours.setMaxValue(23);
        num_pick_hours.setWrapSelectorWheel(true);

        num_pick_minutes = (NumberPicker)findViewById(R.id.np_minutes);
        num_pick_minutes.setMinValue(0);
        num_pick_minutes.setMaxValue(59);
        num_pick_minutes.setWrapSelectorWheel(true);

        event_location = (EditText)findViewById(R.id.ce_setlocation);
//        event_date = (EditText)findViewById(R.id.ce_setdate);
        event_name = (EditText)findViewById(R.id.ce_setname);
        event_desc = (EditText)findViewById(R.id.ce_setdescription);

        create_event = (Button)findViewById(R.id.ce_insert);
        //event_length = (Button)findViewById(R.id.ce_lengthtime);
        event_deadline = (Button)findViewById(R.id.ce_setdate);

        busyTimes = (Spinner)findViewById(R.id.ce_busytime_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busyTimes.setAdapter(spinnerArrayAdapter);

        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("d","here");
                String deadline_string = event_deadline.getText().toString();
                int time_length = num_pick_hours.getValue()*100 + num_pick_minutes.getValue();
                if(time_length == 0){
                    //TODO: throw an error for no duration
                }
                if(deadline_string == "YYYY-MM-DD"){
                    deadline_string = "9999-12-30"; //no deadline input set maximum date
                }

                User us =  GoogleCalendarTest.getUser();
                BusyTime bt =  null;
                for(BusyTime b: busyTime_list){
                    if(busyTimes.getSelectedItem().toString().equals(b.getTitle())){
                        bt = b;
                        break;
                    }
                }
                us.createEventAlgorithmically("EveryDay",event_name.getText().toString(),time_length,
                        event_location.getText().toString(),deadline_string,bt,event_desc.getText().toString());

                finish();

                Intent intent = new Intent(v.getContext(), GoogleCalendarTest.class);
                startActivity(intent);

            }
        });
        event_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                //endtime = ((TimePickerFragment)newFragment).getTimeString();
            }
        });

    }





}
