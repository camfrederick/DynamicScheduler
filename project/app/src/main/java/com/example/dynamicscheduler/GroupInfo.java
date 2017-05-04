package com.example.dynamicscheduler;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupInfo extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dataRef;
    ArrayAdapter<String> adapter;
    ListView groupList;
    FloatingActionButton addmember;

    Group selected_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info);

        addmember = (FloatingActionButton)findViewById(R.id.add_member);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        String group_name = getIntent().getStringExtra("group_name");



        db.getReference("groups").child(group_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    selected_group = dataSnapshot.getValue(Group.class);

                    selected_group.updateMemberDatabase(dataSnapshot);
                    selected_group.groupEvent = dataSnapshot.child("Event").getValue(EventAdapter.class);
                    adapter = new ArrayAdapter<String>(GroupInfo.this, android.R.layout.simple_list_item_1, selected_group.member_names);
                    groupList.setAdapter(adapter);
                    System.out.print(" ");

                    if(!selected_group.member_names.get(0).equals(user.getEmail()))
                        addmember.setVisibility(View.GONE);
                }catch(Exception e){
                    System.out.print(" ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        groupList = (ListView)findViewById(R.id.member_list);
    }
}
