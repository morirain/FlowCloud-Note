package com.morirain.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;

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

    private NoteLibraryRepository mRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    private MutableLiveData<Boolean> isRealFolder = new MutableLiveData<>();

    public SingleLiveEvent<Notes> notesClickEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Void> newNotesClickEvent = new SingleLiveEvent<>();

    public NotesViewModel() {
    }

    public MutableLiveData<Boolean> getIsRealFolder() {
        if (mRepository.isRealFolder(mRepository.getCurrentFolder().getValue())) {
            isRealFolder.setValue(true);
        } else {
            isRealFolder.setValue(false);
        }
        return isRealFolder;
    }

    public void onNewNoteClick() {
        newNotesClickEvent.call();
    }

    public void onItemClick(Notes notes) {
        notesClickEvent.setValue(notes);
    }
}
