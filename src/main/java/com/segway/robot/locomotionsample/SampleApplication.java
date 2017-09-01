package com.segway.robot.locomotionsample;

import android.app.Application;
import android.content.Context;

/**
 * Created by sgs on 2017/4/18.
 */

public class SampleApplication extends Application {

    static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
