package com.example.dynamicscheduler;

import android.accounts.Account;
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
    private Button event_length;
    private Button event_deadline;

    private Spinner busyTimes;

    private TextView timepressed;
    private String starttime;
    private String endtime;

    GoogleAccountCredential mCredential;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<BusyTime> busyTime_list = new ArrayList<BusyTime>(); //TODO get current user busytimes user.schedule.
        busyTime_list.add(new BusyTime(1400,1700,"everyday","Homework"));

        if(busyTime_list.size() == 0){
            //TODO throw error for not having a busytime
        }

        ArrayList<String> spinnerArray = new ArrayList<String>();
        for(BusyTime bt : busyTime_list){
            spinnerArray.add(bt.getTitle());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_automated_event);

        event_location = (EditText)findViewById(R.id.ce_setlocation);
//        event_date = (EditText)findViewById(R.id.ce_setdate);
        event_name = (EditText)findViewById(R.id.ce_setname);
        event_desc = (EditText)findViewById(R.id.ce_setdescription);

        create_event = (Button)findViewById(R.id.ce_insert);
        event_length = (Button)findViewById(R.id.ce_lengthtime);
        event_deadline = (Button)findViewById(R.id.ce_setdate);

        busyTimes = (Spinner)findViewById(R.id.ce_busytime_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busyTimes.setAdapter(spinnerArrayAdapter);

        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String length_string = event_length.toString();
                String deadline_string = event_deadline.toString();

                if(length_string == "00:00"){
                    //TODO throw some sort of error for not putting a length of time
                }
                if(deadline_string == "YYYY-MM-DD"){
                    deadline_string = "9999-12-30"; //no deadline input set maximum date
                }
                length_string = length_string.substring(0,2) + length_string.substring(3,5); //01:34 -> 0134
                int time_length = Integer.parseInt(length_string);

                User us = new User("1234","exampleemail@example.com"); //TODO make user be pulled
                BusyTime bt =  null;
                for(BusyTime b: busyTime_list){
                    if(busyTimes.getSelectedItem().toString().equals(b.getTitle())){
                        bt = b;
                        break;
                    }
                }
                us.createEventAlgorithmically("EveryDay",event_name.toString(),time_length,event_location.toString(),
                        deadline_string,bt);

            }
        });

        event_length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepressed.setText("1");
                DialogFragment newFragment = new TimePickerFragment(
                );
                newFragment.show(getFragmentManager(), "TimePicker");
                //starttime = ((TimePickerFragment)newFragment).getTimeString();
            }
        });



    }





}
