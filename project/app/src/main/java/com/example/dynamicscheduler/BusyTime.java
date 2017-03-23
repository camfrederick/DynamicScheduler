package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */

public class BusyTime {

    private int starttime, stoptime;
    private String days, title;

    public BusyTime(){

    }

    public BusyTime(int start, int stop, String repeat, String name){
        starttime = start;
        stoptime = stop;
        days = repeat;
        title = name;
    }

    public int getStarttime(){
        return starttime;
    }

    public int getStoptime(){
        return stoptime;
    }

    public String getDays(){
        return days;
    }

    public String getTitle(){
        return title;
    }

    public void setStarttime(int start){
        starttime = start;
    }

    public void setStoptime(int stop){
        stoptime = stop;
    }

    public void setDays(String repeat){
        days = repeat;
    }

    public void setTitle(String name){
        title = name;
    }
}
