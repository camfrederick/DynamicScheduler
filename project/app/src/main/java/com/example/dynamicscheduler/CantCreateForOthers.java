package com.example.dynamicscheduler;

/**
 * Created by Casey on 3/23/2017.
 */

public class CantCreateForOthers implements CreatorBehavior{

    @Override
    public void createEvent(){
        //creates group related event for self
    }

    @Override
    public void createBusyTime(){
        //creates group related busytime for self
    }
}
