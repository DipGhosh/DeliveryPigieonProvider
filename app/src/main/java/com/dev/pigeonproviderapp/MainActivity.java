package com.dev.pigeonproviderapp;

import android.os.Bundle;
import com.dev.pigeonproviderapp.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}