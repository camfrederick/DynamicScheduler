package com.example.dynamicscheduler;


import com.google.api.client.util.DateTime;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void createEventTest(){
        DateTime now = new DateTime(System.currentTimeMillis());
        String complexdate = now.toString();
        String currentDate = complexdate.substring(0,complexdate.indexOf("T"));

        User user = new User("tester","123madeup","jryanofarrell@gmail.com","5128978944");
        user.createEvent("shopping",1600,1700,"HEB",currentDate);
        user.createEvent("eating",1900,2000,"Home",currentDate);
        user.createEvent("studying",2000,2200,"Home",currentDate);

        ArrayList<Event> eventlist = user.getSchedule().getEvents();
        assertEquals(eventlist.get(0).getStartTime(),1600);
        assertEquals(eventlist.get(0).getStopTime(),1700);
        assertEquals(eventlist.get(1).getTitle(),"eating");
        assertEquals(eventlist.get(1).getLocation(),"Home");
        assertEquals(eventlist.get(2).day,parseDate(currentDate)[2]);
        assertEquals(eventlist.get(2).month,parseDate(currentDate)[1]);
        assertEquals(eventlist.get(2).year,parseDate(currentDate)[0]);

        eventlist.get(0).changeEvent("shopping", 1400, 1600, "HEB", currentDate);
        eventlist.get(1).changeEvent("dinner with Ryan", 1900, 2000, "Canes", currentDate);
        eventlist.get(2).changeEvent("studying", 2000, 2200, "Home", addDays(currentDate, 1));

        assertEquals(eventlist.get(0).getStartTime(), 1400);
        assertEquals(eventlist.get(0).getStopTime(), 1600);
        assertEquals(eventlist.get(1).getTitle(), "dinner with Ryan");
        assertEquals(eventlist.get(1).getLocation(), "Canes");
        assertEquals(eventlist.get(2).day, parseDate(addDays(currentDate, 1))[2]);
    }

    @Test
    public void createGroupEventTest(){
        DateTime now = new DateTime(System.currentTimeMillis());
        String complexdate = now.toString();
        String currentDate = complexdate.substring(0,complexdate.indexOf("T"));

        Member user1 = new Member("Alice","123 Rio Grande","alice@gmail.com","5128978944");
        Member user2 = new Member("Blake","234 Pearl","blake@gmail.com","5128453434");
        Member user3 = new Member("Casey","123 Nueces","casey@gmail.com","5125551234");
        ArrayList<Member> members = new ArrayList<Member>();
        Group g = new Group(members, user3, 900, 1500, "Project");
        members.add(user2);
        members.add(user1);
        g.addMembers(members);

        user3.createGroupEvent(g, "Work on Project", 1200, 1400, "PCL", currentDate);

        assertEquals(user2.getSchedule().getEvents().get(0).getStartTime(), 1200);
        assertEquals(user1.getSchedule().getEvents().get(0).getTitle(), "Work on Project");
        assertEquals(user3.getSchedule().getEvents().get(0).getStopTime(),  1400);

        GroupEvent gevent = (GroupEvent)user3.getSchedule().getEvents().get(0);
        gevent.changeEvent("Project meeting", 1300, 1500, "PCL", addDays(currentDate,1));

        assertEquals(user2.getSchedule().getEvents().get(0).getStartTime(), 1300);
        assertEquals(user1.getSchedule().getEvents().get(0).getTitle(), "Project meeting");
        assertEquals(user3.getSchedule().getEvents().get(0).getStopTime(),  1500);
        assertEquals(user2.getSchedule().getEvents().get(0).day, parseDate(addDays(currentDate,1))[2]);
    }

    @Test
    public void createBusyTimeTest(){
        User user = new User("tester","123madeup","jryanofarrell@gmail.com","5128978944");

        user.createBusyTime(1400,1800,"EveryDay","HomeWork");
        user.createBusyTime(1000,1100,"EveryDay","School");
        user.createBusyTime(1900,2100,"EveryDay","FreeTime");

        ArrayList<BusyTime> busylist = user.getSchedule().getBusyTimes();
        assertEquals(busylist.get(0).getStarttime(),1400);
        assertEquals(busylist.get(1).getTitle(),"School");
        assertEquals(busylist.get(1).getStarttime(),1000);
        assertEquals(busylist.get(2).getDays(),"EveryDay");
        assertEquals(busylist.get(2).getStoptime(),2100);
    }

    @Test
    public void createAlgorithmicScheduleTest(){
        User user = new User("tester","123madeup","jryanofarrell@gmail.com","5128978944");

        user.createBusyTime(1400,1800,"EveryDay","HomeWork");
        user.createBusyTime(1900,2100,"EveryDay","FreeTime");

        ArrayList<BusyTime> busylist = user.getSchedule().getBusyTimes();

        BusyTime hwBusyTime = busylist.get(0);
        BusyTime freetimeBusyTime = busylist.get(1);
        DateTime now = new DateTime(System.currentTimeMillis());

        String complexdate = now.toString();
        String currentDate = complexdate.substring(0,complexdate.indexOf("T"));

        user.createEventAlgorithmically("EveryDay","HW1", 300, "home", addDays(currentDate,5), hwBusyTime);
        user.createEvent("meeting",1400,1500,"home",currentDate);
        user.createEventAlgorithmically("EveryDay","HW2", 200, "home", addDays(currentDate,5), hwBusyTime);
        user.createEvent("meeting",1400,1600,"home",addDays(currentDate,1));

        user.createEventAlgorithmically("EveryDay","Movie", 200, "home", addDays(currentDate,5), freetimeBusyTime);
        user.createEvent("meeting",1900,2000,"home",currentDate);


        assertEquals(user.getSchedule().getEvents().get(0).getTitle(), "HW1");
        assertEquals(user.getSchedule().getEvents().get(0).day, parseDate(currentDate)[2]);
        assertEquals(user.getSchedule().getEvents().get(0).getStartTime(), 1500);
        assertEquals(user.getSchedule().getEvents().get(0).getStopTime(),  1800);
        assertEquals(user.getSchedule().getEvents().get(2).getTitle(), "HW2");
        assertEquals(user.getSchedule().getEvents().get(2).getStartTime(), 1600);
        assertEquals(user.getSchedule().getEvents().get(2).getStopTime(),  1800);
        assertEquals(user.getSchedule().getEvents().get(2).day, parseDate(addDays(currentDate,1))[2]);
        assertEquals(user.getSchedule().getEvents().get(3).getStartTime(), 1400);
        assertEquals(user.getSchedule().getEvents().get(3).getStopTime(), 1600);

        assertEquals(user.getSchedule().getEvents().get(4).getStartTime(), 1900);
        assertEquals(user.getSchedule().getEvents().get(4).getStopTime(), 2100);
        assertEquals(user.getSchedule().getEvents().get(4).day, parseDate(addDays(currentDate,1))[2]);

        assertEquals(user.getSchedule().getEvents().get(5).getStartTime(), 1900);
        assertEquals(user.getSchedule().getEvents().get(5).getStopTime(), 2000);
        assertEquals(user.getSchedule().getEvents().get(5).day, parseDate(currentDate)[2]);


    }

    public String addDays(String currentDate, int days){
        int[] dateArray = Event.parseDate(currentDate);
        Calendar c =  Calendar.getInstance();
        c.set(dateArray[0],dateArray[1]-1,dateArray[2]);
        c.add(Calendar.DATE,days);
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
        return dateArray;
    }
}