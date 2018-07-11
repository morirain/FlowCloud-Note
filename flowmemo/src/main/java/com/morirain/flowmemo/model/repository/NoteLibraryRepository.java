package com.morirain.flowmemo.model.repository;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.utils.ApplicationConfig;
import com.morirain.flowmemo.model.Folder;
import com.morirain.jgit.utils.JGit;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/30
 */


public class NoteLibraryRepository {

    private final static int ALL_FOLDER = 0;

    private final static int UNCLASSIFIED_FOLDER = 1;

    private MutableLiveData<List<Folder>> mFolderList = new MutableLiveData<>();

    private HashMap<String, Folder> mFolderMap = new HashMap<>();

    private MutableLiveData<Folder> mCurrentFolder = new MutableLiveData<>();

    private Folder mPostCurrentFolder;

    public boolean saveRefreshLock = false;

    public NoteLibraryRepository() {
        getFolderListValue().add(new Folder("所有笔记"));
        getFolderListValue().add(new Folder("未分类"));
        refreshData();
    }

    public MutableLiveData<List<Folder>> getFolderList() {
        return mFolderList;
    }

    public List<Folder> getFolderListValue() {
        if (mFolderList.getValue() == null) mFolderList.setValue(new ArrayList<>());
        return mFolderList.getValue();
    }

    public boolean refreshData() {
        if (saveRefreshLock) return false;
        scanFolderList();
        scanNotes();
        mFolderList.setValue(mFolderList.getValue());
        setCurrentFolder(mPostCurrentFolder);
        mPostCurrentFolder = null;
        return true;
    }

    public void scanFolderList() {
        File root = ApplicationConfig.FLOWMEMO_ROOT_PATH;

        String currentFolderName = "";
        if (getCurrentFolder().getValue() != null && getFolderListValue() != null &&
                isRealFolder(getCurrentFolder().getValue())) {
            currentFolderName = getCurrentFolder().getValue().folderName.getValue();
        } else {
            mPostCurrentFolder = getAllFolder();
        }

        // 如果文件夹为空
        boolean isEmpty = true;
        // 已存在的文件夹列表
        List<Folder> existFolderList = new ArrayList<>();
        // 创建文件夹
        if (FileUtils.createOrExistsDir(root)) {
            // 根目录
            Folder rootFolder = mFolderMap.get(ApplicationConfig.FLOWMEMO_ROOT_PATH_NAME);
            if (rootFolder == null)
                mFolderMap.put(ApplicationConfig.FLOWMEMO_ROOT_PATH_NAME, getUnclassifiedFolder());
            // 目录内所有文件夹
            List<File> allFolder = FileUtils.listFilesInDir(root);
            for (int i = 0; i < allFolder.size(); i++) {
                File file = allFolder.get(i);
                isEmpty = false;
                // 是文件夹 并且不是.git目录
                if (FileUtils.isDir(file) && !file.getName().equals(".git")) {
                    // 文件夹已经登记 读取文件夹 存入已存在的文件夹列表Folder
                    Folder folder = mFolderMap.get(file.getName());
                    if (folder != null & getFolderFile(folder).exists()) {
                        existFolderList.add(folder);
                    } else {
                        // 文件夹已存在 但没有登记
                        folder = new Folder(file);
                        mFolderMap.put(file.getName(), folder);
                        existFolderList.add(folder);
                    }
                }
            }
        }

        Iterator<Folder> iterable = getFolderListValue().iterator();
        while (iterable.hasNext()) {
            // 获取界面中的文件夹
            Folder folder = iterable.next();
            // 如果已存在的文件夹中不存在界面中存在的Folder 则删除界面中的Folder
            if (isRealFolder(folder) && !existFolderList.contains(folder)) {
                for (int i = 0; i < folder.getNotesListValue().size(); i++) {
                    Notes note = folder.getNotesListValue().get(i);
                    getAllFolder().getNotesListValue().remove(note);
                }
                iterable.remove();
            }
        }

        // 如果已存在的文件夹列表在界面中不存在 则添加
        for (int i = 0; i < existFolderList.size(); i++) {
            Folder folder = existFolderList.get(i);
            if (!getFolderListValue().contains(folder)) {
                getFolderListValue().add(folder);
                if (folder.folderName.getValue().equals(currentFolderName))
                    mPostCurrentFolder = folder;
            }
        }

        // 删除Map中无用项
        for (Iterator<Map.Entry<String, Folder>> folder = mFolderMap.entrySet().iterator(); folder.hasNext(); ) {
            Map.Entry<String, Folder> value = folder.next();
            if (!getFolderFile(value.getValue()).exists()) {
                if (getCurrentFolder().getValue().equals(value.getValue()))
                    mPostCurrentFolder = getAllFolder();
                folder.remove();
            }
        }
        // 如果目录为空白 初始化Git
        if (isEmpty) JGit.with(root).init().call();
    }

