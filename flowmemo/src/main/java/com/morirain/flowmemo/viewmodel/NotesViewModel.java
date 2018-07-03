package com.morirain.flowmemo.viewmodel;


import com.morirain.flowmemo.adapter.NotesAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingleLiveEvent;
import com.morirain.flowmemo.utils.SingletonFactory;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends BaseViewModel {

//    private NotesAdapter mAdapter;
//
//    private List<Notes> mList;

    private NoteLibraryRepository mNoteLibraryRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    public SingleLiveEvent<Notes> notesClickEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Void> newNotesClickEvent = new SingleLiveEvent<>();

    public NotesViewModel() {
    }

    public void setAdapter(NotesAdapter adapter){
        /*mAdapter = adapter;
        mAdapter.setHandler(new ItemNotesHandler());
        mList = adapter.getDataList().getValue();
        getListData();*/
    }

    public void onNewNoteClick() {
        newNotesClickEvent.call();
    }

    public void onItemClick(Notes notes) {
        notesClickEvent.setValue(notes);
    }
}
