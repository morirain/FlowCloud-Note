package me.morirain.dev.flowmemo.base;


import android.app.Application;
import android.content.Context;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import me.morirain.dev.flowmemo.BuildConfig;
import me.morirain.dev.flowmemo.model.MyObjectBox;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

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