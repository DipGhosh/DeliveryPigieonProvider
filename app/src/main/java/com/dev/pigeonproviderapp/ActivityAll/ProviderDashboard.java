package com.dev.pigeonproviderapp.ActivityAll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ItemDetailsActivity;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Fragment.SupportFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.GPSTracker;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.LocationDatamodel;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.LocationRequestSendModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.LocationSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProviderDashboard extends AppCompatActivity  {

    private static int loginAttempt = 0;
    BottomNavigationView navView;
    AppBarConfiguration appBarConfiguration;
    GPSTracker gpsTracker;
    double provider_lat, provider_long;
    LocationSendViewModel locationSendViewModel;
    private Activity activity = ProviderDashboard.this;
    private SharePreference sharePreference;
    private String mobileNumber;
    //Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String chatEmail;
    private String chatpassword;
    private int mMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_dashboard);
        navView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_order, R.id.navigation_suport, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

   /* navView.setOnNavigationItemSelectedListener(this);
    navView.getMenu().findItem(R.id.navigation_profile).setChecked(true);*/
        locationSendViewModel = ViewModelProviders.of(this).get(LocationSendViewModel.class);

        sharePreference = new SharePreference(ProviderDashboard.this);
        mobileNumber = sharePreference.getMobileNumber();

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        chatEmail = mobileNumber + "_2@pigeon.in";
        chatpassword = mobileNumber;

        try {
            // registeration and login
            chatLogin();

        } catch (IllegalArgumentException ex) {
            Log.d("Aslam", "ex: " + ex.getMessage());
        }

    }


    private void chatRegisteration() {

        mFirebaseAuth.createUserWithEmailAndPassword(chatEmail, chatpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("chat", "Registeration success");

                            String uid = mFirebaseAuth.getCurrentUser().getUid();
                            String emailN = chatEmail;
                            String fname = chatpassword;
                            String lname = chatpassword;

                            HashMap<String, Object> hashmap = new HashMap<>();
                            hashmap.put("uid", uid);
                            hashmap.put("email", emailN);
                            hashmap.put("fname", fname);
                            hashmap.put("lname", lname);

                            DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance()
                                    .getReference();
                            mFirebaseDatabaseReference.child(SupportFrag.MESSAGES_USERS).push().setValue(hashmap);

                            chatLogin();

                        } else {
                            Log.d("chat", "Registeration failed");
                            chatLogin();
                        }
                    }
                });


    }


    private void chatLogin() {
        loginAttempt++;
        Log.d("Aslam", "chatEmail: " + chatEmail + " chatpassword: " + chatpassword);
        mFirebaseAuth.signInWithEmailAndPassword(chatEmail, chatpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("chat", "Login success");
                        } else {
                            Log.d("chat", "Login failed");

                            if (loginAttempt < 5) {
                                chatRegisteration();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // navigate to profile
        if (Singleton.getInstance().isProfileUpdated()) {
            View view = navView.findViewById(R.id.navigation_profile);
            view.performClick();
        }

        if (Singleton.getInstance().getMessageType().equals("support"))
        {
            View viewsupport = navView.findViewById(R.id.navigation_suport);
            viewsupport.performClick();
            Singleton.getInstance().setMessageType("");
        }

    }


}