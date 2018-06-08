package me.morirain.dev.flowmemo;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import me.morirain.dev.flowmemo.base.BaseActivity;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.bean.Notes;
import me.morirain.dev.flowmemo.databinding.ActivityMainBinding;
import me.morirain.dev.flowmemo.viewmodel.NotesViewModel;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMainBinding mBind;

    private BaseAdapter<Notes> mNotesAdapter;

    private NotesViewModel mNotesViewModel;

    @Override
    protected void init() {
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBind.setClickListener(this);
        mBind.setLifecycleOwner(this);

        Toolbar toolbar = mBind.toolbar;
        setSupportActionBar(toolbar);
        StatusBarUtil.setLightMode(this);

        initRecyclerView();
        initViewModel();
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

    private void initViewModel() {
        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        mNotesViewModel.init(mNotesAdapter);
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBind.notesRecyclerView.setLayoutManager(layoutManager);

        mNotesAdapter = new BaseAdapter<>(this, me.morirain.dev.flowmemo.BR.notes, R.layout.item_notes);
        mBind.notesRecyclerView.setAdapter(mNotesAdapter);

    }

    @Override
    public void onClick(View view) {
        /*if ( view.getId() == R.id.button1 ) {
            JGit.with("FlowMemo")
                    .clone("https://github.com/morirain/FlowMemo.git")
                    .addAll()
                    .commitAll("helloworld")
                    .call();
        }*/
    }
}
