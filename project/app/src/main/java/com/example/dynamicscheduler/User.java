package com.example.dynamicscheduler;

import java.util.Date;
import java.util.Calendar;
import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Cam on 3/23/2017.
 */

public class User{

    public CreatorBehavior behave;
    private String userID;
    private String name;
    private String homeaddress;
    private String email;
    private String telephone;
    protected Schedule schedule;


    public User(String userID, String email){
        this.userID = userID;
        this.email = email;
    }

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
        // System.out.println(currentDate);
        Hashtable<BusyTime,ArrayList<Event>> table = new Hashtable<BusyTime,ArrayList<Event>>();
        for(Event e: algorithmicAdds){
            if(table.containsKey(e.getBusyTime())){
                ArrayList<Event> btEvents = table.get(e.getBusyTime());
                btEvents.add(e);
                table.put(e.getBusyTime(),btEvents);
            }
            else{
                ArrayList<Event> btEvents = new ArrayList<Event>();
                btEvents.add(e);
                table.put(e.getBusyTime(),btEvents);
            }

        }
        DateTime now = new DateTime(System.currentTimeMillis());

        String complexdate = now.toString();
        String currentDate = parseComplexDate(complexdate);

        for(BusyTime bt : table.keySet()){
            for(Event e: table.get(bt)){
                int starttime = bt.getStarttime();
                int endtime = starttime + e.duration;
                String date = currentDate;
                boolean foundtimeflag = true;
                boolean resetsearchflag = false;
                while(foundtimeflag){
                    for(Event hard : hardAdds){
                        if(timeConflict(hard,starttime,endtime,date)){
                            starttime = hard.getStopTime();
                            endtime = starttime + e.duration;
                            resetsearchflag = true;
                            break;
                        }

                    }
                    if(!resetsearchflag) {
                        foundtimeflag = false;
                    }
                    resetsearchflag = false;
                    if(endtime > bt.getStoptime()){
                        date = addDays(date,1);
                        starttime = bt.getStarttime();
                        endtime = starttime + e.duration;
                    }
                }
                hardAdds.add(e);
                e.changeEvent(e.getTitle(),starttime,endtime,e.getLocation(),date);
            }
        }

    }

    public String addDays(String currentDate, int days){
        int[] dateArray = Event.parseDate(currentDate);
        Calendar c =  Calendar.getInstance();
        c.set(dateArray[2],dateArray[0]-1,dateArray[1]);
        c.add(Calendar.DATE,days);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) +1;
        String month_string = Integer.toString(month);
        if(month_string.length() == 1){
            month_string = "0" + month_string;
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        String date = month_string + "/" + Integer.toString(day) + "/" + Integer.toString(year);
        return date;

    }
    public boolean timeConflict(Event event, int starttime, int endtime,String date){
        if(event.getDate().equals(date)){
            if(event.getStartTime() <= starttime && event.getStopTime()> starttime){
                return true;
            }
            if(event.getStartTime() < endtime && event.getStopTime()>= endtime){
                return true;
            }
        }
        return false;
    }

    public String parseComplexDate(String complexDate){
        int year,month,day;
        // Normalize the input (trim whitespace and make lower case)4
        complexDate = complexDate.trim().toLowerCase();
        complexDate = complexDate.substring(0,complexDate.indexOf('t'));

        int firstSlash = complexDate.indexOf('-');
        if (firstSlash == -1) {
            throw new NumberFormatException("Unrecognized time format");
        }

        int secondSlash = complexDate.indexOf('-', firstSlash+1);
        if (secondSlash == -1) {
            throw new NumberFormatException("Unrecognized time format");
        }

        // Interpret everything up to the first colon as the hour
        year = Integer.valueOf(complexDate.substring(0, firstSlash));
        // Interpret everything between the two colons as the minute
        month = Integer.valueOf(complexDate.substring(firstSlash+1, secondSlash));
        day = Integer.valueOf(complexDate.substring(secondSlash+1, complexDate.length()));
        //this.date = date;
        // Range check the values

        String month_string = Integer.toString(month);
        if(month_string.length() == 1){
            month_string = "0" + month_string;
        }
        return month_string + "/" + Integer.toString(day) + "/" + Integer.toString(year);
    }
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.homeaddress;
    }

    public String getPhoneNumber() {
        return this.telephone;
    }

    public String getUserID() {return this.userID;}

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setAddress(String a) {
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
        event.registerSchedule(schedule);
        optimizeSchedule();

    }

    public void createEvent(String title, int startTime, int stopTime,
                            String location, String date){
       Event event = new Event( title,  startTime,  stopTime,
         location,  date);
        schedule.addEvent(event);
        event.registerSchedule(schedule);
        optimizeSchedule();


    }

    public void createBusyTime(int start, int stop, String repeat, String name){
       BusyTime busytime = new BusyTime( start,  stop,  repeat,  name);
        schedule.addBusyTime(busytime);

    }

}
