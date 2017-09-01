package com.segway.robot.locomotionsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.segway.robot.locomotionsample.R;
import com.segway.robot.locomotionsample.Util;
import com.segway.robot.sdk.locomotion.sbv.AngularVelocity;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.locomotion.sbv.LinearVelocity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sgs on 2017/4/18.
 */

public class SimpleBaseFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "SimpleBaseFragment";
    EditText mLinearVelocity;
    EditText mAngularVelocity;

    TextView mAv;
    TextView mLv;
    TextView mTicksTime;
    TextView mUltrasonic;
    Timer mTimer;
    private static final int RUN_TIME = 2000;
    boolean isStop;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mLinearVelocity = (EditText)view.findViewById(R.id.linear_speed_value);
        mAngularVelocity = (EditText) view.findViewById(R.id.v_speed_value);

        mAv = (TextView) view.findViewById(R.id.av_speed);
        mLv = (TextView) view.findViewById(R.id.lv_speed);
        mTicksTime = (TextView) view.findViewById(R.id.ticks_time);
        mUltrasonic = (TextView) view.findViewById(R.id.ultrasonic);
        ((Button)view.findViewById(R.id.set_v_speed)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.set_linear_speed)).setOnClickListener(this);

        mLinearVelocity.setOnFocusChangeListener(mOnFocusChangeListener);
        mAngularVelocity.setOnFocusChangeListener(mOnFocusChangeListener);
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final AngularVelocity av = mBase.getAngularVelocity();
                final LinearVelocity lv = mBase.getLinearVelocity();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAv.setText("AngularVelocity:" + av.getSpeed());
                        mLv.setText("LinearVelocity:" + lv.getSpeed());
                        mTicksTime.setText("Timestamp:" + av.getTimestamp());
                        mUltrasonic.setText("Ultrasonic:" + mBase.getUltrasonicDistance());
                    }
                });
            }
        }, 500, 200);

    }

    @Override
    public void onClick(View view) {
        mBase.setControlMode(Base.CONTROL_MODE_RAW);
        switch (view.getId()) {
            case R.id.set_linear_speed:
                isStop = false;
                new Thread() {
                    @Override
                    public void run() {
                        float startX = mBase.getOdometryPose(-1).getX();
                        float startY = mBase.getOdometryPose(-1).getY();
//                        while ((getDistance(startX, startY, mBase.getOdometryPose(-1).getX(), mBase.getOdometryPose(-1).getY()) < 1) && !isStop) {
                            Log.d(TAG, "run: " + getDistance(startX, startY, mBase.getOdometryPose(-1).getX(), mBase.getOdometryPose(-1).getY()));
                            if (!Util.isEditTextEmpty(mLinearVelocity)) {
                                // set robot base linearVelocity, unit is rad/s, rand is -PI ~ PI.
                                mBase.setLinearVelocity(Util.getEditTextFloatValue(mLinearVelocity));
                            } else {
                                mBase.setLinearVelocity(1);
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                        }
                        mBase.setLinearVelocity(0);
                    }
                }.start();
                break;
            case R.id.set_v_speed:
                isStop = false;
                new Thread() {
                    @Override
                    public void run() {
                        float start = mBase.getOdometryPose(-1).getTheta();
//                        while ((Math.abs(mBase.getOdometryPose(-1).getTheta() - start) < Math.PI) && !isStop) {
                            if (!Util.isEditTextEmpty(mAngularVelocity)) {
                                // set robot base ANGULARVelocity, unit is rad/s, rand is -PI ~ PI.
                                mBase.setAngularVelocity(Util.getEditTextFloatValue(mAngularVelocity));
                            } else {
                                mBase.setAngularVelocity(1);
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                        }
                        // stop
                        mBase.setAngularVelocity(0);
                    }
                }.start();
                break;
            case R.id.stop:
                // stop robot
                isStop = true;
                mBase.stop();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private double getDistance(float startX, float startY, float endX, float endY) {
        Log.d(TAG, "getDistance: endX " + endX + " endY：" + endY);
        double xDis = Math.pow((Math.abs(endX - startX)), 2);
        double yDis = Math.pow((Math.abs(endY - startY)), 2);
        double dis = Math.sqrt( xDis + yDis);
        return dis;
    }
}
