package com.example.strayanimals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.strayanimals.ui.login.LoginActivity;

public class Splash_Activity extends AppCompatActivity {
    private static final int sleepTime = 3500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                long costTime = System.currentTimeMillis() - start;
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //进入主页面
                startActivity(new Intent(Splash_Activity.this, LoginActivity.class));
                finish();
            }
        }).start();
    }
}