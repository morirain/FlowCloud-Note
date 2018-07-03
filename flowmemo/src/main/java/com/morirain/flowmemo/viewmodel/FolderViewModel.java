package com.morirain.flowmemo.viewmodel;


import com.morirain.flowmemo.adapter.FolderAdapter;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingletonFactory;


import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */


public class FolderViewModel extends BaseViewModel {

    private FolderAdapter mAdapter;

    private List<Folder> mList;

    private NoteLibraryRepository mRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    public FolderViewModel() {
    }

    public void setAdapter(FolderAdapter adapter){
       /* mAdapter = adapter;
        mList = adapter.getDataList().getValue();
        getListData();*/
    }

    private void getListData() {
        //mList.addAll(mRepository.getFolderList());
    }

    public void onItemClick(Folder folder) {
        mRepository.setCurrentFolder(folder);
    }
}
