package com.morirain.jgit.utils;

import android.os.Environment;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class JGit {

    private static final String TAG = "JGitUtils";

    private List<Completable> mTaskSequence = new ArrayList<>();

    private Git mNowOpenGitRepo;

    private File mNowOpenDir;

    private String mName = "empty name";

    private String mEmail = "empty@email.com";

    //身份验证
    private UsernamePasswordCredentialsProvider mUsernamePassword = new UsernamePasswordCredentialsProvider("empty", "empty");

    private AllCommandCallback mAllCommandCallback;

    public interface AllCommandCallback {
        void onFinish(Boolean isComplete, Throwable e);
    }

    public JGit setAllCommandCallback(AllCommandCallback allCommandCallback) {
        mAllCommandCallback = allCommandCallback;
        return this;
    }

    private JGit() {
    }

    private void openExistedRepo(File repoDir) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter -> {
                    try {
                        if (mNowOpenGitRepo == null) mNowOpenGitRepo = Git.open(repoDir);
                    } catch (IOException e) {
                        //NoExistedRepo
                        //e.printStackTrace();
                    }
                }));

    }

    private JGit openDir(File dir) {
        mNowOpenDir = dir;
        if (mNowOpenDir.exists()) {
            openExistedRepo(dir);
        } else {
            init();
        }
        return this;
    }

    public static JGit with(File openRepo) {
        return new JGit().openDir(openRepo);
    }
    public static JGit with(String openRepoOnExternal) {
        return new JGit().openDir(new File(Environment.getExternalStorageDirectory(), openRepoOnExternal));
    }
    public static JGit with(Git openGit) {
        JGit jGit = new JGit();
        jGit.mNowOpenDir = openGit.getRepository().getDirectory();
        jGit.mNowOpenGitRepo = openGit;
        return jGit;
    }

    public JGit setAuthorImmediate(String name, String email) {
        mName = name;
        mEmail = email;
        return this;
    }

    public JGit setAuthorOnSequence(String name, String email) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter ->
                        setAuthorImmediate(name, email)
                )
        );
        return this;
    }

    public JGit setUsernamePassword(String name, String password) {
        mUsernamePassword = new UsernamePasswordCredentialsProvider(name, password);
        return this;
    }

    public JGit init() {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter -> {
                    Git git = Git.init()
                            .setDirectory(mNowOpenDir)
                            .setBare(false)
                            .call();
                    if (mNowOpenGitRepo == null && git != null) mNowOpenGitRepo = git;
                }));
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

    public JGit clone(String remoteUrl, File toDir) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter -> {
                            Git git = Git.cloneRepository()
                                    .setURI(remoteUrl)
                                    .setDirectory(toDir)
                                    .setCredentialsProvider(mUsernamePassword)
                                    .call();
                            if (mNowOpenGitRepo == null && git != null) mNowOpenGitRepo = git;
                        }

                )

        );
        return this;
    }

    public JGit clone(String remoteUrl) {
        clone(remoteUrl, mNowOpenDir);
        return this;
    }

    public JGit pull(String remoteUrl, String remoteBranchName) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter ->
                        mNowOpenGitRepo.pull()
                                .setRemoteBranchName(remoteBranchName)
                                .setRemote(remoteUrl)
                                .setCredentialsProvider(mUsernamePassword)
                                .call()
                )
        );
        return this;
    }
    public JGit pull(String remoteBranchName) {
        pull(null, remoteBranchName);
        return this;
    }
    public JGit pull() {
        pull(null, null);
        return this;
    }

    public JGit push(String remoteUrl, Boolean force, Boolean pushAllBranch) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter -> {
                    PushCommand pushCommand = mNowOpenGitRepo.push()
                            .setForce(force)
                            .setCredentialsProvider(mUsernamePassword);
                    if (!remoteUrl.isEmpty()) pushCommand.setRemote(remoteUrl);
                    if (pushAllBranch) pushCommand.setPushAll();
                    pushCommand.call();
                }));
        return this;
    }
    public JGit push(Boolean force, Boolean pushAllBranch) {
        push(null, force, pushAllBranch);
        return this;
    }
    public JGit push(Boolean force) {
        push(null, force, false);
        return this;
    }
    public JGit push() {
        push(null, false, false);
        return this;
    }


    JGit checkout(String branchName, Boolean isCreateNewBranch, String remoteName) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter -> {
                            CheckoutCommand checkoutCommand = mNowOpenGitRepo.checkout()
                                    .setName(branchName);
                            if (isCreateNewBranch) checkoutCommand.setCreateBranch(true);
                            if (!remoteName.isEmpty()) {
                                checkoutCommand
                                        .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                                        .setStartPoint(remoteName + "/" + branchName);
                            }
                            checkoutCommand.call();
                        }
                ));
        return this;
    }

    public JGit checkoutNewBranch(String createBranchName) {
        checkout(createBranchName, true, null);
        return this;
    }
    public JGit checkoutExistedBranch(String checkoutBranchName) {
        checkout(checkoutBranchName, false, null);
        return this;
    }
    public JGit checkoutRemoteBranch(String remoteName, String checkoutBranchName) {
        checkout(checkoutBranchName, true, remoteName);
        return this;
    }

    public JGit addRemote(String remoteUri, String remoteName) {
        mTaskSequence.add(
                JGitCreateCommand.create(emitter -> {
                            RemoteAddCommand remoteAddCommand = mNowOpenGitRepo.remoteAdd();
                            remoteAddCommand.setUri(new URIish(remoteUri));
                            remoteAddCommand.setName(remoteName);
                            remoteAddCommand.call();
                        }
                ));
        return this;
    }

    public void call() {
        Completable.concat(mTaskSequence)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        if (mNowOpenGitRepo != null) mNowOpenGitRepo.close();
                        if (mAllCommandCallback != null) mAllCommandCallback.onFinish(true, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mNowOpenGitRepo != null) mNowOpenGitRepo.close();
                        if (mAllCommandCallback != null) mAllCommandCallback.onFinish(false, e);
                    }
                });
    }
}
