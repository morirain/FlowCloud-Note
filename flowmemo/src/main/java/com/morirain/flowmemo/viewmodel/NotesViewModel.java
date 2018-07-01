package com.morirain.flowmemo.viewmodel;


import java.util.List;

import com.morirain.flowmemo.adapter.NotesAdapter;
import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.databinding.ItemNotesBinding;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingletonFactory;
import com.morirain.flowmemo.viewmodel.handler.ItemNotesHandler;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends BaseViewModel {

    private NotesAdapter mAdapter;

    private List<Notes> mList;

    private NoteLibraryRepository mNoteLibraryRepository = SingletonFactory.getInstace(NoteLibraryRepository.class);

    public NotesViewModel() {
    }

    public void setAdapter(NotesAdapter adapter){
        mAdapter = adapter;
        mAdapter.setHandler(new ItemNotesHandler());
        mList = adapter.getDataList().getValue();
        getListData();
    }

    private void getListData() {
        //mList.addAll(mNoteLibraryRepository.getCurrentFolder().getValue().getNotesList());
    }
}
