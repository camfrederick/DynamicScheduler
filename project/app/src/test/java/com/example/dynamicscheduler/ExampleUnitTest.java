package com.example.dynamicscheduler;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void createEventTest(){
        User user = new User("tester","123madeup","jryanofarrell@gmail.com","5128978944");
        user.createEvent("shopping",1600,1700,"HEB","4/24/2017");
        user.createEvent("eating",1900,2000,"Home","4/24/2017");
        user.createEvent("studying",2000,2200,"Home","4/24/2017");

        ArrayList<Event> eventlist = user.getSchedule().getEvents();
        assertEquals(eventlist.get(0).getStartTime(),1600);
        assertEquals(eventlist.get(0).getStopTime(),1700);
        assertEquals(eventlist.get(1).getTitle(),"eating");
        assertEquals(eventlist.get(1).getLocation(),"Home");
        assertEquals(eventlist.get(2).day,24);
        assertEquals(eventlist.get(2).month,4);
        assertEquals(eventlist.get(2).year,2017);
    }
}