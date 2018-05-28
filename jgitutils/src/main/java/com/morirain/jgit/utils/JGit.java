package com.morirain.jgit.utils;

import android.os.Environment;

import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.morirain.jgit.utils.CallbackCommand.LastCallback;
import com.morirain.jgit.utils.CallbackCommand.AllCommandCallback;


public class JGit {

    private Observer<CallbackCommand> mObserver;

    private Observable<CallbackCommand> mObservable;

    private List<Observable<CallbackCommand>> mTaskSequence = new ArrayList<>();

    private Git mNowOpenGitRepo;

    private File mNowOpenDir;

    private String mName = "empty name";

    private String mEmail = "empty@email.com";

    private LastCallback mLastCallback;

    private AllCommandCallback mAllCommandCallback;

    private Hashtable<Callable, CallbackCommand.LastCallback> mCallbackTable = new Hashtable<>();

    /*public JGit setLastCallback(LastCallback lastCallback) {
        if (!mTaskSequence.isEmpty()) {
            Callable nowTask = mTaskSequence.get(mTaskSequence.size() - 1);
            mCallbackTable.put(nowTask, lastCallback);
        }
        return this;
    }*/

    public JGit setAllCommandCallback(AllCommandCallback allCommandCallback) {
        mAllCommandCallback = allCommandCallback;
        return this;
    }

    private JGit() {
        mObserver = new Observer<CallbackCommand>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(CallbackCommand callLastCallback) {
                callLastCallback.call();
            }

            @Override
            public void onComplete() {
                if (mAllCommandCallback != null) mAllCommandCallback.onFinish(true, null);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (mAllCommandCallback != null) mAllCommandCallback.onFinish(false, e);
            }
        };
    }

    private void openExistedRepo(File repoDir) {
        mTaskSequence.add(Observable.create(emitter -> {
                    try {
                        mNowOpenGitRepo = Git.open(repoDir);
                    } catch (IOException e) {
                        //NoExistedRepo
                        //e.printStackTrace();
                    }
                })
        );

    }

    private JGit openDir(File dir) {
        mNowOpenDir = dir;
        if (mNowOpenDir.exists()) openExistedRepo(dir);
        return this;
    }

    /*public static JGit with() {
        return new JGit();
    }*/

    public static JGit with(File openRepo) {
        return new JGit().openDir(openRepo);
    }

    public static JGit with(String openRepoOnExternal) {
        return new JGit().openDir(new File(Environment.getExternalStorageDirectory(), openRepoOnExternal));
    }

    public JGit setAuthor(String name, String email) {
        mName = name;
        mEmail = email;
        return this;
    }

    public JGit init() {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter ->
                        Git.init()
                                .setDirectory(mNowOpenDir)
                                .setBare(false)
                                .call()
                )

        );
        return this;
    }

    public JGit addAll() {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter ->
                        mNowOpenGitRepo
                                .add()
                                .addFilepattern(".")
                                .call())

        );
        return this;
    }

    public JGit commitAll(String commitMessage) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter ->
                        mNowOpenGitRepo
                                .commit()
                                .setAll(true)
                                .setCommitter(mName, mEmail)
                                .setMessage(commitMessage)
                                .call())
        );
        return this;
    }

    /*public JGit commitAll(File repoDir, String commitMessage) {
        openExistedRepo(repoDir);
        commitAll(commitMessage);
        return this;
    }*/

    /*public JGit clone(String remoteUrl, String toExternalDirName) {
        File toDir;
        String child = "GitRepo";
        if (toExternalDirName.isEmpty()) {
            Pattern patterns = Pattern.compile("[a-zA-Z-]*\\.git");
            Matcher matcher = patterns.matcher(remoteUrl);
            if (matcher.find()) {
                child = matcher.group();
            }
        } else {
            child = toExternalDirName;
        }
        toDir = new File(Environment.getExternalStorageDirectory(), child);
        mTaskSequence.add(
                Git.cloneRepository()
                        .setURI(remoteUrl)
                        .setDirectory(toDir)
        );
        return this;
    }*/

    public JGit clone(String remoteUrl, File toDir) {
        mTaskSequence.add(JGitCreateCommand.create(emitter ->
                        Git.cloneRepository()
                                .setURI(remoteUrl)
                                .setDirectory(toDir)
                                .call()
                )

        );
        return this;
    }

    public JGit clone(String remoteUrl) {
        clone(remoteUrl, mNowOpenDir);
        return this;
    }

    /*public JGit push(String remoteUrl) {
        mTaskSequence.add(
                mNowOpenGitRepo.push()
                        .setRemote(remoteUrl)
        );
        return this;
    }*/

    public void call() {
        mObservable = Observable.concat(mTaskSequence)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mObservable.subscribe(mObserver);
    }
}
