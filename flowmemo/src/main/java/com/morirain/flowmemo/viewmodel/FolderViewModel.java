package com.morirain.flowmemo.viewmodel;


import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.databinding.ItemDrawerFolderBinding;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.viewmodel.handler.ItemDrawerFolderHandler;


import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */


public class FolderViewModel extends BaseViewModel {

    private BaseAdapter mAdapter;// = new FolderAdapter(BR.folder, R.layout.item_drawer_folder, new ItemDrawerFolderHandler());

    private List<Folder> mList;// = mAdapter.mDataList;


    public FolderViewModel() {
    }

    public void setAdapter(BaseAdapter<Folder, ItemDrawerFolderBinding> adapter){
        mAdapter = adapter;
        mAdapter.setHandler(new ItemDrawerFolderHandler());
        mList = adapter.getDataList().getValue();
        getListData();
    }

    private void getListData() {
        Folder notes = new Folder("所有笔记", null);
        mList.add(notes);
    }
}
