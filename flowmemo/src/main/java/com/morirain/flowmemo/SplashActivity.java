package com.morirain.flowmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.ui.activity.MainActivity;
import com.morirain.flowmemo.utils.SingletonFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import me.ele.uetool.UETool;

public class SplashActivity extends AppCompatActivity {

    Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init UETool
        if(BuildConfig.DEBUG) UETool.showUETMenu(100);

        // init data
        SingletonFactory.getInstance(NoteLibraryRepository.class);

        // start
        d = Completable.timer(1, TimeUnit.MICROSECONDS).subscribe(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    @Override
    protected void onDestroy() {
        d.dispose();
        super.onDestroy();
    }
}