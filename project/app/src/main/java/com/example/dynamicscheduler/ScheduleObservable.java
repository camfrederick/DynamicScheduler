package com.example.dynamicscheduler;

/**
 * Created by jryanofarrell on 3/23/2017.
 */

public interface ScheduleObservable {
    public void registerSchedule(ScheduleObserver o);
    public void removeSchedule(ScheduleObserver o);
    public void notifySchedule();
}
