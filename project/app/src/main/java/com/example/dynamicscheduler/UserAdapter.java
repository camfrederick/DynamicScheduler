package com.example.dynamicscheduler;

/**
 * Created by Rohan Konde on 4/30/2017.
 */

public class UserAdapter extends User {
    public String email;
    public String uID;

    public UserAdapter(String email, String uID){
        super(uID, email);
        this.email = super.getEmail();
        this.uID = super.getUserID();
    }
}
