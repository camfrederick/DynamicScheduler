package com.example.dynamicscheduler;

import java.util.Date;
import java.util.Calendar;

import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Cam on 3/23/2017.
 */

public class User{

    private String userID;
    private String name;
    private String homeaddress;
    private String email;
    private String telephone;
    ArrayList<String> group_IDs;
    private ArrayList<Group> groups;
    protected Schedule schedule;

    FirebaseDatabase database;
    public User(){

    }
    public User(String userID, String email){
        this.userID = userID;
        this.email = email;
        this.schedule = new Schedule();
    }

    public User(String fullName, String address, String emailAddress, String phoneNum){
        this.name = fullName;
        this.homeaddress = address;
        this.email = emailAddress;
        this.telephone = phoneNum;
        this.schedule = new Schedule();
    }

    public ArrayList<String> getGroup_IDs(){
        return group_IDs;
    }

    public void updateGroupNames(DataSnapshot parent){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dataRef;


        group_IDs = new ArrayList<String>();
        groups = new ArrayList<Group>();

        for(DataSnapshot child : parent.child("group_IDs").getChildren()){
            //String potato = db.getReference("groups").child(child.getValue(String.class));
            dataRef = db.getReference("groups").child(child.getValue(String.class));
            group_IDs.add(child.getValue(String.class));
        }


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
        String currentDate = complexdate.substring(0,complexdate.indexOf("T"));

        for(BusyTime bt : table.keySet()){
            for(Event e: table.get(bt)){
                int starttime = bt.getStarttime();
                int endtime = starttime + e.duration;
                String date = currentDate;
                date = addDays(currentDate,0,bt);
                boolean foundtimeflag = true;
                boolean resetsearchflag = false;
                while(foundtimeflag){
                    for(Event hard : hardAdds){
                        if(timeConflict(hard,starttime,endtime,date)){
                            starttime = hard.getStopTime();
                            endtime = starttime + e.duration;
                            int endtime_hours = endtime/100;
                            int endtime_minutes = endtime - endtime_hours*100;
                            endtime_hours += endtime_minutes/60;
                            endtime_minutes = endtime_minutes%60;
                            endtime = endtime_hours*100 + endtime_minutes;
                            resetsearchflag = true;
                            break;
                        }
                    }
                    if(!resetsearchflag) {
                        foundtimeflag = false;
                    }
                    resetsearchflag = false;
                    if(endtime > bt.getStoptime()){
                        date = addDays(date,1,bt);
                        starttime = bt.getStarttime();
                        endtime = starttime + e.duration;
                    }
                }
                hardAdds.add(e);
                e.changeEvent(e.getTitle(),starttime,endtime,e.getLocation(),date);
            }
        }
    }

