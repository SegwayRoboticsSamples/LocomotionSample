package com.segway.robot.locomotionsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.segway.robot.locomotionsample.base.BaseActivity;
import com.segway.robot.locomotionsample.head.HeadActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base:
                startActivity(new Intent(this, BaseActivity.class));
                break;
            case R.id.head:
                startActivity(new Intent(this, HeadActivity.class));
                break;
            default:
                break;
        }
    }
}
