package com.morirain.flowmemo.base;


import android.app.Application;
import android.content.Context;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

import com.blankj.utilcode.util.Utils;
import com.morirain.flowmemo.BuildConfig;
import com.morirain.flowmemo.model.MyObjectBox;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class BaseApplication extends Application {

    private static Context sAppContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    public static final boolean EXTERNAL_DIR = false;

    private BoxStore mBoxStore;

    public static RefWatcher sRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();

        // init LeakCanary
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            sRefWatcher = LeakCanary.install(this);
        }

        // init AndroidUtilCode
        Utils.init(sAppContext);

        // init ObjectBox
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(mBoxStore).start(this);
        }

        // init Skin
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                //.setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                //.setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }
}