package com.segway.robot.locomotionsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.segway.robot.algo.Pose2D;
import com.segway.robot.algo.minicontroller.CheckPoint;
import com.segway.robot.algo.minicontroller.CheckPointStateListener;
import com.segway.robot.locomotionsample.R;
import com.segway.robot.sdk.locomotion.sbv.Base;

/**
 * Created by sgs on 2017/4/19.
 */

public class NavigationFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NavigationFragment";
    Button mNavigationButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment, container, false);
        mNavigationButton = (Button) view.findViewById(R.id.navigation_test);
        mNavigationButton.setOnClickListener(this);
        ((Button)view.findViewById(R.id.stop)).setOnClickListener(this);
        mBase.setOnCheckPointArrivedListener(new CheckPointStateListener() {
            @Override
            public void onCheckPointArrived(CheckPoint checkPoint, final Pose2D realPose, boolean isLast) {
                Log.d(TAG, "onCheckPointArrived: x: " + checkPoint.getX() + " y: " + checkPoint.getY());
            }

            @Override
            public void onCheckPointMiss(CheckPoint checkPoint, Pose2D realPose, boolean isLast, int reason) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_test:
                mBase.setControlMode(Base.CONTROL_MODE_NAVIGATION);
                mBase.cleanOriginalPoint();
                Pose2D pose2D = mBase.getOdometryPose(-1);
                mBase.setOriginalPoint(pose2D);
                mBase.addCheckPoint(1f, 0, (float) (Math.PI /2));
                mBase.addCheckPoint(1f, 1f, (float) (Math.PI));
                mBase.addCheckPoint(0f, 1f, (float) (-Math.PI /2));
                mBase.addCheckPoint(0, 0, 0);
                break;
            case R.id.stop:
                mBase.clearCheckPointsAndStop();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBase.setControlMode(Base.CONTROL_MODE_RAW);
    }

    @Override
    public void onDestroy() {
        mBase.setControlMode(Base.CONTROL_MODE_RAW);
        super.onDestroy();
    }
}
