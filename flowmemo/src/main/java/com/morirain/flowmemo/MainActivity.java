package com.morirain.flowmemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.viewmodel.FolderViewModel;
import com.jaeger.library.StatusBarUtil;
import com.morirain.flowmemo.viewmodel.handler.DrawerContentHandler;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import com.morirain.flowmemo.adapter.PagerAdapter;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.databinding.ActivityMainBinding;
import com.morirain.flowmemo.databinding.DrawerContentBinding;
import com.morirain.flowmemo.view.fragment.MemoryFragment;
import com.morirain.flowmemo.view.fragment.NotesFragment;
import com.morirain.flowmemo.viewmodel.MainViewModel;
import com.morirain.flowmemo.viewmodel.UserProfileViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private BaseAdapter<Folder> mFolderAdapter = new BaseAdapter<>(this, R.layout.item_drawer_folder);

    private DrawerLayout mDrawer;

    private DrawerContentBinding mDrawerBinding;

    private FolderViewModel mFolderViewModel;

    private UserProfileViewModel mUserProfileViewModel;

    // for drawer
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void init(Bundle savedInstanceState) {

        initToolbar();
        initFragment();
        initDrawer();

        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable subscribe = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        // 用户已经同意该权限
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』

                    }
                });
    }

    private void initToolbar() {
        setSupportActionBar(getBinding().toolbarMain.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
        StatusBarUtil.setLightMode(this);
    }

    private void initDrawer() {
        mDrawerBinding = getBinding().drawerContent;
        mDrawer = (DrawerLayout) getBinding().getRoot();
        RecyclerView drawerRecyclerView = mDrawerBinding.recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(layoutManager);
        drawerRecyclerView.setLayoutManager(layoutManager);
        drawerRecyclerView.setAdapter(mFolderAdapter);
        mFolderViewModel.setAdapter(mFolderAdapter);

        // DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.addDrawerListener(mDrawerToggle);
    }

    private void initFragment() {
        Fragment notesFragment = new NotesFragment();
        Fragment memoryFragment = new MemoryFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(notesFragment);
        fragmentList.add(memoryFragment);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        getBinding().vpFragment.setAdapter(pagerAdapter);
        getBinding().vpFragment.setCurrentItem(0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_toolbar_setting:
                break;
            default:
        }
        return true;
    }

    @Override
    protected void setCustomViewModelConnect() {
        mFolderViewModel = getNewViewModel(BR.folderViewModel, FolderViewModel.class);
        mUserProfileViewModel = getNewViewModel(BR.userProfileViewModel, UserProfileViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return new DrawerContentHandler();
    }


    //@Override
    //public void onClick(View view) {
        /*if ( view.getId() == R.id.button1 ) {
            JGit.with("FlowMemo")
                    .clone("https://github.com/morirain/FlowMemo.git")
                    .addAll()
                    .commitAll("helloworld")
                    .call();
        }*/
    //}
}
