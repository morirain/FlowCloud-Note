package com.morirain.flowmemo.viewmodel.handler;


import android.support.v4.content.ContextCompat;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;

import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class ItemDrawerFolderHandler extends BaseCommandHandler {

    private final NoteLibraryRepository mRepository;

    private final List<Folder> mNotesList;

    public ItemDrawerFolderHandler(NoteLibraryRepository repository) {
        this.mRepository = repository;
        this.mNotesList = repository.getFolderList();
    }

    public void onItemClick(View view) {
        mRepository.setCurrentFolder(mNotesList.get(getPosition(view)));
    }

    public void onClickMore(View view) {
        LogUtils.d(String.valueOf(getPosition(view)));
    }

}
