package com.dev.pigeonproviderapp.ActivityAll;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.dev.pigeonproviderapp.Fragment.SupportFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class ProviderDashboard extends AppCompatActivity {

  private static int loginAttempt = 0;
  BottomNavigationView navView;
  AppBarConfiguration appBarConfiguration;
  private SharePreference sharePreference;
  private String mobileNumber;
  //Firebase instance variables
  private FirebaseAuth mFirebaseAuth;
  private FirebaseUser mFirebaseUser;
  private String chatEmail = mobileNumber + "_2@pigeon.in";
  private String chatpassword = mobileNumber;

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

    sharePreference = new SharePreference(ProviderDashboard.this);
    mobileNumber = sharePreference.getMobileNumber();

    // Initialize Firebase Auth
    mFirebaseAuth = FirebaseAuth.getInstance();

    chatEmail = mobileNumber + "_2@pigeon.in";
    chatpassword = mobileNumber;

    // registeration and login
    chatLogin();

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
}