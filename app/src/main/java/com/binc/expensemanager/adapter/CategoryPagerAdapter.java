package com.binc.expensemanager.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.binc.expensemanager.helper.DataAccessor;
import com.binc.expensemanager.ui.DisplayFragment;
import com.binc.expensemanager.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashbuch on 22/03/16.
 */
public class CategoryPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragment_list = new ArrayList<>();
    List<String> categoryList;
    Context mContext;
    DataAccessor dataAccessor;

    public CategoryPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        mContext = ctx;
        dataAccessor = DataAccessor.getDataAccessorInstance(mContext);
        getCategoryList();
        // clear fragment_list
        fragment_list.clear();
        if(categoryList != null) {
            for (String category : categoryList) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CATEGORY, category);
                Fragment fragment = new DisplayFragment();
                fragment.setArguments(bundle);
                fragment_list.add(fragment);
            }
        } else {
            Fragment fragment = new DisplayFragment();
            fragment_list.add(fragment);
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(categoryList == null)
            return "No Categories";
        return categoryList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list.size();//remove this hardcoding
    }

    private void getCategoryList(){
        categoryList = dataAccessor.getCategoryList();
    }
}
