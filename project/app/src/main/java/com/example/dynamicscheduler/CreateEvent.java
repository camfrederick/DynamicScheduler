package com.example.dynamicscheduler;

import android.accounts.Account;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class CreateEvent extends AppCompatActivity {
    private EditText event_location;
    private EditText event_date;
    private EditText event_name;
    private EditText event_desc;

    private Button create_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        event_location = (EditText)findViewById(R.id.ce_setlocation);
        event_date = (EditText)findViewById(R.id.ce_setlocation);
        event_name = (EditText)findViewById(R.id.ce_setlocation);
        event_desc = (EditText)findViewById(R.id.ce_setlocation);

        create_event = (Button)findViewById(R.id.ce_insert);

        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });

//        mCredential = GoogleAccountCredential.usingOAuth2(
//                getApplicationContext(), Arrays.asList(SCOPES))
//                .setBackOff(new ExponentialBackOff());
//
//        Account[] mC_Accts = mCredential.getAllAccounts();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        mCredential.setSelectedAccountName(user.getEmail());

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
