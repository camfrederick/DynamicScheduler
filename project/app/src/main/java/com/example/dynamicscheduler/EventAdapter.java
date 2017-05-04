package com.example.dynamicscheduler;

/**
 * Created by Rohan Konde on 5/4/2017.
 */


public class EventAdapter {

    int month;
    int day;
    int year;

    String date;
    String title = "";
    int startTime = 0;
    int stopTime = 0;
    String location = "";
    String desc = "";

    public EventAdapter(){

    }

    public EventAdapter(String title, int startTime, int stopTime,
                        String location, String date,String desc){
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

    public Event makeEvent(EventAdapter event){
        return new Event(event.title, event.startTime, event.stopTime, event.location, event.date, event.desc);
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
}
