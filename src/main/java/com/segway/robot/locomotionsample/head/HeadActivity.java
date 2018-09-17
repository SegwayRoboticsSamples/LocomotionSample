package com.segway.robot.locomotionsample.head;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.segway.robot.locomotionsample.R;
import com.segway.robot.locomotionsample.Util;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.head.Head;

import java.util.Timer;
import java.util.TimerTask;

public class HeadActivity extends Activity {

    Head mHead;
    boolean isBind = false;
    EditText mWorldYaw;
    EditText mWorldPitch;
    EditText mBaseYaw;
    EditText mBasePitch;
    EditText mSpeedYaw;
    EditText mSpeedPitch;
    EditText mPitchIncremental;
    EditText mYawIncremental;
    EditText mHeadMode;

    TextView mWorldYawValue;
    TextView mWorldPitchValue;
    TextView mBaseYawValue;
    TextView mBasePitchValue;
    RadioGroup mMode;
    View mEditTextFocus;
    Timer mTimer = new Timer();
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // get robot head pitch value, the value is angle between head and base int the pitch direction.
                    mBasePitchValue.setText(Util.floatToString(mHead.getPitchRespectBase().getAngle()));
                    // get robot head yaw value, the value is angle between head and base int the yaw direction.
                    mBaseYawValue.setText(Util.floatToString(mHead.getYawRespectBase().getAngle()));
                    // get robot head yaw value, the value is angle between head and world int the yaw direction.
                    mWorldYawValue.setText(Util.floatToString(mHead.getWorldYaw().getAngle()));
                    // get robot head pitch value, the value is angle between head and world int the pitch direction.
                    mWorldPitchValue.setText(Util.floatToString(mHead.getWorldPitch().getAngle()));
                }
            });
        }
    };

    private ServiceBinder.BindStateListener mServiceBindListener = new ServiceBinder.BindStateListener() {
        @Override
        public void onBind() {
            isBind = true;
            // get robot head current movement pattern.
            switch (mHead.getMode()) {
                case Head.MODE_ORIENTATION_LOCK:
                    ((RadioButton) findViewById(R.id.lock)).setChecked(true);
                    break;
                case Head.MODE_SMOOTH_TACKING:
                    ((RadioButton) findViewById(R.id.smooth_track)).setChecked(true);
                    break;
                default:
                    break;
            }
            mTimer.schedule(mTimerTask, 50, 50);
        }

        @Override
        public void onUnbind(String reason) {
            isBind = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        init();
        // get Head instance.
        mHead = Head.getInstance();
        // bindService, if not, all Head api will not work.
        mHead.bindService(getApplicationContext(), mServiceBindListener);

        mMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.smooth_track:
                        // set robot head in MODE_SMOOTH_TACKING.
                        if (isBind) {
                            mHead.setMode(Head.MODE_SMOOTH_TACKING);
                        }
                        break;
                    case R.id.lock:
                        // set robot head in MODE_ORIENTATION_LOCK.
                        if (isBind) {
                            mHead.setMode(Head.MODE_ORIENTATION_LOCK);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // get robot head pitch value, the value is angle between head and base int the pitch direction.
                        mBasePitchValue.setText(Util.floatToString(mHead.getPitchRespectBase().getAngle()));
                        // get robot head yaw value, the value is angle between head and base int the yaw direction.
                        mBaseYawValue.setText(Util.floatToString(mHead.getYawRespectBase().getAngle()));
                        // get robot head yaw value, the value is angle between head and world int the yaw direction.
                        mWorldYawValue.setText(Util.floatToString(mHead.getWorldYaw().getAngle()));
                        // get robot head pitch value, the value is angle between head and world int the pitch direction.
                        mWorldPitchValue.setText(Util.floatToString(mHead.getWorldPitch().getAngle()));
                    }
                });
            }
        };
        // get Head instance.
        mHead = Head.getInstance();
        // bindService, if not, all Head api will not work.
        mHead.bindService(getApplicationContext(), mServiceBindListener);

        mMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.smooth_track:
                        // set robot head in MODE_SMOOTH_TACKING.
                        if (isBind) {
                            mHead.setMode(Head.MODE_SMOOTH_TACKING);
                        }
                        break;
                    case R.id.lock:
                        // set robot head in MODE_ORIENTATION_LOCK.
                        if (isBind) {
                            mHead.setMode(Head.MODE_ORIENTATION_LOCK);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBind) {
            mHead.unbindService();
        }
        mTimerTask = null;
        mTimer.cancel();
        mTimer = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mEditTextFocus != null) {
                    hideKeyboard(mEditTextFocus);
                    mEditTextFocus = null;
                } else {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (!isBind) {
            return;
        }
        switch (view.getId()) {
            case R.id.reset:
                // reset robot head orientation
                mHead.resetOrientation();
                break;
            case R.id.world_pitch:
                if (!Util.isEditTextEmpty(mWorldPitch)
                        && mHead.getMode() == Head.MODE_SMOOTH_TACKING) {
                    // set robot head pitch angle, the value is angle between head and world int the pitch direction
                    mHead.setWorldPitch(Util.getEditTextFloatValue(mWorldPitch));
                }
                break;
            case R.id.world_yaw:
                if (!Util.isEditTextEmpty(mWorldYaw)
                        && mHead.getMode() == Head.MODE_SMOOTH_TACKING) {
                    // set robot head yaw angle, the value is angle between head and world int the yaw direction
                    mHead.setWorldYaw(Util.getEditTextFloatValue(mWorldYaw));
                }
                break;
            case R.id.base_yaw:
                if (!Util.isEditTextEmpty(mBaseYaw)
                        && mHead.getMode() == Head.MODE_SMOOTH_TACKING) {
                    // set robot head yaw angle, the value is angle between head and base int the base direction
                    mHead.setYawRespectBase(Util.getEditTextFloatValue(mBaseYaw));
                }
                break;
            case R.id.pitch_speed:
                if (!Util.isEditTextEmpty(mSpeedPitch)
                        && mHead.getMode() == Head.MODE_ORIENTATION_LOCK) {
                    // set robot head pitch angularVelocity.
                    mHead.setPitchAngularVelocity(Util.getEditTextFloatValue(mSpeedPitch));
                }
                break;
            case R.id.yaw_speed:
                if (!Util.isEditTextEmpty(mSpeedYaw)
                        && mHead.getMode() == Head.MODE_ORIENTATION_LOCK) {
                    // set robot head yaw angularVelocity.
                    mHead.setYawAngularVelocity(Util.getEditTextFloatValue(mSpeedYaw));
                }
                break;
            case R.id.yaw_incremental:
                if (!Util.isEditTextEmpty(mYawIncremental)
                        && mHead.getMode() == Head.MODE_SMOOTH_TACKING) {
                    // Set the angular increment in the yaw direction, the value is angle between head and base int the yaw direction
                    mHead.setIncrementalYaw(Util.getEditTextFloatValue(mYawIncremental));
                }
                break;
            case R.id.pitch_incremental:
                if (!Util.isEditTextEmpty(mPitchIncremental)
                        && mHead.getMode() == Head.MODE_SMOOTH_TACKING) {
                    // Set the angular increment in the pitch direction, the value is angle between head and base int the pitch direction
                    mHead.setIncrementalPitch(Util.getEditTextFloatValue(mPitchIncremental));
                }
                break;
            case R.id.head_mode:
                if (!Util.isEditTextEmpty(mHeadMode)) {
                    mHead.setHeadLightMode(Integer.parseInt(mHeadMode.getText().toString().trim()));
                }
                break;
            default:
                break;
        }
    }

    public void init() {
        mBasePitch = (EditText) findViewById(R.id.need_base_pitch_value);
        mBaseYaw = (EditText) findViewById(R.id.need_base_yaw_value);
        mWorldPitch = (EditText) findViewById(R.id.need_world_pitch_value);
        mWorldYaw = (EditText) findViewById(R.id.need_world_yaw_value);
        mSpeedPitch = (EditText) findViewById(R.id.need_pitch_speed_value);
        mSpeedYaw = (EditText) findViewById(R.id.need_yaw_speed_value);
        mPitchIncremental = (EditText) findViewById(R.id.need_pitch_incremental_value);
        mYawIncremental = (EditText) findViewById(R.id.need_yaw_incremental_value);
        mHeadMode = (EditText) findViewById(R.id.head_mode_value);

        mBasePitch.setOnFocusChangeListener(mOnFocusChangeListener);
        mBaseYaw.setOnFocusChangeListener(mOnFocusChangeListener);
        mWorldPitch.setOnFocusChangeListener(mOnFocusChangeListener);
        mWorldYaw.setOnFocusChangeListener(mOnFocusChangeListener);
        mSpeedPitch.setOnFocusChangeListener(mOnFocusChangeListener);
        mSpeedYaw.setOnFocusChangeListener(mOnFocusChangeListener);
        mPitchIncremental.setOnFocusChangeListener(mOnFocusChangeListener);
        mYawIncremental.setOnFocusChangeListener(mOnFocusChangeListener);

        mBasePitchValue = (TextView) findViewById(R.id.cur_base_pitch_value);
        mBaseYawValue = (TextView) findViewById(R.id.cur_base_yaw_value);
        mWorldPitchValue = (TextView) findViewById(R.id.cur_world_pitch_value);
        mWorldYawValue = (TextView) findViewById(R.id.cur_world_yaw_value);
        mMode = (RadioGroup) findViewById(R.id.mode_select);
    }

    @Override
    protected void onDestroy() {
        mHead.unbindService();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        super.onDestroy();
    }

    View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            } else {
                mEditTextFocus = v;
            }
        }
    };

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
