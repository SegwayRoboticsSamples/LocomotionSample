package com.segway.robot.locomotionsample.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by sgs on 2017/4/19.
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;
    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
