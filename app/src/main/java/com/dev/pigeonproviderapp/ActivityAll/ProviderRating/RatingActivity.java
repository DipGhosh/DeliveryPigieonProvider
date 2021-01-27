package com.dev.pigeonproviderapp.ActivityAll.ProviderRating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.dev.pigeonproviderapp.R;

import java.text.DecimalFormat;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout back;
    private RatingBar ratingBar_review;
    private EditText providerComment;
    private Button reviewSubmit;
    private double rating_val;
    private float get_rating_val;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        back=findViewById(R.id.ll_back);
        ratingBar_review=findViewById(R.id.ratingBar);
        providerComment=findViewById(R.id.et_review_comments);
        reviewSubmit=findViewById(R.id.btn_reviewSubmit);

        back.setOnClickListener(this);

        ratingBar_review.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                get_rating_val= ratingBar_review.getRating();
                rating_val= Double.parseDouble(new DecimalFormat("##.#").format(get_rating_val));


            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
               finish();

                break;
            case R.id.btn_reviewSubmit:

                break;

            default:
                break;
        }
    }
}