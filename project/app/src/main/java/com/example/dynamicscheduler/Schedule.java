package com.example.dynamicscheduler;

import java.util.ArrayList;

/**
 * Created by Cam on 3/23/2017.
 */

public class Schedule implements Observer{

    private ArrayList<Event> events;
    private ArrayList<BusyTime> busyTimes;

    public Schedule(){

    }

    public void addEvent(Event e){
        events.add(e);
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

    }
}
