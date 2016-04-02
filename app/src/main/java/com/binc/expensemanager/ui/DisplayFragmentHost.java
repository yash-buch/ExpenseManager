package com.binc.expensemanager.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binc.expensemanager.R;
import com.binc.expensemanager.util.Constants;

public class DisplayFragmentHost extends Fragment {

    ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //viewPager = new CategoryPagerAdapter();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_display_fragment_host, container, false);
        fm = getChildFragmentManager();
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setTabs();

        tabLayout.setOnTabSelectedListener(tabSelectedListener);

        return view;
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            String category = tab.getText().toString();
            Fragment fragment = new DisplayFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CATEGORY, category);
            fragment.setArguments(bundle);

            fm.beginTransaction()
                    .replace(R.id.fragment_cat, fragment)
                    .commit();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
    }
}
