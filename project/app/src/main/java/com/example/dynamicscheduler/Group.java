package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */

import java.util.ArrayList;

public class Group {

    ArrayList<Member> members;
    Admin admin;
    int startShift;
    int stopShift;

    public Group(ArrayList<Member> m, Admin a, int start, int stop) {
        this.members = m;
        this.admin = a;
        this.startShift = start;
        this.stopShift = stop;
    }

    public Admin getAdmin(){
        return this.admin;
    }

    public ArrayList<Member> getMemberList() {
        return this.members;
    }

    public int getStartShift() {
        return startShift;
    }

    public int getStopShift() {
        return stopShift;
    }

    public void changeAdmin(Admin a) {
        this.admin = a;
    }

    public void addMembers(ArrayList<Member> newMems) {
        for(int i = 0; i < newMems.size(); i++)
            this.members.add(newMems.get(i));
    }

    public void removeMembers(Member i) {
        for(int x = 0; x < members.size(); x++) {
            if(members.get(x).equals(i))
                members.remove(x);
        }
    }
    
    public void setStartShift(int start) {
        this.startShift = start;
    }

    public void setStopShift(int stop) {
        this.stopShift = stop;
    }
}
