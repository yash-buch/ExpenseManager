package com.binc.expensemanager.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;

import com.binc.expensemanager.R;
import com.binc.expensemanager.adapter.ItemListAdapter;
import com.binc.expensemanager.util.Constants;

public class DisplayFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    static final String TAG = "DisplayFragment";
    ItemListAdapter mAdapter;
    ListenerCallback mListener;
    String category = null;

    @Override
    public void setArguments(Bundle bundle){
        String cat = bundle.getString(Constants.CATEGORY);
        category = cat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No Items added.\nAdd Items using the '+' button.");
        mAdapter = new ItemListAdapter(getActivity(), R.layout.item_card, null,
                new String[]{Constants.ProviderConstants.NAME_EN}, new int[]{}, 0);

        getListView().setDividerHeight(20);
        getListView().setPadding(15, 5, 15, 5);
        setListAdapter(mAdapter);
        setListShown(false);

        getListView().setOnItemLongClickListener(mClickListener);


        getLoaderManager().initLoader(0, null, this);
    }

    AdapterView.OnItemLongClickListener mClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, null, 0);
            mListener.setListItemDragBegan(true);

            return true;
        }
    };

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        Activity activity = null;
        if (ctx instanceof Activity)
            activity = (Activity) ctx;

        mListener = (ListenerCallback) activity;

    }

    //Loader callbacks!!
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(Constants.ProviderConstants.URL);
        String[] projection = new String[]{Constants.ProviderConstants.ID, Constants.ProviderConstants.NAME_EN};
        String selection = null;
        String[] selectionArgs = null;
        if(category != null) {
            selection = Constants.ProviderConstants.CATEGORY + " = ?";
            selectionArgs = new String[1];
            selectionArgs[0] = category;
        }

        return new CursorLoader(getActivity(), baseUri, projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    //Activity Callback(activity must implement this callback interface)
    public interface ListenerCallback {
        void setListItemDragBegan(boolean hasBegan);
    }
}
