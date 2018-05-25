package com.morirain.jgit.utils;

import android.app.Application;
import android.os.Environment;
import android.util.SparseArray;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class JGit {

    private CompletableObserver mObserver;

    private Completable mObservable;

    private List<Callable> mTaskSequence = new ArrayList<>();

    private Git mNowOpenGitRepo;

    private String mName = "empty name";

    private String mEmail = "empty@email.com";

    private JGit() {
        mObservable = Completable.create(emitter -> {
            if (!mTaskSequence.isEmpty()) {
                for (Callable command : mTaskSequence) {

                    if (command instanceof GitCommand || command instanceof InitCommand) {
                        command.call();
                    }

                }
                emitter.onComplete();
            }
        });

        mObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
    }

    public static JGit prepare() {
        return new JGit();
    }

    public JGit setAuthor(String name, String email) {
        mName = name;
        mEmail = email;
        return this;
    }

    public JGit init(File toDir) {
        mTaskSequence.add(
                Git.init()
                        .setDirectory(toDir)
                        .setBare(false)
        );
        return this;
    }

    public JGit openExistedRepo(File repoDir) {
        try {
            mNowOpenGitRepo = Git.open(repoDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JGit addAll(File gitRepoDir) {
        try {
            mTaskSequence.add(
                    Git.open(gitRepoDir).add().addFilepattern(".")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JGit commitAll(Git git, String commitMessage) {
        mTaskSequence.add(
                git
                        .commit()
                        .setAll(true)
                        .setCommitter(mName, mEmail)
                        .setMessage(commitMessage)
        );
        return this;
    }

    public JGit commitAll(String commitMessage) {
        if (mNowOpenGitRepo != null) commitAll(mNowOpenGitRepo, commitMessage);
        return this;
    }

    public JGit commitAll(File repoDir, String commitMessage) {
        openExistedRepo(repoDir);
        commitAll(commitMessage);
        return this;
    }

    public JGit clone(String remoteUrl, String toExternalDir) {
        File toDir;
        String child = "GitRepo";
        if (toExternalDir.isEmpty()) {
            Pattern patterns = Pattern.compile("[a-zA-Z-]*\\.git");
            Matcher matcher = patterns.matcher(remoteUrl);
            if (matcher.find()) {
                child = matcher.group();
            }
        } else {
            child = toExternalDir;
        }
        toDir = new File(Environment.getExternalStorageDirectory(), child);
        mTaskSequence.add(
                Git.cloneRepository()
                        .setURI(remoteUrl)
                        .setDirectory(toDir)
        );
        return this;
    }

    public JGit clone(String remoteUrl) {
        clone(remoteUrl, "");
        return this;
    }

    public JGit push(String remoteUrl) {

        return this;
    }

    public void call() {

        if (mObserver != null) {
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        }
    }

}
