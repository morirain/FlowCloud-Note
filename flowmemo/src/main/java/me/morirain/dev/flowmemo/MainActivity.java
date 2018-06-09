package me.morirain.dev.flowmemo;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.morirain.dev.flowmemo.adapter.PagerAdapter;
import me.morirain.dev.flowmemo.base.BaseActivity;
import me.morirain.dev.flowmemo.databinding.ActivityMainBinding;
import me.morirain.dev.flowmemo.view.fragment.MemoryFragment;
import me.morirain.dev.flowmemo.view.fragment.NotesFragment;
import me.morirain.dev.flowmemo.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private Drawer mDrawer;

    @Override
    protected void init(Bundle savedInstanceState) {

        initToolbar();
        initDrawer();
        initFragment();

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
        //ActionBar actionBar = getActionBar();
        //if (actionBar != null) //actionBar.setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(getBinding().toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        StatusBarUtil.setLightMode(this);
    }

    private void initDrawer() {

        LiveData<String> name = getViewModel().getProfileName();
        LiveData<String> email = getViewModel().getProfileEmail();

//        Toolbar toolbar = findViewById(R.id.toolbar_drawer);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                //.withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(name.getValue())
                                .withEmail(email.getValue())
                                .withIcon(getResources().getDrawable(R.drawable.ic_face_black_128dp))
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .withSelectionListEnabledForSingleProfile(false)
                .withTextColor(Color.BLACK)
                .build();


        mDrawer = new DrawerBuilder().withActivity(this)
                .withToolbar(getBinding().toolbar)
                .withAccountHeader(headerResult)
                //.withHeader(getBinding().toolbarDrawer)
                //.withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withFullscreen(false)
                .withSliderBackgroundColor(Color.WHITE)
                .addDrawerItems(
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    // do something with the clicked item :D
                    return true;
                })
                .build();
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
        //getSupportFragmentManager().beginTransaction().replace(R.id.vp_fragment, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toolbar_setting) {

        }
        return true;
    }

    @Override
    protected void setViewModel() {
        getBinding().setViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
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
