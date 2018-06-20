package com.morirain.flowmemo.adapter;


import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.ItemNotesBinding;
import com.morirain.flowmemo.model.Notes;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class NotesAdapter extends BaseAdapter<Notes, ItemNotesBinding> {

    public NotesAdapter(int variableId, int layoutId, BaseCommandHandler<ItemNotesBinding> presenter) {
        super(variableId, layoutId, presenter);
    }

    @Override
    protected void setPosition(ViewHolder holder) {
        getBinding().buttonNotesMore.setTag(holder);
    }
}
