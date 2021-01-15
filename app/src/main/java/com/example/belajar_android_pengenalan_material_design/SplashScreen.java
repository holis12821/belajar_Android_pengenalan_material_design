package com.example.belajar_android_pengenalan_material_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    /*Define Field in the Class Splash_Screen*/
    private static int SPLASH_SCREEN = 2500;
    private ImageView imageView;
    private TextView judul, subjudul, subsubjudul, subsubsubjudul, subsubsubsubjudul;
    private Animation top, bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.image_view);
        judul = findViewById(R.id.textView_judul);
        subjudul = findViewById(R.id.textView_sub_judul);
        subsubjudul = findViewById(R.id.textView_sub_sub_judul);
        subsubsubjudul = findViewById(R.id.textView_sub_sub_sub_judul);
        subsubsubsubjudul = findViewById(R.id.textView_sub_sub_sub_sub_judul);

        /*Create Animation Splash Screen*/
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);

        /*Memberikan ImageView dan TextView Animasi*/
        imageView.setAnimation(top);
        judul.setAnimation(bottom);
        subjudul.setAnimation(bottom);
        subsubjudul.setAnimation(bottom);
        subsubsubjudul.setAnimation(bottom);
        subsubsubsubjudul.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        },SplashScreen.SPLASH_SCREEN);
    }
}