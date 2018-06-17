package com.flowmemo.presenter;


import android.view.View;

import com.flowmemo.base.BasePresenter;
import com.flowmemo.databinding.DrawerContentBinding;
import com.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/17
 */


public class DrawerPresenter extends BasePresenter<DrawerContentBinding> {
    @Override
    public void setPresenter(DrawerContentBinding bind) {
        bind.setPresenter(this);
    }


    public void onEditUserProfileClick(View view) {
        LogUtil.d("dasdas");
    }

    public void onEditRemoteClick(View view) {
        LogUtil.d("dasdas");
    }

    public void onNewFolderClick(View view) {
        LogUtil.d("dasdas");
    }

    public void onHistoryClick(View view) {
        LogUtil.d("dasdas");
    }

    public void onFormatClick(View view) {
        LogUtil.d("dasdas");
    }
}