    public String addDays(String currentDate, int days, BusyTime tempbt){
        int[] dateArray = Event.parseDate(currentDate);
        Calendar c =  Calendar.getInstance();
        c.set(dateArray[0],dateArray[1]-1,dateArray[2]);
        c.add(Calendar.DATE,days);
        boolean foundvalidday = false;
        while(!foundvalidday){
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            String validdays = tempbt.getDays();
            switch(weekday){
                case Calendar.MONDAY:
                    if(!validdays.contains("M")){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
                case Calendar.TUESDAY:
                    if(!validdays.contains("T") || validdays.charAt(validdays.indexOf('T')+1) == 'h'){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
                case Calendar.WEDNESDAY:
                    if(!validdays.contains("W")){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
                case Calendar.THURSDAY:
                    if(!validdays.contains("Th")){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
                case Calendar.FRIDAY:
                    if(!validdays.contains("F")){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
                case Calendar.SATURDAY:
                    if(!validdays.contains("Sa")){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
                case Calendar.SUNDAY:
                    if(!validdays.contains("Su")){
                        c.add(Calendar.DATE,1);
                    }
                    else{
                        foundvalidday = true;
                    }
                    break;
            }
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) +1;
        String month_string = Integer.toString(month);
        if(month_string.length() == 1){
            month_string = "0" + month_string;
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        String day_string = Integer.toString(day);
        if(day_string.length() == 1){
            day_string = "0" + day_string;
        }
        String date = Integer.toString(year) + "-" + month_string + "-" + day_string;
        return date;

    }
    public boolean timeConflict(Event event, int starttime, int endtime,String date){
        if(event.getDate().equals(date)){
            if((event.getStartTime() <= starttime) && (event.getStopTime() > starttime)){
                return true;
            }
            if((event.getStartTime() < endtime) && (event.getStopTime() >= endtime)){
                return true;
            }
        }
        return false;
    }

//    public String parseComplexDate(String complexDate){
//        int year,month,day;
//        // Normalize the input (trim whitespace and make lower case)4
//        complexDate = complexDate.trim().toLowerCase();
//        complexDate = complexDate.substring(0,complexDate.indexOf('t'));
//
//        int firstSlash = complexDate.indexOf('-');
//        if (firstSlash == -1) {
//            throw new NumberFormatException("Unrecognized time format");
//        }
//
//        int secondSlash = complexDate.indexOf('-', firstSlash+1);
//        if (secondSlash == -1) {
//            throw new NumberFormatException("Unrecognized time format");
//        }
//
//        // Interpret everything up to the first colon as the hour
//        year = Integer.valueOf(complexDate.substring(0, firstSlash));
//        // Interpret everything between the two colons as the minute
//        month = Integer.valueOf(complexDate.substring(firstSlash+1, secondSlash));
//        day = Integer.valueOf(complexDate.substring(secondSlash+1, complexDate.length()));
//        //this.date = date;
//        // Range check the values
//
//        String month_string = Integer.toString(month);
//        if(month_string.length() == 1){
//            month_string = "0" + month_string;
//        }
//        return month_string + "/" + Integer.toString(day) + "/" + Integer.toString(year);
//    }
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
                                           String location,String deadline,BusyTime bt,String desc){
        days = "EveryDay"; // will need to update days later

        Event event = new Event(true,days,title,duration,location,deadline,bt,desc);
        schedule.addEvent(event, true);
        event.registerSchedule(schedule);
        optimizeSchedule();

    }

    public void createEvent(String title, int startTime, int stopTime,
                            String location, String date, String desc){
       Event event = new Event( title,  startTime,  stopTime,
         location,  date, desc);
        schedule.addEvent(event);
        event.registerSchedule(schedule);
        optimizeSchedule();


    }

    Group myGroup;
    public void createEvent(String title, int startTime, int stopTime,
                            String location, String date, String desc, String group){
        final Event event = new Event( title,  startTime,  stopTime,
                location,  date, desc);

        schedule.addEvent(event);
        event.registerSchedule(schedule);
        optimizeSchedule();
        //myGroup = new Group(group);




    }

    public void createBusyTime(int start, int stop, String repeat, String name){
        BusyTime busytime = new BusyTime( start,  stop,  repeat,  name);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        schedule.addBusyTime(busytime);

        Map<String, Object> busyTimeObj = new HashMap<String, Object>();
        busyTimeObj.put("busyTimes", schedule.getBusyTimes());
        try{
        db.getReference("users").child(userID).updateChildren(busyTimeObj);}catch(Exception e){
            int i =5;
        }


    }

    public void addSchedule(List<com.google.api.services.calendar.model.Event> items){
        this.schedule = new Schedule();
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        for (com.google.api.services.calendar.model.Event googleevent : items) {
            schedule.addEvent(googleevent);
        }

        db.getReference("users").child(userID).child("busyTimes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // schedule.busyTimes = dataSnapshot.getValue(BusyTime.class);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    try{schedule.addBusyTime(ds.getValue(BusyTime.class));}catch(Exception e){
                        int i = 5;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void addBusyTime(BusyTime bt){
        schedule.addBusyTime(bt);
    }

}
