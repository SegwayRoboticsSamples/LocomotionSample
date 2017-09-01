package com.segway.robot.locomotionsample;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by sunguangshan on 2016/7/26.
 */

public class Util {
    private static final String TAG = "Util";

    public static boolean isEditTextEmpty(EditText editText) {
        if (editText == null) {
            return false;
        }
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            return true;
        } else {
            return false;
        }
    }

    public static String floatToString(float f) {
        return String.valueOf(f);
    }

    public static float getEditTextFloatValue(EditText editText) {
        String text = editText.getText().toString().trim();
        return Float.parseFloat(text);
    }

}