    public void scanNotes() {
        File root = ApplicationConfig.FLOWMEMO_ROOT_PATH;
        // 遍历目录下所有md文件 包括子目录
        if (FileUtils.createOrExistsDir(root)) {
            ArrayList<Notes> addNotesList = new ArrayList<>();
            ArrayList<Notes> updateNotesList = new ArrayList<>();
            SimpleArrayMap<Notes, Integer> updateNotesPositionMap = new SimpleArrayMap<>();
            List<File> existNotesFileList = new ArrayList<>();
            for (File file : FileUtils.listFilesInDir(root, true))
                if (!FileUtils.isDir(file) && FileUtils.getFileExtension(file).equals("md")) {
                    // 遍历目前所有Folder中的子Note file是目录中已存在的note文件 不包含非真实文件夹
                    existNotesFileList.add(file);
                    // 判断是否为相同的文件
                    Notes gotSameNote = null;
                    // 获取到的位置
                    int gotSameNotePosition = 0;
                    for (int i = 1; i < getFolderListValue().size(); i++) {
                        Folder folder = getFolderListValue().get(i);

                        // 用于判断需要进行数据更新的相同Note
                        List<Notes> notesListValue = folder.getNotesListValue();
                        for (int j = 0; j < notesListValue.size(); j++) {
                            Notes note = notesListValue.get(j);
                            if (isSameNoteFile(note, file)) {
                                gotSameNote = note;
                                gotSameNotePosition = j;
                                break;
                            }
                        }
                    }
                    // 如果没有相同的Note
                    if (gotSameNote == null) {
                        addNotesList.add(getNoteFromFile(file));
                    } else {
                        updateNotesList.add(gotSameNote);
                        updateNotesPositionMap.put(gotSameNote, gotSameNotePosition);
                    }


                }


            // 先对数据进行更新
            for (int i = 0; i < updateNotesList.size(); i++) {
                Notes note = updateNotesList.get(i);
                // 更新后的Note
                Notes newNote = getNoteFromFile(note.notePath);
                // 需要更新的位置
                int notePosition = updateNotesPositionMap.get(note);
                int allFolderNotePosition = getAllFolder().getNotesListValue().indexOf(note);
                // 如果存放在根目录下
                if (!isRealFolder(note.getNoteParentDirName())) {
                    getUnclassifiedFolder().getNotesListValue().set(notePosition, newNote);
                } else {
                    getNoteFolder(note).getNotesListValue().set(notePosition, newNote);
                }
                getAllFolder().getNotesListValue().set(allFolderNotePosition, newNote);
            }
            // 其次新增数据
            for (int i = 0; i < addNotesList.size(); i++) {
                Notes note = addNotesList.get(i);
                // 如果存放在根目录下
                if (!isRealFolder(note.getNoteParentDirName())) {
                    getUnclassifiedFolder().getNotesListValue().add(note);
                } else {
                    getNoteFolder(note).getNotesListValue().add(note);
                }
                getAllFolder().getNotesListValue().add(note);
            }

            // 如果已存在的Notes中不存在界面中存在的note 则删除界面中的note
            Iterator<Notes> iterator = getAllFolder().getNotesListValue().iterator();
            while (iterator.hasNext()) {
                Notes note = iterator.next();
                if (!existNotesFileList.contains(note.notePath)) {
                    getNoteFolder(note).getNotesListValue().remove(note);
                    iterator.remove();
                }
            }

            // 更新LastUpdateTime
            for (int i = 0; i < getCurrentFolder().getValue().getNotesListValue().size(); i++) {
                getCurrentFolder().getValue().getNotesListValue().get(i).refreshDate();
            }

        }
    }

    public Notes getNoteFromFile(File noteFile) {
        String label = FileUtils.getFileNameNoExtension(noteFile);
        String content = getNoteContent(noteFile);
        String preview = getNotePreview(content);
        Date lastModified = getLastModifiedTime(noteFile);
        return new Notes(label, content, lastModified, preview, noteFile);
    }

    private String getNoteContent(File noteFile) {
        String noteContent = FileIOUtils.readFile2String(noteFile);
        if (TextUtils.isEmpty(noteContent)) return "";
        return noteContent;
    }

    private Date getLastModifiedTime(File noteFile) {
        return TimeUtils.millis2Date(FileUtils.getFileLastModified(noteFile));
    }

    private String getNotePreview(String noteContent) {
        if (noteContent.length() > ApplicationConfig.NOTE_CONTENT_PREVIEW_LENGTH) {
            return noteContent.substring(0, ApplicationConfig.NOTE_CONTENT_PREVIEW_LENGTH).trim().replace('\n', ' ');
        }
        return noteContent.trim().replace('\n', ' ');
    }

    private String getNewNoteLabel(Notes note) {
        String noteLabelValue = note.noteLabel.getValue();
        if (StringUtils.isSpace(noteLabelValue))
            return note.noteLabel + TimeUtils.millis2String(System.currentTimeMillis());
        if (noteLabelValue.length() > ApplicationConfig.NOTE_LABEL_LENGTH) {
            return noteLabelValue.substring(0, ApplicationConfig.NOTE_CONTENT_PREVIEW_LENGTH).trim().replace('\n', ' ');
        }
        return noteLabelValue.trim().replace('\n', ' ');
    }

