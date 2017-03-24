package com.example.dynamicscheduler;

/**
 * Created by Casey on 3/23/2017.
 */

public interface CreatorBehavior {

    //method header for createEvent, allows for different implementations for different types of users
    public void createEvent();

    //method header for createBusyTime, allows for different implementations for different types of users
    public void createBusyTime();

}
