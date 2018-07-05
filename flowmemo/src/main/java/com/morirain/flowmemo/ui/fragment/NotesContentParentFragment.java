package com.morirain.flowmemo.ui.fragment;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseApplication;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.base.BasePagerAdapter;
import com.morirain.flowmemo.databinding.FragmentNotesContentParentBinding;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotesContentParentFragment extends BaseFragment<FragmentNotesContentParentBinding, NotesContentViewModel> {

    // for drawer
    private MaterialMenuDrawable mMenuDrawable;

    private Notes mNote;

    // 渐变处理
    private int mEndColor;


    public static NotesContentParentFragment getInstance(Notes note) {
        NotesContentParentFragment fragment = new NotesContentParentFragment();
        fragment.mNote = note;
        return fragment;
    }

    @Override
    protected void setArguments() {
        if (mNote != null) {
            getViewModel().notesContent.setValue(mNote.noteContent.getValue());
            getViewModel().notesLabel.setValue(mNote.noteLabel.getValue());
            getViewModel().notesPath = mNote.notePath;
            getViewModel().setDefaultContentEvent.setValue(mNote.noteContent.getValue());
        }

    }

    @Override
    protected void init(Bundle savedInstanceState) {


        initFragment();
        initListener();
        initToolbar();
        initEvent();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(NotesContentFragment.getInstance());
        fragmentList.add(NotesContentPreviewFragment.getInstance());
        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList);
        getBinding().vpFragment.setAdapter(basePagerAdapter);
        getBinding().vpFragment.setCurrentItem(0);

    }

    private void initListener() {
        int mColors[] = {getColor(R.color.colorEditViewBackground), getColor(R.color.colorBackground)};
        getBinding().vpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ArgbEvaluator evaluator = new ArgbEvaluator();
                //给布局设置初始颜色
                getBinding().vpFragment.setBackgroundColor(mColors[position]);
                //计算不同页面的结束颜色，最后一张的颜色是第一个颜色，其他的分别加一
                mEndColor = position == mColors.length - 1 ? mColors[0] : mColors[position + 1];
                //根据positionOffset得到渐变色，因为positionOffset本身为0~1之间的小数所以无需多做处理了
                int evaluate = (int) evaluator.evaluate(positionOffset, mColors[position], mEndColor);

                //最后设置渐变背景色给布局
                getBinding().vpFragment.setBackgroundColor(evaluate);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                int currentItem = getBinding().vpFragment.getCurrentItem();
                if (i == 2) {
                    if (currentItem == 1) {
                        InputMethodManager imm = (InputMethodManager) BaseApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null)
                            imm.hideSoftInputFromWindow(getBinding().vpFragment.getWindowToken(), 0);
                        getBinding().getRoot().clearFocus();
                        /*ValueAnimator colorAnim = ObjectAnimator.ofInt(getBinding().vpFragment,"backgroundColor", R.color.colorEditViewBackground);
                        colorAnim.setDuration(2000);
                        colorAnim.setEvaluator(new ArgbEvaluator()); colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                        colorAnim.start();*/
                    } else if (currentItem == 0) {
                        getBinding().vpFragment.requestFocus();
                    }
                }

            }
        });
    }

    private void initToolbar() {
        setHasOptionsMenu(true);

        mMenuDrawable = new MaterialMenuDrawable(getContext(), getColor(R.color.colorButton), MaterialMenuDrawable.Stroke.THIN);
        mMenuDrawable.setIconState(MaterialMenuDrawable.IconState.ARROW);

        Toolbar toolbar = getBinding().toolbarNotesContentParent.toolbar;
        toolbar.setNavigationIcon(mMenuDrawable);

        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);

        // show home
        ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // title clearFocus
        getBinding().toolbarNotesContentParent.etToolbarNotesContentTitle.clearFocus();
    }

    private void initEvent() {
        getViewModel().isContentChangeEvent.observe(this, aBoolean -> {
                    if (aBoolean == null) aBoolean = false;
                    if (aBoolean) {
                        mMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.CHECK);
                    } else {
                        mMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                    }
                }
        );
    }

    private int getColor(int colorResources) {
        return ContextCompat.getColor(getActivity(), colorResources);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_notes_content, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.menu_toolbar_notes_content_undo:
                getViewModel().onUndoClickEvent.call();
                break;
            case R.id.menu_toolbar_notes_content_redo:
                getViewModel().onRedoClickEvent.call();
                break;
            default:
        }
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes_content_parent;
    }

    @Override
    protected Class<NotesContentViewModel> getViewModelClass() {
        return NotesContentViewModel.class;
    }

}
