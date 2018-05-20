package morirain.dev.jgit.utils;

import android.os.Environment;
import android.util.Log;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

public class JGit {

    private String mCloneFromUrl;

    private Observer<Object> mObserver;

    private Observable<Object> mObservable;

    private List<GitCommand> mTaskSequence = new ArrayList<>();

    private JGit() {
    }

    public static JGit prepare() {
        JGit jGit = new JGit();
        jGit.mObserver = new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        return new JGit();
    }

    /*public JGit to(String toDir, Boolean isExternal) {
        if (!toDir.isEmpty()) {
            if (isExternal) {
                mToDir = new File(Environment.getExternalStorageDirectory(), toDir);
                if(!mToDir.exists()) {
                    mToDir.mkdirs();
                }
                return this;
            }
        }
    }*/

    public JGit clone(String remoteUrl) {
        mCloneFromUrl = remoteUrl;
        mTaskSequence.add(
                Git.cloneRepository()
                .setURI(mCloneFromUrl)
                .setDirectory(new File(remoteUrl))
        );
        return this;
    }

    public void call() {

        if (mObserver != null) {
            mObservable = new Observable<Object>() {
                @Override
                protected void subscribeActual(Observer<? super Object> observer) {

                }
            };
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        }
        if (!mCloneFromUrl.isEmpty()) {
            final File toPath = new File(Environment.getExternalStorageDirectory(), "/rsad1p/");

            Single.just(toPath)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<File>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(File file) {
                            try {
                                Git.cloneRepository()
                                        .setURI(mCloneFromUrl)
                                        .setDirectory(file)
                                        .call();
                            } catch (GitAPIException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }

}
