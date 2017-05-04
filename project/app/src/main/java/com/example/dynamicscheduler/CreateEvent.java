package com.example.dynamicscheduler;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CreateEvent extends AppCompatActivity {
    private User client_user = GoogleCalendarTest.getUser();
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    private EditText event_location;
   //private EditText event_date;
    private EditText event_name;
    private EditText event_desc;
    Spinner groupspin;
    ArrayAdapter adapter;
    ArrayList<String> groups_with_blank;

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
    DatabaseReference dataRef;
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
        event_starttime = (Button)findViewById(R.id.ce_lengthtime);
        event_stoptime = (Button)findViewById(R.id.ce_stoptime);
        automated_event = (Button)findViewById(R.id.automatedevent);
        groupspin = (Spinner)findViewById(R.id.group_spin);

        groups_with_blank = GoogleCalendarTest.client_user.getGroup_IDs();
        groups_with_blank.add(0, "Select Group (Optional)");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groups_with_blank);
        groupspin.setAdapter(adapter);
        timepressed = (TextView)findViewById(R.id.time_button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();



        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //        starttime = event_starttime.getText().toString();
                //        endtime = event_stoptime.getText().toString();
                //        com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
                //                .setSummary(event_name.getText().toString())
                //                .setLocation(event_location.getText().toString())
                //                .setDescription(event_desc.getText().toString());
                //
                //        String testDate = event_date.getText().toString() + "T" + starttime + ":00" + "-06:00";
                //        DateTime startDateTime = new DateTime(event_date.getText().toString() + "T" + starttime + ":00" + "-06:00");
                //        EventDateTime start = new EventDateTime()
                //                .setDateTime(startDateTime);
                //        event.setStart(start);
                //
                //        if(!endtime.equals("Button")){
                //            DateTime endDateTime = new DateTime(event_date.getText().toString() + "T" + endtime + ":00" + "-06:00");
                //            EventDateTime end = new EventDateTime()
                //                    .setDateTime(endDateTime);
                //            event.setEnd(end);
                //        }
                //        String calendarId = "primary";
                //
                //        event = mService.events().insert(calendarId, event).execute();
                //        System.out.printf("Event created: %s\n", event.getHtmlLink());

                int starttime = parseTime(event_starttime.getText().toString());
                int stoptime = parseTime(event_stoptime.getText().toString());

                String group_selection = groupspin.getSelectedItem().toString();

                if((groupspin.getSelectedItemPosition() != 0)&&(client_user!=null)){
                    client_user.createEvent(event_name.getText().toString(), starttime, stoptime, event_location.getText().toString(),
                            event_date.getText().toString(), event_desc.getText().toString(), group_selection);
                    final EventAdapter event = new EventAdapter(event_name.getText().toString(), starttime, stoptime, event_location.getText().toString(),
                            event_date.getText().toString(), event_desc.getText().toString());
                    dataRef = database.getReference("groups").child(group_selection).child("Event");
                    try{
                        database.getReference("groups").child(group_selection).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Group myGroup = dataSnapshot.getValue(Group.class);
                                //myGroup.groupEvent = event;
                                //myGroup.updateMemberDatabase(dataSnapshot);

                                Map<String, Object> notifMap = new HashMap<String, Object>();
                                notifMap.put("groupEvent", event);
                                dataRef.setValue(event);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });}catch(Exception e){
                        int i = 5;
                    }

                    //database.getReference("groups").child(group_selection).updateChildren(notifMap);

                }
                //client_user = GoogleCalendarTest.getUser();
                else if(client_user != null) {
                    client_user.createEvent(event_name.getText().toString(), starttime, stoptime, event_location.getText().toString(),
                            event_date.getText().toString(), event_desc.getText().toString());
                    finish();
                }
                //new MakeInsertTask(mCredential).execute();
//                else {
//                    finish();
//                }
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

    public int parseTime(String time){
        String hourString = time.substring(0,time.indexOf(":"));
        String minuteString = time.substring(time.indexOf(":") +1 ,time.indexOf(":") +3);
        return Integer.parseInt(hourString) * 100 + Integer.parseInt(minuteString);

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
         *
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
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private void pushToGoogleCal() throws IOException {

//        starttime = event_starttime.getText().toString();
//        endtime = event_stoptime.getText().toString();
//        com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
//                .setSummary(event_name.getText().toString())
//                .setLocation(event_location.getText().toString())
//                .setDescription(event_desc.getText().toString());
//
//        String testDate = event_date.getText().toString() + "T" + starttime + ":00" + "-06:00";
//        DateTime startDateTime = new DateTime(event_date.getText().toString() + "T" + starttime + ":00" + "-06:00");
//        EventDateTime start = new EventDateTime()
//                .setDateTime(startDateTime);
//        event.setStart(start);
//
//        if(!endtime.equals("Button")){
//            DateTime endDateTime = new DateTime(event_date.getText().toString() + "T" + endtime + ":00" + "-06:00");
//            EventDateTime end = new EventDateTime()
//                    .setDateTime(endDateTime);
//            event.setEnd(end);
//        }
//        String calendarId = "primary";
//
//        event = mService.events().insert(calendarId, event).execute();
//        System.out.printf("Event created: %s\n", event.getHtmlLink());
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
