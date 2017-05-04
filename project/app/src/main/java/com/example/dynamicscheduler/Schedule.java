package com.example.dynamicscheduler;

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
    private ArrayList<BusyTime> busyTimes;

    public Schedule(){
        events = new ArrayList<Event>();
        busyTimes = new ArrayList<BusyTime>();
    }

    public void addEvent(Event e){
        events.add(e);
        new MakeInsertTask(GoogleCalendarTest.getmCredential(),e,MakeInsertTask.addEvent).execute();
        update();
    }

    public void addEvent (com.google.api.services.calendar.model.Event e){
        String title = "";
        int startTime = 1100;
        int stopTime = 1300;
        String location = "";
        String date = "2017-04-06";
        String desc = "";
//        title = e.getSummary().toString();
//        location = e.getLocation().toString();
//        desc = e.getDescription().toString();

//        DateTime start = e.getStart().getDateTime();
//        DateTime end = e.getEnd().getDateTime();

//        if (start == null) {
//            // All-day events don't have start times, so just use
//            // the start date.
//            start = e.getStart().getDate();
//            startTime = 0;
//            stopTime = 2400;
//        }
//        else{
//            String startString = start.toString();
//            startString = startString.substring(startString.indexOf("T"));
//            String stopString = end.toString();
//            stopString = stopString.substring(startString.indexOf("T"));
//            startTime = parseTime(startString);
//            stopTime = parseTime(stopString);
//        }
//        date = start.toString();



        Event event = new Event(title,startTime,stopTime,location,date,desc);
        events.add(event);
    }
    public int parseTime(String time){
        int mTime = 0;
        String hours = time.substring(0,2);
        String minutes = time.substring(3,5);
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
