package com.dev.pigeonproviderapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dev.pigeonproviderapp.Fragment.OrdersFrag;
import com.dev.pigeonproviderapp.Fragment.ProfileFrag;
import com.dev.pigeonproviderapp.Fragment.SupportFrag;
import com.dev.pigeonproviderapp.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    //TextView trainer_main_header;
    private int[] tabIcons = {
            R.drawable.tab_order,
            R.drawable.tab_support,
            R.drawable.tab_profile,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager_trainer);
        //trainer_main_header=(TextView)findViewById(R.id.tv_dashboard);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupTabIcons();

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab)
                    {
                        super.onTabSelected(tab);
                        viewPager.setCurrentItem(tab.getPosition());
                        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.otptextColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

                       /* if (viewPager.getCurrentItem()==0)
                        {
                            trainer_main_header.setText("Orders");
                        }
                        else if (viewPager.getCurrentItem()==1)
                        {
                            trainer_main_header.setText("Support");
                        }
                        else if (viewPager.getCurrentItem()==2)
                        {
                            trainer_main_header.setText("Profile");
                        }
                        else if (viewPager.getCurrentItem()==3)
                        {
                            trainer_main_header.setText("Setting");

                        }*/

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.fadeBlackColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);

                    }
                }

        );


    }

    private void setupTabIcons()
    {

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);




        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.inputBorderColor), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);




    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OrdersFrag(), "Orders");
        adapter.addFrag(new SupportFrag(), "Support");
        adapter.addFrag(new ProfileFrag(), "Profile");



        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);

        }
    }

}