package com.segway.robot.locomotionsample.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.segway.robot.locomotionsample.R;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends FragmentActivity {

    ViewPager mViewPager;
    BaseFragmentAdapter mBaseFragmentAdapter;
    Button mSimple;
    Button mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mSimple = (Button) findViewById(R.id.base_simple);
        mNavigation = (Button) findViewById(R.id.navigation);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SimpleBaseFragment());
        fragmentList.add(new NavigationFragment());
        mBaseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mBaseFragmentAdapter);
        mViewPager.setCurrentItem(0);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_simple:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.navigation:
                mViewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
