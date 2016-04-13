package com.binc.expensemanager.ui;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.binc.expensemanager.R;
import com.binc.expensemanager.ui.AddDialog;
import com.binc.expensemanager.ui.DisplayFragment;
import com.binc.expensemanager.util.Constants;

public class DisplayActivity extends BaseActivity implements DisplayFragment.ListenerCallback{

    LinearLayout ll_remove;
    private static String VIEW_PREFERENCE = "view_preference";
    private static String VIEW_SETTING = "view_category";
    SharedPreferences viewPreference;
    static final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        viewPreference = getSharedPreferences(VIEW_PREFERENCE, MODE_PRIVATE);

        if(savedInstanceState == null){
            setupView();
        }

        FloatingActionButton btn_fab = (FloatingActionButton) findViewById(R.id.btn_fab);
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddDialog mAddDialog = new AddDialog();
                mAddDialog.show(fm, "Add");
            }
        });

        ll_remove = (LinearLayout) DisplayActivity.this.findViewById(R.id.ll_remove);
        ll_remove.setOnDragListener(mDragListener);

        //content observer
        getApplicationContext().getContentResolver().registerContentObserver(Uri.parse(Constants.ProviderConstants.URL), true, new ContentObserver(new Handler()) {


            @Override
            public boolean deliverSelfNotifications() {
                return false;
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                //setupView();
            }
        });
    }

    private void setupView(){
        if(viewPreference.getBoolean(VIEW_SETTING, false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.host_fragment, new DisplayFragmentHost())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.host_fragment, new DisplayFragment())
                    .commit();
        }
    }

    View.OnDragListener mDragListener = new View.OnDragListener(){

        @Override
        public boolean onDrag(View v, DragEvent event) {
            if(v.getId() == R.id.ll_remove) {
                Log.d(TAG, "ll_remove");
                if(DragEvent.ACTION_DRAG_ENDED == event.getAction())
                    setListItemDragBegan(false);
            }
            Log.d(TAG, "onDrag.");
            return false;
        }
    };

    @Override
    public void setListItemDragBegan(boolean hasBegun) {
        if(hasBegun) {
            ll_remove.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "gone");
            ll_remove.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tabs) {
            SharedPreferences.Editor editor = viewPreference.edit();
            editor.putBoolean(VIEW_SETTING, true);
            editor.commit();
            setupView();
            return true;
        }

        if (id == R.id.action_list){
            SharedPreferences.Editor editor = viewPreference.edit();
            editor.putBoolean(VIEW_SETTING, false);
            editor.commit();
            setupView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
