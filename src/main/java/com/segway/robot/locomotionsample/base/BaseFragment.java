package com.segway.robot.locomotionsample.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.segway.robot.locomotionsample.SampleApplication;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.sbv.Base;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sgs on 2017/4/18.
 */

public class BaseFragment extends Fragment {
    static final float RADIUS = (float) 0.23;
    private static final String TAG = "BaseFragment";

    Base mBase;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        mBase = Base.getInstance();
        mBase.bindService(SampleApplication.getContext(), new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {

            }

            @Override
            public void onUnbind(String reason) {
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)SampleApplication.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            } else {
            }
        }
    };
}
