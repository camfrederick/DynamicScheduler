package com.example.dynamicscheduler;

import android.os.Parcelable;

/**
 * Created by Casey on 3/23/2017.
 */

public class CreateForOthers implements CreatorBehavior{

    @Override
    public void createEvent(){
        //creates event for self and group members or just self
    }

    @Override
    public void createBusyTime(){
        //creates busytime for self and group members or just self
    }
}
