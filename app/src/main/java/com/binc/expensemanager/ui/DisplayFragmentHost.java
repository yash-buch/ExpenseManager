package com.binc.expensemanager.ui;

import android.database.ContentObserver;
import android.net.Uri;
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
import com.binc.expensemanager.helper.DataAccessor;
import com.binc.expensemanager.util.Constants;

import java.util.List;

public class DisplayFragmentHost extends Fragment {

    private List<String> categoryList;
    View view;
    private TabLayout tabLayout;
    FragmentManager fm;
    private ContentObserver contentObserver;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //viewPager = new CategoryPagerAdapter();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_display_fragment_host, container, false);
        fm = getChildFragmentManager();
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setTabs();

        tabLayout.setOnTabSelectedListener(tabSelectedListener);
        /*getActivity().getContentResolver().registerContentObserver(
                Uri.parse(Constants.ProviderConstants.URL), true, contentObserver);*/

        return view;
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            showFragment(tab.getText().toString());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void showFragment(String cat){
        String category = cat;
        Fragment fragment = new DisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CATEGORY, category);
        fragment.setArguments(bundle);

        fm.beginTransaction()
                .replace(R.id.fragment_cat, fragment)
                .commit();
    }

    private void setTabs() {
        categoryList = DataAccessor.getDataAccessorInstance(getActivity()).getCategoryList();
        if(categoryList == null){
            showFragment(null);
        } else {
            for (String category : categoryList) {
                tabLayout.addTab(tabLayout.newTab().setText(category));
            }
            showFragment(categoryList.get(0));
        }
/*        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("A"));*/
    }
}
