package com.morirain.flowmemo.presenter;


import android.view.View;

import com.morirain.flowmemo.base.BasePresenter;
import com.morirain.flowmemo.databinding.ItemNotesBinding;
import com.morirain.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class NotesPresenter extends BasePresenter<ItemNotesBinding> {

    @Override
    public void setPresenter(ItemNotesBinding bind) {
        bind.setPresenter(this);
    }

    public void onItemClick(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

    public void onItemMoreClick(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }
}
