package com.example.dynamicscheduler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ManageGroups extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        displayGroups();
    }

    private void displayGroups(){
        dataRef = db.getReference("users");
        dataRef = dataRef.child(user.getUid()).child("groups");

        Query myGroups = dataRef.orderByValue();

        //myGroups.
    }
}
