package com.morirain.jgit.utils;


/**
 * Created by morirain on 2018/5/26.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class CallbackCommand {
    private LastCallback mLastCallback;
    private Boolean mIsComplete;
    private Exception mException;

    public interface LastCallback {
        void onFinish(Boolean isComplete, Exception e);
    }

    public interface AllCommandCallback {
        void onFinish(Boolean isComplete, Throwable e);
    }

    public CallbackCommand(LastCallback lastCallback, Boolean isComplete, Exception exception) {
        this.mLastCallback = lastCallback;
        this.mIsComplete = isComplete;
        this.mException = exception;
    }
    public void call() {
        mLastCallback.onFinish(mIsComplete, mException);
    }
}
