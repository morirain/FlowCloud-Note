package me.morirain.dev.flowmemo.base;


import android.app.Application;
import android.content.Context;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import me.morirain.dev.flowmemo.BuildConfig;
import me.morirain.dev.flowmemo.model.MyObjectBox;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class BaseApplication extends Application {

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        // init ObjectBox
        boxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }
    }
}