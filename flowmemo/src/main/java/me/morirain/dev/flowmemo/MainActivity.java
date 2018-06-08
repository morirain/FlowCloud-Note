package me.morirain.dev.flowmemo;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jaeger.library.StatusBarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import me.morirain.dev.flowmemo.base.BaseActivity;
import me.morirain.dev.flowmemo.databinding.ActivityMainBinding;
import me.morirain.dev.flowmemo.view.fragment.NotesFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    protected void init(Bundle savedInstanceState) {

        setSupportActionBar(getBinding().toolbar);
        StatusBarUtil.setLightMode(this);

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

    private void initFragment() {
        Fragment fragment = new NotesFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
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
