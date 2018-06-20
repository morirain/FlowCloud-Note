package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.DrawerContentBinding;
import com.morirain.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/17
 */


public class DrawerContentHandler extends BaseCommandHandler<DrawerContentBinding> {

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
