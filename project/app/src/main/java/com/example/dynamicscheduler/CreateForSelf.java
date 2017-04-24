package com.example.dynamicscheduler;

/**
 * Created by Casey on 3/23/2017.
 */

public class CreateForSelf implements CreatorBehavior{

    @Override
    public Event createEvent(String title, int startTime, int stopTime, String location, String date){
        Event e = new Event(title, startTime, stopTime, location, date);
        return e;
    }

    @Override
    public BusyTime createBusyTime(int start, int stop, String repeat, String name){
        BusyTime b = new BusyTime(start, stop, repeat, name);
        return b;
    }
}
