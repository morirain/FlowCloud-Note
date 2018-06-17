package com.flowmemo.presenter;


import android.view.View;

import com.flowmemo.base.BasePresenter;
import com.flowmemo.databinding.ItemNotesBinding;
import com.flowmemo.utils.LogUtil;

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
