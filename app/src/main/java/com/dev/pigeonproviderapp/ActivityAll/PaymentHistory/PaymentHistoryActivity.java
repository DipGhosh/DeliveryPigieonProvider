package com.dev.pigeonproviderapp.ActivityAll.PaymentHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.HistoryFragment.BonusFragment;
import com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.HistoryFragment.BonusHistoryFrag;
import com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.HistoryFragment.EarnHistoryFrag;
import com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.HistoryFragment.PaymentHistoryFrag;
import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.ActiveOrdersFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.CurrentOrderFrag;
import com.dev.pigeonproviderapp.Fragment.OrdersFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PaymentHistoryActivity extends BaseActivity implements View.OnClickListener {

    private Activity activity = PaymentHistoryActivity.this;

    private TabLayout tabLayout;
    private TabItem tabPaymentHistory;
    private TabItem tabEarnHistory;
    private TabItem tabIncentiveHistory;
    private TabItem tabBonusHistory;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;

    private ProfileViewModel profileViewModel;

    private PaymentHistoryFrag payHistoryFrag = new PaymentHistoryFrag();
    private EarnHistoryFrag earnHistoryFrag = new EarnHistoryFrag();
    private BonusHistoryFrag incentiveHistoryFrag = new BonusHistoryFrag();
    private BonusFragment bonusFragment = new BonusFragment();


    private ImageView back;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        dialog = UiUtils.showProgress(activity);

        tabLayout = findViewById(R.id.tablayout);
        tabPaymentHistory = findViewById(R.id.tab_payment_history);
        tabEarnHistory = findViewById(R.id.tab_earning_history);
        tabIncentiveHistory = findViewById(R.id.tab_incentive_history);
        //tabBonusHistory=findViewById(R.id.tab_bonus_historynew);
        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.img_back);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // ViewModel Object
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        // restrict refresh fragments
        viewPager.setOffscreenPageLimit(2);


        back.setOnClickListener(this);


        if (NetworkUtils.isNetworkAvailable(activity)) {

            paymentHistoryCall();

        } else {
            UiUtils.showToast(this, getString(R.string.network_error));
        }
    }

    public void paymentHistoryCall() {

        dialog.show();

        profileViewModel.getPaymentHistory().observe(this, new Observer<PaymentHistoryDataModel>() {
            @Override
            public void onChanged(PaymentHistoryDataModel paymentHistoryDataModel) {

                dialog.dismiss();

                if (paymentHistoryDataModel != null) {

                    if (paymentHistoryDataModel.getStatus() == 200) {
                        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(pageAdapter);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        // restrict refresh fragments
                        viewPager.setOffscreenPageLimit(3);

                        // set data
                        if (paymentHistoryDataModel.getData().getPaymentHistory() != null) {
                            payHistoryFrag.setData(paymentHistoryDataModel.getData().getPaymentHistory());
                            incentiveHistoryFrag.setData(paymentHistoryDataModel.getData().getBonusHistory());
                            bonusFragment.setData(paymentHistoryDataModel.getData().getNewbonusHistory());
                            earnHistoryFrag.setData(paymentHistoryDataModel.getData().getEarningHistory());


                        }
                    }

                }


            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                finish();
                break;

            default:
                break;
        }
    }

    public class PageAdapter extends FragmentPagerAdapter {

        private int numOfTabs;

        PageAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return payHistoryFrag;
                case 1:
                    return incentiveHistoryFrag;
                case 2:
                    return bonusFragment;
                case 3:
                    return earnHistoryFrag;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }

}