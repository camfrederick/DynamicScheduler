package com.example.dynamicscheduler;
import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Arrays;

public class MakeInsertTask extends AsyncTask<Void, Void, Void> {
    final static int removeEvent = 0;
    final static int addEvent =1 ;

    private int addorremove = -1;

    private com.google.api.services.calendar.Calendar mService = null;
    private Exception mLastError = null;
    private Event my_event;
    MakeInsertTask(GoogleAccountCredential credential,Event event,int addorremove) {
        this.addorremove = addorremove;
        this.my_event = event;
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
        if(addorremove == addEvent) {
            try {
                pushToGoogleCal();
                return null;
            } catch (Exception e) {

                Log.d("D",e.getMessage());
                mLastError = e;
                cancel(true);
                return null;
            }
        }
        else if(addorremove == removeEvent) {
            try {
                removefromGoogleCal();
                return null;
            } catch (Exception e) {

                System.out.println("");
                mLastError = e;
                cancel(true);
                return null;
            }
        }
        return null;
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private void pushToGoogleCal() throws IOException{

        String starttime = timeToString(my_event.getStartTime());
        String endtime = timeToString(my_event.getStartTime());
        com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
                .setSummary(my_event.getTitle())
                .setLocation(my_event.getLocation())
                .setDescription(my_event.getDesc());

        String testDate = my_event.getDate() + "T" + starttime + ":00" + "-06:00";
        DateTime startDateTime = new DateTime(my_event.getDate() + "T" + starttime + ":00" + "-06:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

            DateTime endDateTime = new DateTime(my_event.getDate() + "T" + endtime + ":00" + "-06:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);
            event.setEnd(end);

        String calendarId = "primary";
//
        event = mService.events().insert(calendarId, event).execute();
//        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    private void removefromGoogleCal() throws IOException{

        String starttime = timeToString(my_event.getStartTime());
        String endtime = timeToString(my_event.getStartTime());
        com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
                .setSummary(my_event.getTitle())
                .setLocation(my_event.getLocation())
                .setDescription(my_event.getDesc());

        String testDate = my_event.getDate() + "T" + starttime + ":00" + "-06:00";
        DateTime startDateTime = new DateTime(my_event.getDate() + "T" + starttime + ":00" + "-06:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(my_event.getDate() + "T" + endtime + ":00" + "-06:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        String calendarId = "primary";
//
        event = mService.events().insert(calendarId, event).execute();
//        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    @Override
    protected void onCancelled() {

        if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {

            } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                startActivityForResult(
//                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                        NavigationPage.REQUEST_AUTHORIZATION);
            } else {

            }
        } else {

        }
    }
    public String timeToString(int time){
        String hourString = Integer.toString(time/100);
        String minuteString = Integer.toString(time%100);
        if(hourString.length() == 1){
            hourString = "0" + hourString;
        }
        if(minuteString.length() == 1){
            minuteString = "0" + minuteString;
        }
        return hourString + ":" + minuteString;
    }
}