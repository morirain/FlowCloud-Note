package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.ItemDrawerFolderBinding;
import com.morirain.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class ItemDrawerFolderHandler extends BaseCommandHandler {


    public void onItemClick(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

    public void onClickMore(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

}
