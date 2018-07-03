package com.morirain.flowmemo.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseApplication;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.FragmentNotesContentBinding;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.syntax.edit.EditFactory;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/23
 */


public class NotesContentFragment extends BaseFragment<FragmentNotesContentBinding, NotesContentViewModel> {

    BaseActivity mActivity;

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
        markdownProcessor.live(getBinding().etNotesContentMarkdown);
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
