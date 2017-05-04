package com.example.dynamicscheduler;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Events;

import java.util.ArrayList;

/**
 * Created by Casey on 3/23/2017.
 */

public class Schedule implements ScheduleObserver{

    private com.google.api.services.calendar.Calendar mService = null;
    private ArrayList<Event> events;
    ArrayList<BusyTime> busyTimes;

    public Schedule(){
        events = new ArrayList<Event>();
        busyTimes = new ArrayList<BusyTime>();
    }

    public void addEvent(Event e){
        events.add(e);
        new MakeInsertTask(GoogleCalendarTest.getmCredential(),e,MakeInsertTask.addEvent).execute();
        update();
    }

    public void addEvent(Event e, boolean b){
        events.add(e);
    }

    public void addEvent (com.google.api.services.calendar.model.Event e){
        String title = "";
        int startTime = 1100;
        int stopTime = 1300;
        String location = "";
        String date = "2017-04-06";
        String desc = "";
        if(e.getSummary() != null) {
            title = e.getSummary().toString();
        }
        if(e.getLocation() != null) {
            location = e.getLocation().toString();
        }
        if(e.getDescription() != null) {
            desc = e.getDescription().toString();
        }
        DateTime start = e.getStart().getDateTime();
        DateTime end = e.getEnd().getDateTime();
//
        if (start == null) {
            // All-day events don't have start times, so just use
            // the start date.
            start = e.getStart().getDate();
            //Log.d("start is null",start.toString());
            startTime = 0;
            stopTime = 2400;
        }
        else{
            String startString = start.toString();
            //Log.d("start is not",start.toString());
            startString = startString.substring(startString.indexOf("T") +1);
            String stopString = end.toString();
            stopString = stopString.substring(stopString.indexOf("T") +1);
            startTime = parseTime(startString);
            stopTime = parseTime(stopString);
        }
        date = start.toString();
        date = date.substring(0, date.indexOf('T'));

        Event event = new Event(title,startTime,stopTime,location,date,desc);
        events.add(event);
    }
    public int parseTime(String time){
//        Log.d("parsetime",time);
        int mTime = 0;
        String hours = time.substring(0,time.indexOf(":"));
        String minutes = time.substring(time.indexOf(":")+1,time.indexOf(":")+3);
        mTime = Integer.parseInt(hours)*100 + Integer.parseInt(minutes);
        return mTime;
    }
    public void addBusyTime(BusyTime busy) {
        busyTimes.add(busy);
    }

    public void removeBusyTime(BusyTime busy){
        busyTimes.remove(busy);
    }

    public void removeEvent(Event e){
        new MakeInsertTask(GoogleCalendarTest.getmCredential(),e,MakeInsertTask.removeEvent).execute();
        events.remove(e);
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public ArrayList<BusyTime> getBusyTimes(){
        return busyTimes;
    }

    @Override
    public void update() {

        drawSchedule();
    }

    public void drawSchedule(){

    }
}
