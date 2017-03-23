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

public class Event{
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
    }
}

//interface DisplayElement {  public void display();
//}

class groupEvent extends Event implements Observable{
    private ArrayList observers;

    public groupEvent(String title, int startTime, int stopTime,
                      String location) {
        super(title,startTime,stopTime,location);
        notifyObservers();

    }

    public void changeEvent(String title, int startTime, int stopTime,
                            String location){
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer)observers.get(i);
            observer.update(startTime, stopTime, title,location);
        }
    }
}