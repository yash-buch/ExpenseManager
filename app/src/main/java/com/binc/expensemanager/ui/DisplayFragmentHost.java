package com.binc.expensemanager.ui;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binc.expensemanager.R;
import com.binc.expensemanager.adapter.CategoryPagerAdapter;

public class DisplayFragmentHost extends Fragment {

    ViewPager viewPager;
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //viewPager = new CategoryPagerAdapter();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_display_fragment_host, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        fm = getChildFragmentManager();
        viewPager.setAdapter(new CategoryPagerAdapter(fm, getActivity()));

        return view;
    }
}
