package com.example.dynamicscheduler;

/**
 * Created by jryanofarrell on 3/23/2017.
 */

public interface UserObservable {
    public void registerObserver(UserObserver o);
    public void removeObserver(UserObserver o);
    public void notifyObservers();
}
