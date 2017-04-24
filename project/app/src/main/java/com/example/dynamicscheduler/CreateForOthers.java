package com.example.dynamicscheduler;

/**
 * Created by Casey on 3/23/2017.
 */

public class CreateForOthers implements CreatorBehavior{

    @Override
    public Event createEvent(String title, int startTime, int stopTime, String location, String date){
        return null;
        //creates event for self
    }

    @Override
    public BusyTime createBusyTime(int start, int stop, String repeat, String name){
        return null;
        //creates busytime for self
    }
}
