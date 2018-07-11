package com.morirain.flowmemo.ui.activity;

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
import android.widget.Toast;

import com.morirain.flowmemo.BR;
import com.morirain.flowmemo.R;
import com.morirain.flowmemo.adapter.FolderAdapter;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.ApplicationConfig;
import com.morirain.flowmemo.utils.SingletonFactory;
import com.morirain.flowmemo.viewmodel.FolderViewModel;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import com.morirain.flowmemo.base.BasePagerAdapter;
import com.morirain.flowmemo.base.BaseActivity;
import com.morirain.flowmemo.databinding.ActivityMainBinding;
import com.morirain.flowmemo.databinding.DrawerContentBinding;
import com.morirain.flowmemo.ui.fragment.MemoryFragment;
import com.morirain.flowmemo.ui.fragment.NotesFragment;
import com.morirain.flowmemo.viewmodel.MainViewModel;
import com.morirain.flowmemo.viewmodel.UserProfileViewModel;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private FolderAdapter mFolderAdapter = new FolderAdapter(this, R.layout.item_drawer_folder);

    private NoteLibraryRepository mRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    private DrawerLayout mDrawer;

    private DrawerContentBinding mDrawerBinding;

    private FolderViewModel mFolderViewModel;

    private UserProfileViewModel mUserProfileViewModel;

    // for drawer
    private ActionBarDrawerToggle mDrawerToggle;

    // for back
    private long mKeyTime = 0;


    @Override
    protected void init(Bundle savedInstanceState) {
        mFolderViewModel = getNewViewModel(BR.folderViewModel, FolderViewModel.class);
        mUserProfileViewModel = getNewViewModel(BR.userProfileViewModel, UserProfileViewModel.class);

        initToolbar();
        initFragment();
        initDrawer();

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)//, Permission.Group.MICROPHONE, Permission.Group.CAMERA)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mRepository.refreshData();
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
        drawerRecyclerView.setAdapter(mFolderAdapter);
        mFolderAdapter.setViewModel(mFolderViewModel);

        // DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.addDrawerListener(mDrawerToggle);
    }

    private void initFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NotesFragment());
        fragmentList.add(new MemoryFragment());

        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList);
        getBinding().vpFragment.setAdapter(basePagerAdapter);
        getBinding().vpFragment.setCurrentItem(0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) mDrawerToggle.syncState();
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
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(getBinding().drawerContent.getRoot())) {
            mDrawer.closeDrawers();
        } else {
            long mNowTime = System.currentTimeMillis();//获取第一次按键时间
            if ((mNowTime - mKeyTime) > 1500) {//比较两次按键时间差
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mKeyTime = mNowTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

}
