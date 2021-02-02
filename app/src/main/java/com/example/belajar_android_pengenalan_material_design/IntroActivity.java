package com.example.belajar_android_pengenalan_material_design;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.belajar_android_pengenalan_material_design.screenItem.ScreenItem;
import com.example.belajar_android_pengenalan_material_design.adapter.IntroViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager screenPager;
    private TabLayout  tabIndicator;
    private Button btnNext;
    private int position = 0;
    private  Button btnGetStarted;
    private Animation btnAnim;
    private TextView tvSkip;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Make  the Activity on Full Screen*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*When this activity is about to be launch we need to check if its opened before or not*/
        if (restorePrefData()){
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }

        setContentView(R.layout.activity_intro);

        /*ini views dan inisialisasi awal*/
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animaton);
        tvSkip = findViewById(R.id.tv_skip);

        /*fill list screen*/
        /*Instansiasi Collection menggunakan ArrayList*/
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Tenangi Diri Anda",  "Tenangkan diri anda karena masalah yang anda hadapi selalu menghasilkan solusi yang bisa anda gunakan untuk mengatasi masalah", R.drawable.icon_stress1));
        mList.add(new ScreenItem("Love your self", "Mungkin anda mengalami masalah atau tekanan hidup, tetapi anda harus ingat bahwa mencintai diri sendiri adalah hal yang terpenting salam hidup anda", R.drawable.icon_stress2));
        mList.add(new ScreenItem("Terdapat sejuta solusi", "Terdapat sejuta solusi di dalam otak anda jika anda mengalami masalah dan menyikapi dengan lapang dada dan tenang", R.drawable.icon_stress3));

        /*Setup ViewPager*/
        screenPager = findViewById(R.id.screen_viewpager);
        IntroViewPagerAdapter introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        /*Setup TabLayout with ViewPager */
        tabIndicator.setupWithViewPager(screenPager);

        /*Next Button Click Listener*/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                /*Kondisi if*/
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size()-1) /*When we rech to the last screen*/{
                    loadLastScreen();
                }
            }
        });

        /*TabLayout add change listener*/
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*Kondisi if*/
                if (tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

           /*Get Started button click listener*/
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Open Start Activity*/
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                //also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();
            }
        });

        /*Skip Button click listener*/
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpnend", false);
    }

    private void savePrefsData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.apply();
    }
    /*Show the GetStarted Button and hide the indicator and the next button*/
    private void loadLastScreen(){
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        /*Setup Animation*/
        btnGetStarted.setAnimation(btnAnim);
    }
}