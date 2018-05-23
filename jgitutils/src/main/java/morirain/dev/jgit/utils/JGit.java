package morirain.dev.jgit.utils;

import android.os.Environment;
import android.util.SparseArray;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JGit {

    private String mCloneFromUrl;

    private Observer<GitCommand> mObserver;

    private Observable<GitCommand> mObservable;

    private List<Callable> mTaskSequence = new ArrayList<>();

    private SparseArray<File> mSparseArray = new SparseArray<>();

    private JGit() {
        mObserver = new Observer<GitCommand>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GitCommand gitCommand) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public static JGit prepare() {
        return new JGit();
    }

    public JGit init(File toDir) {
        mTaskSequence.add(
                Git.init()
                        .setDirectory(toDir)
                        .setBare(false)
        );
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

    public JGit clone(String remoteUrl) {
        mTaskSequence.add(
                Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(new File(Environment.getExternalStorageDirectory(), "/aaa/"))
        );
        return this;
    }

    public void call() {

        if (mObserver != null) {
            mObservable = new Observable<GitCommand>() {
                @Override
                protected void subscribeActual(Observer<? super GitCommand> observer) {
                    if (!mTaskSequence.isEmpty()) {
                        try {
                            for (Callable command : mTaskSequence) {
                                if (command instanceof InitCommand
                                        || command instanceof AddCommand
                                        || command instanceof CloneCommand) {
                                    command.call();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        }
    }

}
