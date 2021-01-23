package com.dev.pigeonproviderapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.ActiveOrdersFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.CurrentOrderFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.PastOrderFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class OrdersFrag extends Fragment {

    private View mView;
    private TabLayout tabLayout;
    private TabItem tabActive;
    private TabItem tabCurrent;
    private TabItem tabPast;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;

    private OrderListViewModel orderListViewModel;

    private ActiveOrdersFrag activeOrdersFrag = new ActiveOrdersFrag();
    private CurrentOrderFrag currentOrderFrag = new CurrentOrderFrag();
    private PastOrderFrag pastOrderFrag = new PastOrderFrag();

    public OrdersFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_orders, container, false);
        tabLayout=mView.findViewById(R.id.tablayout);
        tabActive=mView.findViewById(R.id.tabActiveOrder);
        tabCurrent=mView.findViewById(R.id.tabCurrentOrders);
        tabPast=mView.findViewById(R.id.tabPastOrders);
        viewPager=mView.findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
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
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        getOrderList();

        return mView;
    }

    public void getOrderList() {
        orderListViewModel.getOrderListData().observe(this, new Observer<ListOrderResponseDataModel>() {
            @Override
            public void onChanged(ListOrderResponseDataModel listOrderDataModel) {

                pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pageAdapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                // set data
                if (listOrderDataModel.getData().getAvailable()!=null)
                {
                    activeOrdersFrag.setData(listOrderDataModel.getData().getAvailable());
                }


            }
        });
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
                    return new ActiveOrdersFrag();
                case 1:
                    return new CurrentOrderFrag();
                case 2:
                    return new PastOrderFrag();
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