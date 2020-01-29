package com.example.keo.steptracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity
{
    TextView tv; //app name
    ImageView iv; //app image

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv = (TextView) findViewById(R.id.tvSplashScreen);
        iv = (ImageView) findViewById(R.id.ivLogo);

        Animation anime = AnimationUtils.loadAnimation(this, R.anim.splash_transition);

        tv.startAnimation(anime);
        iv.startAnimation(anime);

        //opens the login activity after the splash screen
        final Intent i = new Intent(this, Login.class);
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(5000); //delays for 5 seconds
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
