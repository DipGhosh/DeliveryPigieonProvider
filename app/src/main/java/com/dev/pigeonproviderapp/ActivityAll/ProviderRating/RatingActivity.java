package com.dev.pigeonproviderapp.ActivityAll.ProviderRating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ItemDetailsActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OrderRatingAPIModel;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;

import java.text.DecimalFormat;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener {

    OrderListViewModel orderListViewModel;
    private Activity activity = RatingActivity.this;
    private LinearLayout back;
    private RatingBar ratingBar_review;
    private EditText providerComment;
    private Button reviewSubmit;
    private double rating_val;
    private float get_rating_val;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        back = findViewById(R.id.ll_back);
        ratingBar_review = findViewById(R.id.ratingBar);
        providerComment = findViewById(R.id.et_review_comments);
        reviewSubmit = findViewById(R.id.btn_reviewSubmit);

        dialog = UiUtils.showProgress(RatingActivity.this);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        back.setOnClickListener(this);
        reviewSubmit.setOnClickListener(this);

        ratingBar_review.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                get_rating_val = ratingBar_review.getRating();
                rating_val = Double.parseDouble(new DecimalFormat("##.#").format(get_rating_val));


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
                if (isValid()) {
                    rateOrderSubmit();
                }

                break;

            default:
                break;
        }
    }

    public void rateOrderSubmit() {

        dialog.show();

        OrderRatingAPIModel orderRatingAPIModel = new OrderRatingAPIModel();
        orderRatingAPIModel.setRating(rating_val);
        orderRatingAPIModel.setComment(providerComment.getText().toString());


        orderListViewModel.orderRatingData(orderRatingAPIModel).observe(this, acceptPaymentResponseModel -> {

            dialog.dismiss();

            if (acceptPaymentResponseModel.getStatus() == 200) {
                UiUtils.showAlert(activity, "Rating", getString(R.string.aleart_rating_submit));
            }
        });
    }

    // method will validate the fields
    private boolean isValid() {
        if (TextUtils.isEmpty(providerComment.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_rating_comment));
            return false;
        }else  if (rating_val==0) {
            UiUtils.showToast(this, getString(R.string.aleart_select_rating));
            return false;
        }
        else {
            return true;
        }
    }
}