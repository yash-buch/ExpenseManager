package com.binc.expensemanager.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.binc.expensemanager.R;


public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getActionBar().hide();
        animate();
    }

    private void animate(){
        TextView tv_username = (TextView) findViewById(R.id.tv_name);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        tv_username.startAnimation(animation);
        startDisplayActivity();
    }

    private void startDisplayActivity(){
        final Intent displayIntent = new Intent(this, DisplayActivity.class);
        Handler mHandler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(displayIntent);
            }
        };
        mHandler.postDelayed(r, 8000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
