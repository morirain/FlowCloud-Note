package com.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import com.flowmemo.BR;
import com.flowmemo.R;
import com.flowmemo.adapter.FolderAdapter;
import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BaseViewModel;
import com.flowmemo.databinding.ItemDrawerFolderBinding;
import com.flowmemo.model.Folder;
import com.flowmemo.presenter.DrawerPresenter;


import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */


public class FolderViewModel extends BaseViewModel {

    public FolderAdapter Adapter = new FolderAdapter(BR.folder, R.layout.item_drawer_folder, new DrawerPresenter());

    private MutableLiveData<List<Folder>> mFolderList = Adapter.DataList;


    public FolderViewModel() {
        getFolderData();
    }

    private void getFolderData() {
        Folder notes = new Folder("所有笔记", null);
        mFolderList.getValue().add(notes);
    }

    @Override
    public BaseAdapter getAdapter() {
        return Adapter;
    }
}
