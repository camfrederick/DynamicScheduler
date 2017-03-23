package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */
//import java.util.Observable;
//import java.util.Observer;
import java.util.ArrayList;
public interface Event {
    String title = "";
    int startTime = 0;
    int stopTime = 0;
    String location = "";
    String deadline = "";
    int flexStart = 0;
    int flexStop = 0;
    String days = "";

//    public Event(String title, int startTime, int stopTime,
//                String location, String deadline, int flexStart,
//                int timeEst, String days);
    String getTitle();
    String getLocation();
    int getStartTime();
    int getStopTime();

}

class privateEvent implements Event{


    public privateEvent() {
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getStartTime() {
        return startTime;
    }

    @Override
    public int getStopTime() {
        return stopTime;
    }
}

//interface DisplayElement {  public void display();
//}

class groupEvent implements Observable, Event{
    private ArrayList observers;

    public groupEvent() {
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getStartTime() {
        return startTime;
    }

    @Override
    public int getStopTime() {
        return stopTime;
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