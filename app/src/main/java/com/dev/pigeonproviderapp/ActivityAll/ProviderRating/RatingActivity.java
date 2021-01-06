package com.dev.pigeonproviderapp.ActivityAll.ProviderRating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.dev.pigeonproviderapp.R;

public class RatingActivity extends AppCompatActivity {
    private LinearLayout back;
    private RatingBar ratingBar_review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        back=findViewById(R.id.ll_back);
        ratingBar_review=(RatingBar) findViewById(R.id.ratingBar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}