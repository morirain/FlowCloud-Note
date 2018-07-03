package com.morirain.flowmemo.ui.fragment;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseApplication;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.FragmentNotesContentBinding;
import com.morirain.flowmemo.utils.EditTextMonitor;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.syntax.edit.EditFactory;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/23
 */


public class NotesContentFragment extends BaseFragment<FragmentNotesContentBinding, NotesContentViewModel> {

    private BaseActivity mActivity;

    private EditTextMonitor mEditTextMonitor;

    @Override
    protected void init(Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        initEdit();
        initEvent();
    }

    private void initEdit() {
        MarkdownProcessor markdownProcessor = new MarkdownProcessor(mActivity);
        //markdownProcessor.config(markdownConfiguration);
        markdownProcessor.factory(EditFactory.create());
        mEditTextMonitor = new EditTextMonitor(getBinding().etNotesContentMarkdown);
        markdownProcessor.live(getBinding().etNotesContentMarkdown);

        // 为监听器设置默认数据
        getViewModel().setDefaultContentEvent.observe(this, mEditTextMonitor::setDefaultText);

        // 监听有无修改
        /*mEditTextMonitor.isUndoHistoryEmpty.observe(this, b -> {
            if (b == null || !b) {
                getViewModel().isContentChangeEvent.setValue(true);
            } else {
                getViewModel().isContentChangeEvent.setValue(false);
            }
        });*/
        requestFocus();
    }

    private void initEvent() {
        getViewModel().requestFocusEvent.observe(this, o -> {
            requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) BaseApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(getBinding().etNotesContentMarkdown, 0);
            }
        });

        getViewModel().onUndoClickEvent.observe(this, o -> mEditTextMonitor.undo());
        getViewModel().onRedoClickEvent.observe(this, o -> mEditTextMonitor.redo());
    }

    public void requestFocus() {
        getBinding().etNotesContentMarkdown.requestFocus();
    }

    @Override
    protected void setArguments() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes_content;
    }

    @Override
    protected Class<NotesContentViewModel> getViewModelClass() {
        return NotesContentViewModel.class;
    }

}
