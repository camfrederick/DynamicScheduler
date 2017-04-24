package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */

import java.util.ArrayList;

public class Admin extends User implements UserObserver {

    ArrayList<Group> groups;

    public Admin(ArrayList<Group> groupList, String fullName, String address, String emailAddress, int phoneNum, Schedule sched) {
        super(fullName, address, emailAddress, phoneNum, sched);
        this.groups = groupList;
        behave = new CreateForOthers();
    }

    public Admin(Group g, String fullName, String address, String emailAddress, int phoneNum, Schedule sched) {
        super(fullName, address, emailAddress, phoneNum, sched);
        this.groups.add(g);
        behave = new CreateForOthers();
    }

    public void createEvent(String title, int startTime, int stopTime, String location, String date){
        Event event = behave.createEvent(title, startTime, stopTime, location, date);
        schedule.addEvent(event);
    }

    public void createBusyTime(int start, int stop, String repeat, String name){
        BusyTime busy = behave.createBusyTime(start, stop, repeat, name);
        schedule.addBusyTime(busy);
    }

    @Override
    public void update(int startTime, int stopTime, String title, String location, String date) {
        //notifies admin that a new member event has been created for them and gives them the option of adding the event to their
        //schedule
    }
}
