package com.morirain.jgit.utils;

import android.os.Environment;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.morirain.jgit.utils.CallbackCommand.LastCallback;
import com.morirain.jgit.utils.CallbackCommand.AllCommandCallback;


public class JGit {

    private Observer<CallbackCommand> mObserver;

    private Observable<CallbackCommand> mObservable;

    private List<Callable> mTaskSequence = new ArrayList<>();

    private Git mNowOpenGitRepo;

    private File mNowOpenDir;

    private String mName = "empty name";

    private String mEmail = "empty@email.com";

    private LastCallback mLastCallback;

    private AllCommandCallback mAllCommandCallback;

    private Hashtable<Callable, CallbackCommand.LastCallback> mCallbackTable = new Hashtable<>();

    public JGit setLastCallback(LastCallback lastCallback) {
        if (!mTaskSequence.isEmpty()) {
            Callable nowTask = mTaskSequence.get(mTaskSequence.size() - 1);
            mCallbackTable.put(nowTask, lastCallback);
        }
        return this;
    }

    public JGit setAllCommandCallback(AllCommandCallback allCommandCallback) {
        mAllCommandCallback = allCommandCallback;
        return this;
    }

    private JGit() {
        mObservable = Observable.create(new ObservableOnSubscribe<CallbackCommand>() {
            @Override
            public void subscribe(ObservableEmitter<CallbackCommand> emitter) {
                if (!mTaskSequence.isEmpty()) {
                    for (Callable command : mTaskSequence) {

                        if (command instanceof GitCommand || command instanceof InitCommand) {
                            LastCallback commandLastCallback = mCallbackTable.get(command);
                            try {
                                // Init 操作时顺便设置 mNowOpenGitRepo
                                if (mNowOpenGitRepo == null) {
                                    if (command instanceof InitCommand) {
                                        mNowOpenGitRepo = (Git) command.call();
                                    } else {
                                        emitter.onError(new RepositoryNotFoundException(mNowOpenDir));
                                    }
                                } else {
                                    command.call();
                                }
                                if (commandLastCallback != null) emitter.onNext(new CallbackCommand(commandLastCallback, true, null));
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (commandLastCallback != null) emitter.onNext(new CallbackCommand(commandLastCallback, false, e));
                                emitter.onError(e);
                            }
                        }

                    }
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        mObserver = new Observer<CallbackCommand>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

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
        try {
            mNowOpenGitRepo = Git.open(repoDir);
        } catch (IOException e) {
            //NoExistedRepo
            //e.printStackTrace();
        }
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
                Git.init()
                        .setDirectory(mNowOpenDir)
                        .setBare(false)
        );
        return this;
    }

    public JGit addAll() {
        mTaskSequence.add(
                mNowOpenGitRepo.add().addFilepattern(".")
        );
        return this;
    }

    public JGit commitAll(String commitMessage) {
        mTaskSequence.add(
                mNowOpenGitRepo
                        .commit()
                        .setAll(true)
                        .setCommitter(mName, mEmail)
                        .setMessage(commitMessage)
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
        mTaskSequence.add(
                Git.cloneRepository()
                        .setURI(remoteUrl)
                        .setDirectory(toDir)
        );
        return this;
    }

    public JGit clone(String remoteUrl) {
        clone(remoteUrl, mNowOpenDir);
        return this;
    }

    public JGit push(String remoteUrl) {
        mTaskSequence.add(
                mNowOpenGitRepo.push()
                        .setRemote(remoteUrl)
        );
        return this;
    }

    public void call() {

        if (mObserver != null) {
            mObservable.subscribe(mObserver);
        }
    }
}
