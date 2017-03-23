package com.example.dynamicscheduler;

/**
 * Created by jryanofarrell on 3/23/2017.
 */

public interface Observable {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
