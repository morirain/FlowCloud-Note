package com.flowmemo.adapter;


import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BasePresenter;
import com.flowmemo.databinding.ItemDrawerFolderBinding;
import com.flowmemo.model.Folder;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class FolderAdapter extends BaseAdapter<Folder, ItemDrawerFolderBinding> {

    public FolderAdapter(int variableId, int layoutId, BasePresenter<ItemDrawerFolderBinding> presenter) {
        super(variableId, layoutId, presenter);
    }

    @Override
    protected void setPosition(ViewHolder holder) {
        getBinding().buttonDrawerFolderMore.setTag(holder);
    }
}
