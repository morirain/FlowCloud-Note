package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.base.BaseCommandHandler;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class ItemDrawerFolderHandler extends BaseCommandHandler {


    public void onItemClick(View view) {
        LogUtils.d(String.valueOf(getPosition(view)));
    }

    public void onClickMore(View view) {
        LogUtils.d(String.valueOf(getPosition(view)));
    }

}
