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
    // mont/day/year  XX/XX/XXXX
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

    boolean algorithmicAdd;
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

        algorithmicAdd = false;
        this.date = date;
        int[] dateArray = parseDate(date);
        this.month = dateArray[0];
        this.day = dateArray[1];
        this.year = dateArray[2];
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;


    }

    public Event(boolean alg, String days, String title,int flexStart,
            int flexStop,String location,String deadline) {


        algorithmicAdd = alg;
        this.deadline = deadline;
        int[] dateArray = parseDate(date);
        this.deadlineMonth = dateArray[0];
        this.deadlineDay = dateArray[1];
        this.deadlineYear = dateArray[2];
        this.title = title;
        this.flexStart = flexStart;
        this.flexStop = flexStop;
        this.location = location;

    }

    public String getTitle() {
        return title;
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

    public void changeEvent(String title, int startTime, int stopTime,
                            String location,String date){

        this.date = date;
        int[] dateArray = parseDate(date);
        this.month = dateArray[0];
        this.day = dateArray[1];
        this.year = dateArray[2];
        this.title = title;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;
        notifySchedule();
    }

    public int[] parseDate(String date){
        int[] dateArray = new int[3];
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
        dateArray[0] = Integer.valueOf(date.substring(0, firstSlash));
        // Interpret everything between the two colons as the minute
        dateArray[1] = Integer.valueOf(date.substring(firstSlash+1, secondSlash));
        // Interpret the two characters after the second colon as the seconds
        dateArray[2] = Integer.valueOf(date.substring(secondSlash+1, secondSlash+5));
        this.date = date;
        // Range check the values

        if ((dateArray[0] < MIN_MONTH || dateArray[0] > MAX_MONTH) ||
                (dateArray[1] < MIN_DAY || dateArray[1] > MAX_DAY) ||
                (dateArray[2] < MIN_YEAR || dateArray[2] > MAX_YEAR)) {
            throw new IllegalArgumentException("Unacceptable date specified");
        }
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
}