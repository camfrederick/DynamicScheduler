package com.example.dynamicscheduler;

/**
 * Created by Cam on 3/23/2017.
 */

import android.view.View;
import android.widget.ArrayAdapter;

import com.google.api.client.util.Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Group {

    String group_name;
    ArrayList<Member> members = new ArrayList<Member>();
    ArrayList<String> member_names;
    Member admin;
    int startShift;
    int stopShift;

    EventAdapter groupEvent;

    public Group(){

    }

    public Group(String group_name){
        this.group_name = group_name;
        member_names = new ArrayList<String>();
    }

    public Group(ArrayList<Member> m, Member a, int start, int stop, String group_name) {
        this.group_name = group_name;
        if(m != null)
            this.members.addAll(m);
        this.members.add(a);
        this.admin = a;
        this.startShift = start;
        this.stopShift = stop;
    }




    public void updateMemberDatabase(DataSnapshot parent){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dataRef;


        member_names = new ArrayList<String>();


        for(DataSnapshot child : parent.child("member_names").getChildren()){
            //String potato = db.getReference("groups").child(child.getValue(String.class));
            
            String memname = child.getValue(String.class);
            member_names.add(child.getValue(String.class));
        }


    }

    public Member getAdmin(){
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

    public void changeAdmin(Member a) {
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
