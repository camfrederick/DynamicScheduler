package com.example.dynamicscheduler;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
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
        update();
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
        //draws the schedule on our UI
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
        Events events = mService.events();
    }
}
