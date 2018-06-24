package com.morirain.flowmemo.view.fragment;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;
import com.morirain.flowmemo.databinding.FragmentNotesContentBinding;
import com.morirain.flowmemo.viewmodel.handler.FragmentNotesContentHandler;
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
        initToolbar();
        initEdit();
    }

    private void initToolbar() {
        setHasOptionsMenu(true);
        mActivity.setSupportActionBar(getBinding().toolbarNotesContent.toolbar);

        // show home
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // title clearFocus
        getBinding().toolbarNotesContent.etToolbarNotesContentTitle.clearFocus();
    }

    private void initEdit() {
        MarkdownProcessor markdownProcessor = new MarkdownProcessor(mActivity);
        //markdownProcessor.config(markdownConfiguration);
        markdownProcessor.factory(EditFactory.create());
        markdownProcessor.live(getBinding().etNotesContentMarkdown);
        requestFocus();
    }

    public void requestFocus() {
        getBinding().etNotesContentMarkdown.requestFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.onBackPressed();
                break;
            default:
        }
        return true;
    }


    @Override
    protected void setCustomViewModelConnect() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes_content;
    }

    @Override
    protected Class<NotesContentViewModel> getViewModelClass() {
        return NotesContentViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return new FragmentNotesContentHandler(this);
    }
}
