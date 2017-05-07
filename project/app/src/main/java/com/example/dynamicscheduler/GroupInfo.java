package com.example.dynamicscheduler;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GroupInfo extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dataRef;
    ArrayAdapter<String> adapter;
    ListView groupList;
    FloatingActionButton addmember;
    FloatingActionButton addevent;

    String m_Text;
    String group_name;
    Group selected_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info);

        addmember = (FloatingActionButton)findViewById(R.id.add_member);
        addevent = (FloatingActionButton)findViewById(R.id.add_groupevent);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        group_name = getIntent().getStringExtra("group_name");



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

                    if(selected_group.groupEvent == null)
                        addevent.setVisibility(View.GONE);

                }catch(Exception e){
                    System.out.print(" ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupInfo.this);
                builder.setTitle("Create Group Event: ");

                final TextView groupinfo = new TextView(GroupInfo.this);

                groupinfo.setText("Event: " + selected_group.groupEvent.title + "\n Time: " + selected_group.groupEvent.startTime + " to "
                    + selected_group.groupEvent.stopTime);
                builder.setView(groupinfo);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GoogleCalendarTest.client_user.createEvent(selected_group.groupEvent.title,
                                selected_group.groupEvent.startTime,
                                selected_group.groupEvent.stopTime,
                                selected_group.groupEvent.location,
                                selected_group.groupEvent.date,
                                selected_group.groupEvent.desc);
                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupInfo.this);
                builder.setTitle("Add New Member:");

                final EditText input = new EditText(GroupInfo.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        m_Text = input.getText().toString();

                       // groupEntry.put(m_Text, newgroup);
                        dataRef = db.getReference("users");
                        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    User currentUser = ds.getValue(User.class);
                                    currentUser.updateGroupNames(ds);
                                    if(currentUser.getEmail().equals(m_Text)){
                                        currentUser.group_IDs.add(group_name);
                                        Map<String, Object> userupdate = new HashMap<String, Object>();
                                        userupdate.put(currentUser.getUserID(), currentUser);
                                        db.getReference("users").updateChildren(userupdate);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        dataRef = db.getReference("groups");
                        dataRef.child(group_name).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Group updateGroup = dataSnapshot.getValue(Group.class);
                                updateGroup.updateMemberDatabase(dataSnapshot);
                                updateGroup.member_names.add(m_Text);
                                Map<String, Object> groupEntry = new HashMap<String, Object>();
                                groupEntry.put(group_name, updateGroup);
                                db.getReference("groups").updateChildren(groupEntry);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        groupList.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        groupList = (ListView)findViewById(R.id.member_list);
    }
}
