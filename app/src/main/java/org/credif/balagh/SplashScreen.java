package org.credif.balagh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 7000;
    ProgressBar progressBar;
    Animation animFadeIn,animFadeON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ImageView logo = (ImageView) findViewById(R.id.imgLogo);
        TextView Text=findViewById(R.id.text);

        animFadeIn= AnimationUtils.loadAnimation(this, R.anim.enter_from_right);
        logo.setAnimation(animFadeIn);

        animFadeON= AnimationUtils.loadAnimation(this, R.anim.enter_from_left);
        Text.setAnimation(animFadeON);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                progressBar.setProgress(SPLASH_TIME_OUT);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}