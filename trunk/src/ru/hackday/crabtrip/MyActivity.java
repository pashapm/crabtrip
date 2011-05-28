package ru.hackday.crabtrip;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    findViewById(R.id.gameView).post(new Runnable() {
                        public void run() {
                            findViewById(R.id.gameView).invalidate();
                        }
                    });
                }
            }
        }).start();
    }
}
