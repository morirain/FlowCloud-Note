package me.morirain.dev.flowcloud.note;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import morirain.dev.jgit.utils.JGit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JGit.prepare()
                        .clone("https://github.com/morirain/FlowCloud-Note.git")
                        .call();
            }
        });
    }
}
