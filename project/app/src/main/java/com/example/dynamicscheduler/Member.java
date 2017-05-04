package com.example.dynamicscheduler;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cam on 3/23/2017.
 */

public class Member extends User implements UserObserver {

    private ArrayList<Group> groups;

    public Member(ArrayList<Group> groupList, String fullName, String address, String emailAddress, String phoneNum) {
        super(fullName, address, emailAddress, phoneNum);
        this.groups.addAll(groupList);
    }

    public Member(Group g, String fullName, String address, String emailAddress, String phoneNum) {
        super(fullName, address, emailAddress, phoneNum);
        this.groups.add(g);
    }

    public Member(String fullName, String address, String emailAddress, String phoneNum){
        super(fullName, address, emailAddress, phoneNum);
    }

    public void createEvent(String title, int startTime, int stopTime, String location, String date,String desc){
        Event event = new Event(title, startTime, stopTime, location, date,desc);
        schedule.addEvent(event);
    }

    public void createGroupEvent(Group group, String title, int startTime, int stopTime, String location, String date,String desc){
        GroupEvent gevent = new GroupEvent(group,title, startTime, stopTime, location, date, desc);
        schedule.addEvent(gevent);
    }

    public ArrayList<Group> getAdminGroups(){
        ArrayList<Group> grouplist = new ArrayList<Group>();
        for (Group g : groups){
            if(g.getAdmin().equals(this)){
                grouplist.add(g);
            }
        }
        return grouplist;
    }

    public void createBusyTime(int start, int stop, String repeat, String name){
        BusyTime busy = behave.createBusyTime(start, stop, repeat, name);
        schedule.addBusyTime(busy);
    }

    @Override
    public void update(Event e) {
        //ping user using android notification
        if (schedule.getEvents().contains(e)){
            schedule.removeEvent(e);
        }
        schedule.addEvent(e);
        //notifies user that a new event has been created for them and adds the new event to their schedule
    }
}
