package com.example.belajar_android_pengenalan_material_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.belajar_android_pengenalan_material_design.form.DialogForm;
import com.example.belajar_android_pengenalan_material_design.tab.MyAdapterExpert;
import com.example.belajar_android_pengenalan_material_design.tab.SlidingTabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ExpertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home Expert");
        setSupportActionBar(toolbar);

        /*Define ViewPager*/
        ViewPager mViewPager = findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new MyAdapterExpert(getSupportFragmentManager()));
        /*Define SlidingTabLayout*/
        /*Define Field in the class ExpertActivity*/
        SlidingTabLayout mSlidingTabLayout = findViewById(R.id.stl_tabs);
        /*Call Back Method in Activity Main*/
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);

        /*Setup View Pager with SlidingTabLayout*/
        mSlidingTabLayout.setViewPager(mViewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm dialogForm = new DialogForm("tambah");
                dialogForm.show(getSupportFragmentManager(), "form");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==  R.id.settings){
            startActivity(new Intent(ExpertActivity.this, SettingsActivity.class));
        }
        else if (id == R.id.changePsw) {
            startActivity(new Intent(ExpertActivity.this, ChangePasswordActivity.class));
        }
        /*else if(id == R.id.about_developer){

        }*/
        else if(id == R.id.logout){
            /*Define Firebase to logout user*/
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ExpertActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }
}