package com.example.dynamicscheduler;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageGroups extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dataRef;
    ArrayAdapter<String> adapter;
    ListView groupList;
    FloatingActionButton addgroup;
    String m_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page);

        groupList = (ListView)findViewById(R.id.group_list);
        addgroup = (FloatingActionButton)findViewById(R.id.add_group);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, GoogleCalendarTest.client_user.getGroup_IDs());
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();

        groupList.setAdapter(adapter);

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManageGroups.this, GroupInfo.class);
                intent.putExtra("group_name", (String)parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageGroups.this);
                builder.setTitle("Title");

                final EditText input = new EditText(ManageGroups.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> groupEntry = new HashMap<String, Object>();
                        m_Text = input.getText().toString();

                        Group newgroup = new Group(m_Text);
                        newgroup.member_names.add(user.getEmail());
                        groupEntry.put(m_Text, newgroup);
                        dataRef = db.getReference("groups");
                        dataRef.updateChildren(groupEntry);


                        dataRef = db.getReference("users");
                        GoogleCalendarTest.client_user.group_IDs.add(m_Text);
                        Map<String, Object> userupdate = new HashMap<String, Object>();
                        userupdate.put(user.getUid(), GoogleCalendarTest.client_user);
                        dataRef.updateChildren(userupdate);

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
    }


}