    public void saveNote(Notes sourceNote, Notes afterNote, boolean isChangeLabel) {
        saveRefreshLock = false;
        File root = ApplicationConfig.FLOWMEMO_ROOT_PATH;
        File savePath;
        if (sourceNote != null) {
            if (!isRealFolder(sourceNote.getNoteParentDirName())) {
                savePath = new File(root, afterNote.noteLabel.getValue() + ".md");
            } else {
                savePath = new File(root, afterNote.getNoteParentDirName() + "/" + afterNote.noteLabel.getValue() + ".md");
            }
        } else {
            if (isChangeLabel) {
                savePath = new File(root, getNewNoteLabel(afterNote) + ".md");
            } else {
                savePath = new File(root, ApplicationConfig.NEW_NOTE_DEFALUT_LABEL + " " + TimeUtils.getNowString().trim().replace(':', ' ') + ".md");
            }
        }
        FileIOUtils.writeFileFromString(savePath, afterNote.noteContent.getValue());

        if (sourceNote != null) {
            Folder noteFolder;
            if (isRealFolder(afterNote.getNoteParentDirName())) {
                noteFolder = mFolderMap.get(afterNote.getNoteParentDirName());
            } else {
                noteFolder = getUnclassifiedFolder();
            }
            // 目前的NoteList
            List<Notes> currentNotes = noteFolder.getNotesListValue();
            // 获取修改前的note的位置
            int allFolderPosition = getAllFolder().getNotesListValue().indexOf(sourceNote);

            // 获取修改前的note的位置
            int position = currentNotes.indexOf(sourceNote);
            // 如果存在修改前的note 那么更新原note
            if (position != -1) {

                afterNote.notePreview.setValue(getNotePreview(afterNote.noteContent.getValue()));
                afterNote.setNoteLastUpdateDate(TimeUtils.getNowDate());

                getAllFolder().getNotesListValue().set(allFolderPosition, afterNote);
                currentNotes.set(position, afterNote);
                // notify change
                getAllFolder().getNotesList().setValue(getAllFolder().getNotesList().getValue());
                //getCurrentFolder().getValue().getNotesList().setValue(currentNotes);
                noteFolder.getNotesList().setValue(noteFolder.getNotesListValue());
                getCurrentFolder().setValue(getCurrentFolder().getValue());
            }
        }
    }

    public boolean isRealFolder(Folder folder) {
        return !(ObjectUtils.equals(folder, getAllFolder()) || ObjectUtils.equals(folder, getUnclassifiedFolder()));
    }

    public boolean isRealFolder(String fileName) {
        return !(ObjectUtils.equals(fileName, getAllFolder().folderName.getValue())
                || ObjectUtils.equals(fileName, getUnclassifiedFolder().folderName.getValue())
                || ObjectUtils.equals(fileName, ApplicationConfig.FLOWMEMO_ROOT_PATH.getName()));
    }

    private File getFolderFile(Folder folder) {
        // 预计做一个File缓存
        File root = ApplicationConfig.FLOWMEMO_ROOT_PATH;
        if (folder != null && isRealFolder(folder) && folder.folderName.getValue() != null)
            return new File(root, folder.folderName.getValue());
        return root;
    }

    // 路径一致 且文件名一致
    private boolean isSameNoteFile(Notes fNote, File noteFile) {
        //if (!ObjectUtils.equals(fNote.noteLastUpdateTime.getValue(), getLastModifiedTime(noteFile)))
        //    return false;
        if (!ObjectUtils.equals(fNote.getNoteParentDirName(), noteFile.getParentFile().getName()))
            return false;
        if (!ObjectUtils.equals(fNote.noteLabel.getValue(), FileUtils.getFileNameNoExtension(noteFile)))
            return false;
        /*if (!ObjectUtils.equals(fNote.noteContent.getValue(), sNote.noteContent.getValue()))
            return false;*/ // 没有必要浪费资源
        return true;
    }

    public Folder getAllFolder() {
        return getFolderListValue().get(ALL_FOLDER);
    }

    public Folder getUnclassifiedFolder() {
        return getFolderListValue().get(UNCLASSIFIED_FOLDER);
    }

    public Folder getNoteFolder(Notes note) {
        return mFolderMap.get(note.getNoteParentDirName());
    }

    public MutableLiveData<Folder> getCurrentFolder() {
        if (mCurrentFolder.getValue() == null) {
            mCurrentFolder.setValue(getAllFolder());
        }
        return mCurrentFolder;
    }

    public void setCurrentFolder(Folder folder) {
        if (folder == null) return;
        Folder currentFolder = getCurrentFolder().getValue();
        if (currentFolder != null) currentFolder.setSelected(false);
        folder.setSelected(true);
        getCurrentFolder().setValue(folder);
    }
}
