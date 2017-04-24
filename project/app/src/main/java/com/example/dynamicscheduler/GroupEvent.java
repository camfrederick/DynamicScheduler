package com.example.dynamicscheduler;

import java.util.ArrayList;

/**
 * Created by Casey on 4/24/2017.
 */

public class GroupEvent extends Event implements UserObservable{
    private ArrayList<UserObserver> observers = new ArrayList<UserObserver>();
    private ArrayList<ScheduleObserver> schedules = new ArrayList<ScheduleObserver>();
    private Group group;

    public GroupEvent(Group g, String title, int startTime, int stopTime,
                      String location,String date) {
        super(title,startTime,stopTime,location,date);
        for(Member member : g.getMemberList()){
            if(member.equals(g.getAdmin())){
                continue;
            }
            registerObserver(member);
            registerSchedule(member.getSchedule());
        }
        group = g;
        notifyObservers();
    }

    public void changeEvent(String title, int startTime, int stopTime,
                            String location,String date){
        super.changeEvent(title,startTime,stopTime,location,date);
        notifyObservers();
        notifySchedule();
    }


    @Override
    public void registerSchedule(ScheduleObserver o) {
        schedules.add(o);
    }


    @Override
    public void removeSchedule(ScheduleObserver o) {
        schedules.remove(o);
    }


    @Override
    public void notifySchedule() {
        for(ScheduleObserver s : schedules){
            s.update();
        }

    }

    @Override
    public void registerObserver(UserObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(UserObserver o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            UserObserver observer = (UserObserver)observers.get(i);
            observer.update(this);

        }
    }
}