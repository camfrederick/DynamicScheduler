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

    }

    public Admin(Group g, String fullName, String address, String emailAddress, int phoneNum, Schedule sched) {
        super(fullName, address, emailAddress, phoneNum, sched);
        this.groups.add(g);
    }

    public void manageGroups() {

    }

    public void createEvent() {

    }

    @Override
    public void update(int startTime, int stopTime, String title, String location) {
        //notifies admin that a new member event has been created for them and gives them the option of adding the event to their
        //schedule
    }
}
