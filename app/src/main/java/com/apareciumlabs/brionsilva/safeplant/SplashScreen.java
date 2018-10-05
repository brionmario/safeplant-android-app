package com.apareciumlabs.brionsilva.safeplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private final int DURATION = 5000;
    private Thread mSplashThread;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        progressBar.setProgress(0);
                        wait(DURATION);
                    } catch (InterruptedException e) {
                    } finally {
                        progressBar.setProgress(100);
                        finish();
                        Intent intent = new Intent(getBaseContext(),
                                LoginScreen.class);
                        startActivity(intent);
                    }
                }
            }

        };
        mSplashThread.start();
    }
}
