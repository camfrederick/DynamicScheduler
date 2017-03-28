package com.example.dynamicscheduler;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cam on 3/23/2017.
 */

public class Member extends User implements UserObserver {

    ArrayList<Group> groups;

    public Member(ArrayList<Group> groupList, String fullName, String address, String emailAddress, int phoneNum, Schedule sched) {
        super(fullName, address, emailAddress, phoneNum, sched);
        this.groups = groupList;
    }

    public Member(Group g, String fullName, String address, String emailAddress, int phoneNum, Schedule sched) {
        super(fullName, address, emailAddress, phoneNum, sched);
        this.groups.add(g);
    }

    public void manageGroups() {

    }

    public void createEvent() {

    }

    @Override
    public void update(int startTime, int stopTime, String title, String location) {
        //notifies user that a new event has been created for them and adds the new event to their schedule
    }
}
