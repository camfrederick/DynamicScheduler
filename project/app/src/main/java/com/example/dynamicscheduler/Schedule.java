package com.example.dynamicscheduler;

import java.util.ArrayList;

/**
 * Created by Casey on 3/23/2017.
 */

public class Schedule implements ScheduleObserver{

    private ArrayList<Event> events;
    private ArrayList<BusyTime> busyTimes;

    public Schedule(){
        events = new ArrayList<Event>();
        busyTimes = new ArrayList<BusyTime>();
    }

    public void addEvent(Event e){
        events.add(e);
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
    }
}
