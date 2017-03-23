package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */
//import java.util.Observable;
//import java.util.Observer;
import java.util.ArrayList;
//public abstract class Event {
//    String title = "";
//    int startTime = 0;
//    int stopTime = 0;
//    String location = "";
//    String deadline = "";
//    int flexStart = 0;
//    int flexStop = 0;
//    String days = "";
//
////    public Event(String title, int startTime, int stopTime,
////                String location, String deadline, int flexStart,
////                int timeEst, String days);
//    String getTitle();
//    String getLocation();
//    int getStartTime();
//    int getStopTime();
//
//}

public class Event implements ScheduleObservable {
    ScheduleObserver schedule = null;
    String title = "";
    int startTime = 0;
    int stopTime = 0;
    String location = "";
    String deadline = "";
    int flexStart = 0;
    int flexStop = 0;
    String days = "";

    public Event(String title, int startTime, int stopTime,
                 String location) {
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;
        notifySchedule();
    }


    public String getTitle() {
        return title;
    }


    public String getLocation() {
        return location;
    }


    public int getStartTime() {
        return startTime;
    }
    
    public int getStopTime() {
        return stopTime;
    }

    public void changeEvent(String title, int startTime, int stopTime,
                            String location){
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;
        notifySchedule();
    }

    @Override
    public void registerSchedule(ScheduleObserver o) {
        schedule = o;
    }

    @Override
    public void removeSchedule(ScheduleObserver o) {
        schedule = null;
    }

    @Override
    public void notifySchedule() {
        schedule.update();
    }
}

//interface DisplayElement {  public void display();
//}

class groupEvent extends Event implements UserObservable{
    private ArrayList<UserObserver> observers;

    public groupEvent(String title, int startTime, int stopTime,
                      String location) {
        super(title,startTime,stopTime,location);
        notifyObservers();

    }

    public void changeEvent(String title, int startTime, int stopTime,
                            String location){
        super.changeEvent(title,startTime,stopTime,location);
        notifyObservers();
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
            observer.update(startTime, stopTime, title,location);
        }
    }
}