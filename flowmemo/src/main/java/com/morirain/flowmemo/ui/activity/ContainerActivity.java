package com.morirain.flowmemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import com.blankj.utilcode.util.LogUtils;
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

    private int mKey = 0;

    @Override
    protected void init(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mKey = savedInstanceState.getInt("fragmentKey");
        } else {
            Intent intent = getIntent();
            if (intent != null) mKey = getIntent().getIntExtra("fragment", 0);
        }
        if (mKey != 0) switchFragment(getBinding().container.getId(), sFragmentMap.get(mKey), null);
        //sFragmentMap.remove(mKey);

        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragmentKey", mKey);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sFragmentMap.remove(mKey);
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
