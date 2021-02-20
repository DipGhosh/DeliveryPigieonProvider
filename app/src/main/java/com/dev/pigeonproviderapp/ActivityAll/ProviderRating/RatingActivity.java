package com.dev.pigeonproviderapp.ActivityAll.ProviderRating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ItemDetailsActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OrderRatingAPIModel;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.squareup.picasso.Picasso;

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
    private ImageView providerImage;
    private TextView providerName;
    private ProgressBar profileFragProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        back = findViewById(R.id.ll_back);
        ratingBar_review = findViewById(R.id.ratingBar);
        providerComment = findViewById(R.id.et_review_comments);
        reviewSubmit = findViewById(R.id.btn_reviewSubmit);
        providerImage=findViewById(R.id.ic_provider_profile_img);
        providerName=findViewById(R.id.tv_provider_name);
        profileFragProgress=findViewById(R.id.image_loader_progress);

        providerName.setText(Singleton.getInstance().getUSERNAME());

        if (Singleton.getInstance().getUSERIMAGE() != null)
        {
            Glide.with(activity)
                    .load(Singleton.getInstance().getUSERIMAGE())
                    .error(R.drawable.dummy_image)
                    .into(providerImage);
            profileFragProgress.setVisibility(View.GONE);
        }else {
            Picasso.with(activity).load(R.drawable.dummy_image).into(providerImage);
            profileFragProgress.setVisibility(View.GONE);
        }

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

                Singleton.getInstance().setORDERRATING( get_rating_val);


            }
        });


        if (Singleton.getInstance().getORDERRATING()>0)
        {
            ratingBar_review.setRating(Singleton.getInstance().getORDERRATING());
        }
        if (Singleton.getInstance().getRATECOMMENT()!=null)
        {
            providerComment.setText(Singleton.getInstance().getRATECOMMENT());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                if (Singleton.getInstance().isALLDROPPOINTCOMPLETE() == true)
                {
                    Singleton.getInstance().setALLDROPPOINTCOMPLETE(true);
                }
                finish();

                break;
            case R.id.btn_reviewSubmit:
                if (isValid()) {
                    rateOrderSubmit();
                    Singleton.getInstance().setRATECOMMENT(providerComment.getText().toString());
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
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                builder.setTitle(getResources().getString(R.string.app_name));
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage(R.string.aleart_rating_submit);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.label_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Singleton.getInstance().isALLDROPPOINTCOMPLETE() == true)
                                {
                                    Singleton.getInstance().setALLDROPPOINTCOMPLETE(true);
                                }
                                finish();
                            }
                        });
                final android.app.AlertDialog alert = builder.create();
                alert.show();

            }


        });
    }

    // method will validate the fields
    private boolean isValid() {
         if (rating_val==0) {
            UiUtils.showToast(this, getString(R.string.aleart_select_rating));
            return false;
        }
        else {
            return true;
        }
    }
}