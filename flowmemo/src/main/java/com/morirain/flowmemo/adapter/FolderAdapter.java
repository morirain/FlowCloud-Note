package com.morirain.flowmemo.adapter;


import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.ItemDrawerFolderBinding;
import com.morirain.flowmemo.model.Folder;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class FolderAdapter extends BaseAdapter<Folder, ItemDrawerFolderBinding> {

    public FolderAdapter(int variableId, int layoutId, BaseCommandHandler<ItemDrawerFolderBinding> presenter) {
        super(variableId, layoutId, presenter);
    }

    @Override
    protected void setPosition(ViewHolder holder) {
        getBinding().buttonDrawerFolderMore.setTag(holder);
    }
}
