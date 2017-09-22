package com.example.android.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by Lukasz on 2017-09-22.
 */

public class SplashActvity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent i = new Intent(SplashActvity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },1100);
    }
}
