package com.morirain.flowmemo.viewmodel;


import java.util.List;

import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.databinding.ItemNotesBinding;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.viewmodel.handler.ItemNotesHandler;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends BaseViewModel {

    private BaseAdapter mAdapter;// = new NotesAdapter(BR.notes, R.layout.item_notes, new ItemNotesHandler());

    private List<Notes> mList;


    public NotesViewModel() {
    }

    public void setAdapter(BaseAdapter<Notes> adapter){
        mAdapter = adapter;
        mAdapter.setHandler(new ItemNotesHandler());
        mList = adapter.getDataList().getValue();
        getListData();
    }

    private void getListData() {
        for (int i = 0; i < 100; i++) {
            Notes notes = new Notes("Label", "Content", "Just Now");
            mList.add(notes);
        }
    }
}
