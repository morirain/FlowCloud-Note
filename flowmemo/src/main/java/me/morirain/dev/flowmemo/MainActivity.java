package me.morirain.dev.flowmemo;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.morirain.jgit.utils.JGit;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import me.morirain.dev.flowmemo.databinding.ActivityMainBinding;
import me.morirain.dev.flowmemo.utils.LogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setClickListener(this);

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

    @Override
    public void onClick(View view) {
        if ( view.getId() == R.id.button1 ) {
            JGit.with("FlowMemo")
                    //.clone("https://github.com/morirain/FlowMemo.git")
                    .addAll()
                    .commitAll("helloworld")
                    .call();
            /*JGit.with()
                    .clone("https://github.com/morirain/FlowMemo.git", "FlowMemo")
                    .setLastCallback((isComplete, e) -> {
                        if (isComplete) {
                            Toast.makeText(MainActivity.this, "clone complete", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setAllCommandCallback((isComplete, e) -> {
                        if (isComplete) {
                            Toast.makeText(MainActivity.this, "all command complete", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .call();*/
        }
    }
}
