package com.example.dynamicscheduler;

/**
 * Created by Casey on 3/23/2017.
 */

public interface CreatorBehavior {

    //method header for createEvent, allows for different implementations for different types of users
    public Event createEvent(String title, int startTime, int stopTime, String location, String date);

    //method header for createBusyTime, allows for different implementations for different types of users
    public BusyTime createBusyTime(int start, int stop, String repeat, String name);

}
