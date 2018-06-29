package com.morirain.flowmemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jaeger.library.StatusBarUtil;
import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.ActivityContainerBinding;
import com.morirain.flowmemo.viewmodel.PageJumpViewModel;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/23
 */


public class ContainerActivity extends BaseActivity<ActivityContainerBinding, PageJumpViewModel> {

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Fragment fragment = (Fragment) intent.getSerializableExtra("fragment");
        if (fragment != null) switchFragment(getBinding().container.getId(), fragment, null);

        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(0, R.anim.slide_out_down);
    }

    @Override
    protected void setCustomViewModelConnect() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_container;
    }

    @Override
    protected Class<PageJumpViewModel> getViewModelClass() {
        return PageJumpViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return null;
    }
}
