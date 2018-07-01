package com.morirain.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.adapter.FolderAdapter;
import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingletonFactory;
import com.morirain.flowmemo.viewmodel.handler.ItemDrawerFolderHandler;


import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */


public class FolderViewModel extends BaseViewModel {

    private FolderAdapter mAdapter;

    private List<Folder> mList;

    private NoteLibraryRepository mNoteLibraryRepository = SingletonFactory.getInstace(NoteLibraryRepository.class);

    public FolderViewModel() {
    }

    public void setAdapter(FolderAdapter adapter){
        mAdapter = adapter;
        mAdapter.setHandler(new ItemDrawerFolderHandler(mNoteLibraryRepository));
        mList = adapter.getDataList().getValue();
        getListData();
    }

    private void getListData() {
        mList.addAll(mNoteLibraryRepository.getFolderList());
    }

}
