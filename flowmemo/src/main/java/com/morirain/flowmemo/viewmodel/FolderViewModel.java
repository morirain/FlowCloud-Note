package com.morirain.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.BR;
import com.morirain.flowmemo.R;
import com.morirain.flowmemo.adapter.FolderAdapter;
import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.presenter.DrawerContentPresenter;


import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */


public class FolderViewModel extends BaseViewModel {

    private FolderAdapter mAdapter = new FolderAdapter(BR.folder, R.layout.item_drawer_folder, new DrawerContentPresenter());

    private MutableLiveData<List<Folder>> mFolderList = mAdapter.DataList;


    public FolderViewModel() {
        getFolderData();
    }

    private void getFolderData() {
        Folder notes = new Folder("所有笔记", null);
        mFolderList.getValue().add(notes);
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }
}
