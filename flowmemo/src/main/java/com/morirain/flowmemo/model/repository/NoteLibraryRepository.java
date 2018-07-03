package com.morirain.flowmemo.model.repository;

import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.utils.ApplicationConfig;
import com.morirain.flowmemo.model.Folder;
import com.morirain.jgit.utils.JGit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/30
 */


public class NoteLibraryRepository {

    private final static int ALL_FOLDER = 0;

    private final static int UNCLASSIFIED_FOLDER = 1;

    private MutableLiveData<List<Folder>> mFolderList = new MutableLiveData<>();

    private MutableLiveData<Folder> mCurrentFolder = new MutableLiveData<>();

    public NoteLibraryRepository() {
        getFolderListValue().add(new Folder("所有笔记"));
        getFolderListValue().add(new Folder("未分类"));
        setCurrentFolder(getAllFolder());
        scanRootFolder();
    }

    public MutableLiveData<List<Folder>> getFolderList() {
        return mFolderList;
    }

    public List<Folder> getFolderListValue() {
        if (mFolderList.getValue() == null) mFolderList.setValue(new ArrayList<>());
        return mFolderList.getValue();
    }

    public List<Folder> scanRootFolder() {
        File root = ApplicationConfig.FLOWMEMO_ROOT_PATH;
        boolean isEmpty = true;
        if (FileUtils.createOrExistsDir(root)) {
            for (File file : FileUtils.listFilesInDir(root)) {
                isEmpty = false;
                if (FileUtils.isDir(file)) {
                    // Filter
                    if (!file.getName().equals(".git")) getFolderListValue().add(new Folder(file));
                } else {
                    if (FileUtils.getFileExtension(file).equals("md")) {
                        Notes note = getNoteFromFile(file);
                        getAllFolder().getNotesList().add(note);
                        getUnclassifiedFolder().getNotesList().add(note);
                    }
                }
            }
            if (isEmpty) JGit.with(root).init().call();
        }
        return getFolderListValue();
    }

    public Notes getNoteFromFile(File noteFile) {
        String label = FileUtils.getFileNameNoExtension(noteFile);
        String content = getNoteContent(noteFile);
        String lastModified = TimeUtils.millis2String(FileUtils.getFileLastModified(noteFile));
        String preview = getNotePreview(content);
        return new Notes(label, content, lastModified, preview);
    }

    private String getNoteContent(File noteFile) {
        String noteContent = FileIOUtils.readFile2String(noteFile);
        if (TextUtils.isEmpty(noteContent)) return "";
        return noteContent;
    }

    private String getNotePreview(String noteContent) {
        if (noteContent.length() > ApplicationConfig.NOTE_CONTENT_PREVIEW_LENGTH) {
            return noteContent.substring(0, ApplicationConfig.NOTE_CONTENT_PREVIEW_LENGTH).trim().replace('\n', ' ');
        }
        return noteContent.trim().replace('\n', ' ');
    }

    public Folder getAllFolder() {
        return getFolderListValue().get(ALL_FOLDER);
    }

    public Folder getUnclassifiedFolder() {
        return getFolderListValue().get(UNCLASSIFIED_FOLDER);
    }

    public MutableLiveData<Folder> getCurrentFolder() {
        return mCurrentFolder;
    }

    public void setCurrentFolder(Folder folder) {
        Folder currentFolder = mCurrentFolder.getValue();
        if (currentFolder != folder) {
            if (currentFolder != null) currentFolder.setSelected(false);
            folder.setSelected(true);
            mCurrentFolder.postValue(folder);
        }
    }
}
