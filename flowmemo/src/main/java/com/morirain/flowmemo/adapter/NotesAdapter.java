package com.morirain.flowmemo.adapter;


import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingletonFactory;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/7/1
 */


public class NotesAdapter extends BaseAdapter<Notes> {

    private NoteLibraryRepository mRepository = SingletonFactory.getInstace(NoteLibraryRepository.class);

    public NotesAdapter(LifecycleOwner lifecycleOwner, int layoutId) {
        super(lifecycleOwner, layoutId);
        mRepository.getCurrentFolder().observe(lifecycleOwner, folder -> {
            if (folder != null) {
                getListValue().clear();
                getListValue().addAll(folder.getNotesList());
                notifyDataSetChanged();
            }
        });
    }


}
