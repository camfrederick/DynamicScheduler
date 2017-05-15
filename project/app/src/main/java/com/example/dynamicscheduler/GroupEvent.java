package com.example.dynamicscheduler;

import java.util.ArrayList;

/**
 * Created by Casey on 4/24/2017.
 */

public class GroupEvent extends Event{
    private ArrayList<User> observers = new ArrayList<User>();
    private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    private Group group;

    public GroupEvent(Group g, String title, int startTime, int stopTime,
                      String location,String date,String desc) {
        super(title,startTime,stopTime,location,date, desc);
        for(Member member : g.getMemberList()){
            if(member.equals(g.getAdmin())){
                continue;
            }
            registerObserver(member);
            registerSchedule(member.getSchedule());
        }
        group = g;
    }

    public void changeEvent(String title, int startTime, int stopTime,
                            String location,String date){
        super.changeEvent(title,startTime,stopTime,location,date);
    }


    public void registerSchedule(Schedule o) {
        schedules.add(o);
    }


    public void removeSchedule(Schedule o) {
        schedules.remove(o);
    }



    public void registerObserver(User o) {
        observers.add(o);
    }


    public void removeObserver(User o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }


//
}
