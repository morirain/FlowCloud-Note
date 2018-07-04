package com.morirain.flowmemo.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Stack;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/7/3
 */


public class EditTextMonitor {

    public interface IsChange {
        void isChange(boolean isChange);
    }

    //操作序号(一次编辑可能对应多个操作，如替换文字，就是删除+插入)
    private int index;
    //撤销栈
    private Stack<Action> mUndoHistory = new Stack<>();
    //恢复栈
    private Stack<Action> mRedoHistory = new Stack<>();

    private Editable mEditable;
    private EditText mEditText;
    //自动操作标志，防止重复回调,导致无限撤销
    private boolean mFlag = false;
    //如果文本不一致的回调
    private IsChange mIsChange;
    private boolean mChanged = false;
    private String mDefaultContent;
    //设置时间延迟 以获得更好的效果
    private int mTime;

    public EditTextMonitor(@NonNull EditText editText) {
        this.mEditable = editText.getText();
        this.mEditText = editText;
        editText.addTextChangedListener(new Watcher());
    }

    public void setIsChangeListener(IsChange isChange) {
        mIsChange = isChange;
    }

    private void onEditableChanged(Editable s) {

    }

    private void onTextChanged(Editable s) {
        if (s == null) s = mEditable;
        if (mIsChange != null && mDefaultContent != null) {
            boolean changeBefore = !mDefaultContent.contentEquals(s);
            if (changeBefore != mChanged) {
                mChanged = changeBefore;
                mIsChange.isChange(changeBefore);
            }
        }
    }


    /**
     * 首次设置文本
     * Set default text.
     */
    public final void setDefaultText(String text) {
        clearHistory();
        mFlag = true;
        mEditable.replace(0, mEditable.length(), text);
        mDefaultContent = text;
        mFlag = false;
    }

    /**
     * 清理记录
     * Clear mUndoHistory.
     */
    public final void clearHistory() {

        mUndoHistory.clear();
        mRedoHistory.clear();
    }

    /**
     * 撤销
     * Undo.
     */
    public final void undo() {
        if (mUndoHistory.empty()) return;
        //锁定操作
        mFlag = true;
        Action action = mUndoHistory.pop();
        mRedoHistory.push(action);
        if (action.mIsAdd) {
            //撤销添加
            mEditable.delete(action.mStartCursor, action.mStartCursor + action.mCharSequence.length());
            mEditText.setSelection(action.mStartCursor, action.mStartCursor);
        } else {
            //插销删除
            mEditable.insert(action.mStartCursor, action.mCharSequence);
            if (action.mEndCursor == action.mStartCursor) {
                mEditText.setSelection(action.mStartCursor + action.mCharSequence.length());
            } else {
                mEditText.setSelection(action.mStartCursor, action.mEndCursor);
            }
        }
        //释放操作
        mFlag = false;
        //判断是否是下一个动作是否和本动作是同一个操作，直到不同为止
        if (!mUndoHistory.empty() && mUndoHistory.peek().mIndex == action.mIndex) {
            undo();
        }
        onTextChanged(null);
    }

    /**
     * 恢复
     * Redo.
     */
    public final void redo() {
        if (mRedoHistory.empty()) return;
        mFlag = true;
        Action action = mRedoHistory.pop();
        mUndoHistory.push(action);
        if (action.mIsAdd) {
            //恢复添加
            mEditable.insert(action.mStartCursor, action.mCharSequence);
            if (action.mEndCursor == action.mStartCursor) {
                mEditText.setSelection(action.mStartCursor + action.mCharSequence.length());
            } else {
                mEditText.setSelection(action.mStartCursor, action.mEndCursor);
            }
        } else {
            //恢复删除
            mEditable.delete(action.mStartCursor, action.mStartCursor + action.mCharSequence.length());
            mEditText.setSelection(action.mStartCursor, action.mStartCursor);
        }
        mFlag = false;
        //判断是否是下一个动作是否和本动作是同一个操作
        if (!mRedoHistory.empty() && mRedoHistory.peek().mIndex == action.mIndex)
            redo();
        onTextChanged(null);
    }

    private class Watcher implements TextWatcher {

        /**
         * Before text changed.
         *
         * @param s     the s
         * @param start the start 起始光标
         * @param count the mEndCursor 选择数量
         * @param after the after 替换增加的文字数
         */
        @Override
        public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (mFlag) return;
            int end = start + count;
            if (end > start && end <= s.length()) {
                CharSequence charSequence = s.subSequence(start, end);
                //删除了文字
                if (charSequence.length() > 0) {
                    Action action = new Action(charSequence, start, false);
                    if (count > 1) {
                        //如果一次超过一个字符，说名用户选择了，然后替换或者删除操作
                        action.setSelectCount(count);
                    } else if (count == 1 && count == after) {
                        //一个字符替换
                        action.setSelectCount(count);
                    }
                    //还有一种情况:选择一个字符,然后删除(暂时没有考虑这种情况)

                    mUndoHistory.push(action);
                    mRedoHistory.clear();
                    action.setIndex(++index);
                }
            }
        }

        /**
         * On text changed.
         *
         * @param s      the s
         * @param start  the start 起始光标
         * @param before the before 选择数量
         * @param count  the mEndCursor 添加的数量
         */
        @Override
        public final void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mFlag) return;
            int end = start + count;
            if (end > start) {
                CharSequence charSequence = s.subSequence(start, end);
                //添加文字
                if (charSequence.length() > 0) {
                    Action action = new Action(charSequence, start, true);

                    mUndoHistory.push(action);
                    mRedoHistory.clear();
                    if (before > 0) {
                        //文字替换（先删除再增加），删除和增加是同一个操作，所以不需要增加序号
                        action.setIndex(index);
                    } else {
                        action.setIndex(++index);
                    }
                }
            }
        }

        @Override
        public final void afterTextChanged(Editable s) {
            if (mFlag) return;
            if (s != mEditable) {
                mEditable = s;
                onEditableChanged(s);
            }
            EditTextMonitor.this.onTextChanged(s);
        }

    }

    public class Action {
        /**
         * 改变字符.
         */
        CharSequence mCharSequence;
        /**
         * 光标位置.
         */
        int mStartCursor;
        int mEndCursor;
        /**
         * 标志增加操作.
         */
        boolean mIsAdd;
        /**
         * 操作序号.
         */
        int mIndex;


        public Action(CharSequence actionTag, int startCursor, boolean add) {
            this.mCharSequence = actionTag;
            this.mStartCursor = startCursor;
            this.mEndCursor = startCursor;
            this.mIsAdd = add;
        }

        public void setSelectCount(int count) {
            this.mEndCursor = mEndCursor + count;
        }

        public void setIndex(int index) {
            this.mIndex = index;
        }
    }

}
