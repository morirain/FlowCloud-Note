package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.ItemNotesBinding;
import com.morirain.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class ItemNotesHandler extends BaseCommandHandler<ItemNotesBinding> {

    public void onItemClick(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

    public void onItemMoreClick(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }
}
