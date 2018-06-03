package me.morirain.dev.flowmemo;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.morirain.dev.flowmemo.base.BaseActivity;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.bean.Notes;
import me.morirain.dev.flowmemo.databinding.ActivityMainBinding;
import me.morirain.dev.flowmemo.viewmodel.ActivityMainViewModel;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMainBinding mBind;

    private BaseAdapter<Notes> mNotesAdapter;

    private ActivityMainViewModel mActivityMainViewModel;

    @Override
    protected void init() {
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBind.setClickListener(this);
        initRecyclerView();

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

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBind.notesRecyclerView.setLayoutManager(layoutManager);
        List<Notes> list = new ArrayList<>();
        list.add(new Notes("A", "B", "C"));
        mNotesAdapter = new BaseAdapter<>(list, me.morirain.dev.flowmemo.BR.notes, R.layout.item_notes);
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
