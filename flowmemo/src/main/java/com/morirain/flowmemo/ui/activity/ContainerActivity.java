package com.morirain.flowmemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import com.jaeger.library.StatusBarUtil;
import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.ActivityContainerBinding;
import com.morirain.flowmemo.viewmodel.PageJumpViewModel;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/23
 */


public class ContainerActivity extends BaseActivity<ActivityContainerBinding, PageJumpViewModel> {

    public static SparseArray<BaseFragment> sFragmentMap = new SparseArray<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        int key = 0;
        Intent intent = getIntent();
        if (intent != null) key = getIntent().getIntExtra("fragment", 0);
        if (key != 0) switchFragment(getBinding().container.getId(), sFragmentMap.get(key), null);
        sFragmentMap.remove(key);

        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(0, R.anim.slide_out_down);
    }

    public static void setFragment(int key, BaseFragment fragment) {
        sFragmentMap.put(key, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_container;
    }

    @Override
    protected Class<PageJumpViewModel> getViewModelClass() {
        return PageJumpViewModel.class;
    }
}
