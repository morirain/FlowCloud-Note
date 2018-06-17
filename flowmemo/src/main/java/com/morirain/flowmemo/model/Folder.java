package com.morirain.flowmemo.model;


import android.arch.lifecycle.MutableLiveData;

import java.io.File;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */

@Entity
public class Folder {

    @Id
    long boxId;

    @Transient
    public MutableLiveData<String> folderName = new MutableLiveData<>();
    @Transient
    public MutableLiveData<File> folderDir = new MutableLiveData<>();

    public Folder(String folderName, File folderDir) {
        this.folderName.postValue(folderName);
        this.folderDir.postValue(folderDir);
    }
}
