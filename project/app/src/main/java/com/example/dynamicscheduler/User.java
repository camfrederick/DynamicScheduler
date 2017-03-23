package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */

public abstract class User{
    private String name;
    private String homeaddress;
    private String email;
    private int telephone;
    private Schedule schedule;


    public User(String fullName, String address, String emailAddress, int phoneNum, Schedule sched){
        this.name = fullName;
        this.homeaddress = address;
        this.email = emailAddress;
        this.telephone = phoneNum;
        this.schedule = sched;
    }

    public void optimizeSchedule() {

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

    public int getPhoneNumber() {
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

    public void setPhoneNum(int p) {
        this.telephone = p;
    }

    public abstract void manageGroups();

    public void createEvent(){
    };

}
