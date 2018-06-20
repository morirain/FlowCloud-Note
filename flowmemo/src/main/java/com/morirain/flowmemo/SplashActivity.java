package com.morirain.flowmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends AppCompatActivity {

    Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d = Completable.timer(1, TimeUnit.MILLISECONDS).subscribe(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        });
    }

    @Override
    protected void onDestroy() {
        d.dispose();
        super.onDestroy();
    }
}