package me.morirain.dev.flowmemo.utils;


import android.util.Log;

import me.morirain.dev.flowmemo.BuildConfig;

/**
 * Created by morirain on 2018/5/26.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class LogUtil {

    private static String TAG = " " + "LogUtil";

    public static void setTAG(String TAG) {
        LogUtil.TAG = " " + TAG;
    }

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Debug Message"
                    + "\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                    + "\n┃ Debug"
                    + "\n┠───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────"
                    + "\n┃"
                    + "\n┃ " + message
                    + "\n┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        }
    }

    public static void e(String message) {
        //if (BuildConfig.DEBUG) {
        Log.e(TAG, "Error Message"
                + "\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                + "\n┃ Error"
                + "\n┠───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────"
                + "\n┃"
                + "\n┃ " + message
                + "\n┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        //}
    }

    private LogUtil() {
    }

}
