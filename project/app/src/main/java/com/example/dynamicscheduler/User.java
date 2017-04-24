package com.example.dynamicscheduler;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Cam on 3/23/2017.
 */

public class User{

    public CreatorBehavior behave;
    private String name;
    private String homeaddress;
    private String email;
    private String telephone;
    protected Schedule schedule;


    public User(String fullName, String address, String emailAddress, String phoneNum){
        this.name = fullName;
        this.homeaddress = address;
        this.email = emailAddress;
        this.telephone = phoneNum;
        this.schedule = new Schedule();
        behave = new CreateForSelf();
    }

    public void optimizeSchedule() {
        ArrayList<Event> algorithmicAdds = new ArrayList<Event>();
        ArrayList<Event> hardAdds = new ArrayList<Event>();
        for(Event e : schedule.getEvents()){
            if(e.getAlgorithmicAdd())
                algorithmicAdds.add(e);
            else
                hardAdds.add(e);
        }
        String currentDate = "4/24/2017"; //TODO: figure out how to find current date
        Hashtable<BusyTime,ArrayList<Event>> table = new Hashtable<BusyTime,ArrayList<Event>>();
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAdress() {
        return this.homeaddress;
    }

    public String getPhoneNumber() {
        return this.telephone;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setAdress(String a) {
        this.homeaddress = a;
    }

    public void setPhoneNum(String p) {
        this.telephone = p;
    }

    public void createEventAlgorithmically(String days, String title,int duration,
                                           String location,String deadline,BusyTime bt){
        days = "EveryDay"; // will need to update days later

        Event event = new Event(true,days,title,duration,location,deadline,bt);
        schedule.addEvent(event);
        optimizeSchedule();

    }

    public void createEvent(String title, int startTime, int stopTime,
                            String location, String date){
       Event event = new Event( title,  startTime,  stopTime,
         location,  date);
        schedule.addEvent(event);
        event.registerSchedule(schedule);


    }

    public void createBusyTime(int start, int stop, String repeat, String name){
       BusyTime busytime = new BusyTime( start,  stop,  repeat,  name);
        schedule.addBusyTime(busytime);

    }

}
