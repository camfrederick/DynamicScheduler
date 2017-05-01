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
import android.widget.Button;
import android.widget.EditText;
import android.app.DialogFragment;
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


public class CreateEvent extends AppCompatActivity {
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    private EditText event_location;
   //private EditText event_date;
    private EditText event_name;
    private EditText event_desc;

    private Button create_event;
    private Button event_starttime;
    private Button event_stoptime;
    private Button event_date;
    private Button automated_event;

    private TextView timepressed;
    private String starttime;
    private String endtime;
    GoogleAccountCredential mCredential;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        event_location = (EditText)findViewById(R.id.ce_setlocation);
//        event_date = (EditText)findViewById(R.id.ce_setdate);
        event_name = (EditText)findViewById(R.id.ce_setname);
        event_desc = (EditText)findViewById(R.id.ce_setdescription);

        create_event = (Button)findViewById(R.id.ce_insert);
        event_date = (Button)findViewById(R.id.ce_setdate);
        event_starttime = (Button)findViewById(R.id.ce_starttime);
        event_stoptime = (Button)findViewById(R.id.ce_stoptime);
        automated_event = (Button)findViewById(R.id.automatedevent);


        timepressed = (TextView)findViewById(R.id.time_button);

        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new MakeInsertTask(mCredential).execute();
                finish();
            }
        });

        event_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepressed.setText("1");
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
                //starttime = ((TimePickerFragment)newFragment).getTimeString();
            }
        });

        event_stoptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepressed.setText("2");
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
                //endtime = ((TimePickerFragment)newFragment).getTimeString();
            }
        });

        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepressed.setText("3");
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                //endtime = ((TimePickerFragment)newFragment).getTimeString();
            }
        });

        automated_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAutomatedEvent.class);
                startActivity(intent);
            }
        });





        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        Account[] mC_Accts = mCredential.getAllAccounts();

        user = FirebaseAuth.getInstance().getCurrentUser();

        mCredential.setSelectedAccountName(user.getEmail());


    }

    private void pushToFirebase(){

    }

    private class MakeInsertTask extends AsyncTask<Void, Void, Void> {


        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        MakeInsertTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();

        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected Void doInBackground(Void... params) {
            //android.os.Debug.waitForDebugger();
            try {
                pushToGoogleCal();
                return null;
            } catch (Exception e) {

                System.out.println("");
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private void pushToGoogleCal() throws IOException{

            starttime = event_starttime.getText().toString();
            endtime = event_stoptime.getText().toString();
            com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
                    .setSummary(event_name.getText().toString())
                    .setLocation(event_location.getText().toString())
                    .setDescription(event_desc.getText().toString());

            String testDate = event_date.getText().toString() + "T" + starttime + ":00" + "-06:00";
            DateTime startDateTime = new DateTime(event_date.getText().toString() + "T" + starttime + ":00" + "-06:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime);
            event.setStart(start);

            if(!endtime.equals("Button")){
                DateTime endDateTime = new DateTime(event_date.getText().toString() + "T" + endtime + ":00" + "-06:00");
                EventDateTime end = new EventDateTime()
                        .setDateTime(endDateTime);
                event.setEnd(end);
            }
            String calendarId = "primary";

            event = mService.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }

        @Override
        protected void onCancelled() {

            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {

                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            NavigationPage.REQUEST_AUTHORIZATION);
                } else {

                }
            } else {

            }
        }
    }



}
