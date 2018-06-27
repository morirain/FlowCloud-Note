package com.morirain.flowmemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.base.BasePagerAdapter;
import com.morirain.flowmemo.databinding.FragmentNotesContentParentBinding;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotesContentParentFragment extends BaseFragment<FragmentNotesContentParentBinding, NotesContentViewModel> {

    BaseActivity mActivity;

    @Override
    protected void init(Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NotesContentFragment());
        fragmentList.add(new NotesContentPreviewFragment());
        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(mActivity.getSupportFragmentManager(), fragmentList);
        getBinding().vpFragment.setAdapter(basePagerAdapter);
        getBinding().vpFragment.setCurrentItem(0);

        initToolbar();
    }

    private void initToolbar() {
        setHasOptionsMenu(true);
        mActivity.setSupportActionBar(getBinding().toolbarNotesContentParent.toolbar);
        // show home
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // title clearFocus
        getBinding().toolbarNotesContentParent.etToolbarNotesContentTitle.clearFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_notes_content, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.onBackPressed();
                break;
            case R.id.menu_toolbar_notes_content_undo:
                break;
            case R.id.menu_toolbar_notes_content_redo:
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
        return R.layout.fragment_notes_content_parent;
    }
    @Override
    protected Class<NotesContentViewModel> getViewModelClass() {
        return NotesContentViewModel.class;
    }
    @Override
    protected BaseCommandHandler getHandler() {
        return null;
    }
}
