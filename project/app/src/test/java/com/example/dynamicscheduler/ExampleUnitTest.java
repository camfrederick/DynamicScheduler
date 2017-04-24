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

        eventlist.get(0).changeEvent("shopping", 1400, 1600, "HEB", "4/24/2017");
        eventlist.get(1).changeEvent("dinner with Ryan", 1900, 2000, "Canes", "4/24/2017");
        eventlist.get(2).changeEvent("studying", 2000, 2200, "Home", "4/25/2017");

        assertEquals(eventlist.get(0).getStartTime(), 1400);
        assertEquals(eventlist.get(0).getStopTime(), 1600);
        assertEquals(eventlist.get(1).getTitle(), "dinner with Ryan");
        assertEquals(eventlist.get(1).getLocation(), "Canes");
        assertEquals(eventlist.get(2).day, 25);
    }

    @Test
    public void createGroupEventTest(){
        Member user1 = new Member("Alice","123 Rio Grande","alice@gmail.com","5128978944");
        Member user2 = new Member("Blake","234 Pearl","blake@gmail.com","5128453434");
        Member user3 = new Member("Casey","123 Nueces","casey@gmail.com","5125551234");
        ArrayList<Member> members = new ArrayList<Member>();
        Group g = new Group(members, user3, 900, 1500, "Project");
        members.add(user2);
        members.add(user1);
        g.addMembers(members);

        user3.createGroupEvent(g, "Work on Project", 1200, 1400, "PCL", "4/25/2017");

        assertEquals(user2.getSchedule().getEvents().get(0).getStartTime(), 1200);
        assertEquals(user1.getSchedule().getEvents().get(0).getTitle(), "Work on Project");
        assertEquals(user3.getSchedule().getEvents().get(0).getStopTime(),  1400);

        GroupEvent gevent = (GroupEvent)user3.getSchedule().getEvents().get(0);
        gevent.changeEvent("Project meeting", 1300, 1500, "PCL", "4/26/2017");

        assertEquals(user2.getSchedule().getEvents().get(0).getStartTime(), 1300);
        assertEquals(user1.getSchedule().getEvents().get(0).getTitle(), "Project meeting");
        assertEquals(user3.getSchedule().getEvents().get(0).getStopTime(),  1500);
        assertEquals(user2.getSchedule().getEvents().get(0).day, 26);
    }
}