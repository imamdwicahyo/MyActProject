package com.idea.idc.myacts.view;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.idea.idc.myacts.R;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashScreenActivity extends AppCompatActivity {

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // membuat transparan notifikasi
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        //animasi loading avi
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator("BallPulseIndicator");

        //set lama waktu spashscreen 3 detik
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,
                        WalkthroughActivity.class));
                finish();
            }
        },3000);
    }
}
