package com.example.dynamicscheduler;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Rohan Konde on 4/29/2017.
 */

public class CurrentAccount {
    private static GoogleSignInAccount google_acct;
    private static FirebaseUser firebase_acct;

    public void setGoogle_acct(GoogleSignInAccount acct){google_acct = acct;}
    public void setFirebase_acct(FirebaseUser acct){firebase_acct = acct;}

    public GoogleSignInAccount getGoogle_acct(){return google_acct;}
    public FirebaseUser getFirebase_acct(){return firebase_acct;}
}
