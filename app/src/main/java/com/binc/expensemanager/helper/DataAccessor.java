package com.binc.expensemanager.helper;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.binc.expensemanager.bean.DataBean;
import com.binc.expensemanager.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yashbuch on 19/03/16.
 */
public class DataAccessor {
    private Context mContext;
    private HashMap<String, Boolean> cat_map;
    private static DataAccessor mInstance = null;

    private DataAccessor(Context ctx){
        mContext = ctx;
    }

    public static DataAccessor getDataAccessorInstance(Context ctx){
        if(mInstance == null){
            mInstance = new DataAccessor(ctx);
        }
        return mInstance;
    }

    public Uri add(String uri, DataBean data){
        Uri _uri = Uri.parse(uri);
        ContentValues cv = new ContentValues();

        cv.put(Constants.ProviderConstants.NAME_EN, data.getName_en());
        cv.put(Constants.ProviderConstants.NAME_TE, data.getName_te());
        cv.put(Constants.ProviderConstants.CATEGORY, data.getCategory());
        cv.put(Constants.ProviderConstants.AVAILABLE_QTY, data.getAvb_qty());
        cv.put(Constants.ProviderConstants.REQUIRED_QTY, data.getReq_qty());

        //maintain category list
        updateCategoryList(data.getCategory());

        return mContext.getContentResolver().insert(_uri, cv);
    }

    public int delete(String uri, DataBean data){
        Uri _uri = Uri.parse(uri);
        String selection = data.getName_en();
        String[] selection_args = null;

        return mContext.getContentResolver().delete(_uri, selection, selection_args);
    }

    public int update(Uri uri, DataBean data){
        String selection = data.getName_en();
        String[] selection_args = null;
        ContentValues cv = new ContentValues();

        cv.put(Constants.ProviderConstants.NAME_EN, data.getName_en());
        cv.put(Constants.ProviderConstants.NAME_TE, data.getName_te());
        cv.put(Constants.ProviderConstants.CATEGORY, data.getCategory());
        cv.put(Constants.ProviderConstants.AVAILABLE_QTY, data.getAvb_qty());
        cv.put(Constants.ProviderConstants.REQUIRED_QTY, data.getReq_qty());

        return mContext.getContentResolver().update(uri, cv, selection, selection_args);
    }

    private void updateCategoryList(String cat){
        if(cat_map == null){
            cat_map = new HashMap<>();
            cat_map.put(cat,true);
        } else {
            if(!cat_map.containsKey(cat)){
                cat_map.put(cat,true);
            }
        }
    }

    public List<String> getCategoryList(){
        if(cat_map == null)
            return null;

        List<String> cat_list = new ArrayList<>();
        cat_list.addAll(cat_map.keySet());
        return cat_list;
    }

// ADD A FAIL-SAFE LATER
}
