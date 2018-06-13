package me.morirain.dev.flowmemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable d = Completable.timer(3, TimeUnit.MILLISECONDS).subscribe(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        });
    }
}