package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */
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
    // day/month/year  XX/XX/XXXX
    String date;
    int day;
    int month;
    int year;
    ScheduleObserver schedule = null;
    String title = "";
    int startTime = 0;
    int stopTime = 0;
    String location = "";
    String deadline = "";
    int flexStart = 0;
    int flexStop = 0;
    String days = "";

    private static final int MIN_DAY = 0;
    private static final int MAX_DAY = 31;
    private static final int MIN_MONTH = 0;
    private static final int MAX_MONTH = 12;
    private static final int MIN_YEAR = 0000;
    private static final int MAX_YEAR = 9999;

    public Event(String title, int startTime, int stopTime,
                 String location, String date) {
        // Normalize the input (trim whitespace and make lower case)4
        date = date.trim().toLowerCase();

        int firstSlash = date.indexOf('/');
        if (firstSlash == -1) {
            throw new NumberFormatException("Unrecognized time format");
        }

        int secondSlash = date.indexOf('/', firstSlash+1);
        if (secondSlash == -1) {
            throw new NumberFormatException("Unrecognized time format");
        }

        // Interpret everything up to the first colon as the hour
        day = Integer.valueOf(date.substring(0, firstSlash));
        // Interpret everything between the two colons as the minute
        month = Integer.valueOf(date.substring(firstSlash+1, secondSlash));
        // Interpret the two characters after the second colon as the seconds
        year = Integer.valueOf(date.substring(secondSlash+1, secondSlash+5));
        this.date = date;
        // Range check the values

        if ((day < MIN_DAY || day > MAX_DAY) ||
                (month < MIN_MONTH || month > MAX_MONTH) ||
                (year < MIN_YEAR || year > MAX_YEAR)) {
            throw new IllegalArgumentException("Unacceptable date specified");
        }
        // Calculate number of seconds since midnight
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
    private Group group;

    public groupEvent(Group g, String title, int startTime, int stopTime,
                      String location,String date) {
        super(title,startTime,stopTime,location,date);
        group = g;
        for(Member member : g.getMemberList()){
            registerObserver(member);
        }
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
            observer.update(this);
        }
    }
}