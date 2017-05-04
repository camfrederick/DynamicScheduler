package com.example.dynamicscheduler;

import android.util.Log;

/**
 * Created by Cam on 3/23/2017.
 */


public class Event implements ScheduleObservable {
    // YYYY-MM-DD
    int deadlineMonth;
    int deadlineDay;
    int deadlineYear;
    int month;
    int day;
    int year;
    ScheduleObserver schedule = null;

    String date;
    String title = "";
    int startTime = 0;
    int stopTime = 0;
    String location = "";
    String desc = "";

    int duration;
    boolean algorithmicAdd;
    String deadline = "";
    BusyTime busyTime;
    String days = "";

    private static final int MIN_DAY = 0;
    private static final int MAX_DAY = 31;
    private static final int MIN_MONTH = 0;
    private static final int MAX_MONTH = 12;
    private static final int MIN_YEAR = 0000;
    private static final int MAX_YEAR = 9999;

    public Event(String title, int startTime, int stopTime,
                 String location, String date,String desc) {

        algorithmicAdd = false;
        this.desc = desc;
        this.date = date;
        int[] dateArray = parseDate(date);
        this.year = dateArray[0];
        this.month = dateArray[1];
        this.day = dateArray[2];
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;


    }

    public Event(boolean alg, String days, String title,int duration,String location,String deadline,BusyTime bt, String desc) {

        this.desc = desc;
        algorithmicAdd = alg;
        this.deadline = deadline;
        this.duration = duration;
        int[] dateArray = parseDate(deadline);
        this.deadlineYear = dateArray[0];
        this.deadlineMonth = dateArray[1];
        this.deadlineDay = dateArray[2];
        this.title = title;
        this.busyTime = bt;
        this.location = location;

    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
    public String getLocation() {
        return location;
    }

    public void setAlgorithmicAdd(boolean add){
        algorithmicAdd = add;
    }

    public boolean getAlgorithmicAdd(){
        return algorithmicAdd;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public BusyTime getBusyTime(){
        return busyTime;
    }
    public String getDate(){
        return date;
    }

    public void changeEvent(String title, int startTime, int stopTime,
                            String location,String date){
        Schedule sc = (Schedule) schedule;
        //sc.removeEvent(this);
        this.date = date;
        int[] dateArray = parseDate(date);
        this.year = dateArray[0];
        this.month = dateArray[1];
        this.day = dateArray[2];
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;
        sc.addEvent(this); 
    }


    public static int[] parseDate(String date){
        int[] dateArray = new int[3];
        // Normalize the input (trim whitespace and make lower case)4
        date = date.trim().toLowerCase();

        int firstSlash = date.indexOf('-');
        if (firstSlash == -1) {
            throw new NumberFormatException("Unrecognized time format");
        }

        int secondSlash = date.indexOf('-', firstSlash+1);
        if (secondSlash == -1) {
            throw new NumberFormatException("Unrecognized time format");
        }

        // Interpret everything up to the first colon as the hour
        dateArray[0] = Integer.valueOf(date.substring(0, firstSlash));
        // Interpret everything between the two colons as the minute
        dateArray[1] = Integer.valueOf(date.substring(firstSlash+1, secondSlash));
        // Interpret the two characters after the second colon as the seconds
        dateArray[2] = Integer.valueOf(date.substring(secondSlash+1, secondSlash+3));
        //this.date = date;
        // Range check the values

//        if ((dateArray[] < MIN_MONTH || dateArray[0] > MAX_MONTH) ||
//                (dateArray[1] < MIN_DAY || dateArray[1] > MAX_DAY) ||
//                (dateArray[2] < MIN_YEAR || dateArray[2] > MAX_YEAR)) {
//            throw new IllegalArgumentException("Unacceptable date specified");
//        }
        return dateArray;
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

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setStopTime(int stopTime){
        this.stopTime = stopTime;
    }
}