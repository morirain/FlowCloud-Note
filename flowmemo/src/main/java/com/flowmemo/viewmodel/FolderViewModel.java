package com.flowmemo.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BaseViewModel;
import com.flowmemo.model.Folder;
import com.flowmemo.model.Notes;

import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */


public class FolderViewModel extends BaseViewModel {

    private BaseAdapter<Folder> mAdapter;

    private MutableLiveData<List<Folder>> mFolderList;


    public LiveData<List<Folder>> getFolderList() {
        if (mFolderList == null) {
            mFolderList = mAdapter.getList();
            getFolderData();
        }
        return mFolderList;
    }

    @Override
    protected void init() {

    }

    public void setAdapter(BaseAdapter<Folder> adapter) {
        mAdapter = adapter;
        mFolderList = adapter.getList();
        getFolderData();
    }

    private void getFolderData() {
        Folder notes = new Folder("所有笔记", null);
        mFolderList.getValue().add(notes);
    }
}
