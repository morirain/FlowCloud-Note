package com.flowmemo.presenter;


import android.view.View;

import com.flowmemo.base.BasePresenter;
import com.flowmemo.databinding.ItemDrawerFolderBinding;
import com.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class DrawerContentPresenter extends BasePresenter<ItemDrawerFolderBinding> {

    @Override
    public void setPresenter(ItemDrawerFolderBinding bind) {
        bind.setPresenter(this);
    }

    public void onItemClick(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

    public void onClickMore(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

}
