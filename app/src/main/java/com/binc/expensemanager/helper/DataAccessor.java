package com.binc.expensemanager.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private List<String> categoryList;
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

        Uri retUri = mContext.getContentResolver().insert(_uri, cv);
        //maintain category list
        updateCategoryList(data.getCategory(), _uri);

        return retUri;
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

    private void updateCategoryList(){
        categoryList = categoryList == null ? new ArrayList<String>() : categoryList;
        Cursor cursor = mContext.getContentResolver().query(Uri.parse(Constants.ProviderConstants.URL),
                new String[]{Constants.ProviderConstants.CATEGORY}, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            categoryList.add(cursor.getString(0));
            cursor.moveToNext();
        }
    }

    private void updateCategoryList(String cat, Uri uri){
        if(categoryList == null){
            updateCategoryList();
        }else {
            String cat_query = "SELECT COUNT(*) FROM " + Constants.TableConstants.TABLE_NAME + " WHERE " +
                    Constants.ProviderConstants.CATEGORY + " = ?";
            String[] sel_arg = new String[]{cat};

            Cursor cursor = mContext.getContentResolver().query(uri,
                    new String[]{Constants.ProviderConstants.CATEGORY}, cat_query, sel_arg, null);

            if(cursor.getCount() == 0){
                categoryList.add(cat);
            }

        }

    }

    public List<String> getCategoryList(){
        if(categoryList == null || categoryList.size() == 0) {
            updateCategoryList();
        }
        return categoryList;
    }

// ADD A FAIL-SAFE LATER
}
