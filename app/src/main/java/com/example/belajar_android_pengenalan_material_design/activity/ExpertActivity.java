package com.example.belajar_android_pengenalan_material_design.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.form.DialogForm;
import com.example.belajar_android_pengenalan_material_design.model.UsersData;
import com.example.belajar_android_pengenalan_material_design.tab.MyAdapterExpert;
import com.example.belajar_android_pengenalan_material_design.tab.SlidingTabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExpertActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        /*define TextView*/
        final TextView tv_expert = findViewById(R.id.user_expert);
        final CircleImageView circleImageView = findViewById(R.id.profile_image_expert);

        /*circleImageView onClick Listener*/
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpertActivity.this, SettingsActivity.class));
            }
        });

        /*define Firebase User and Database reference to retrieve the data user and image*/
        FirebaseUser mUsers = FirebaseAuth.getInstance().getCurrentUser();
        /*create condition if statement, where the if statement  will handled to a null value*/
        if (mUsers != null){
            DatabaseReference refExpert = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(mUsers.getUid());
            /*retrieve the username expert*/
            refExpert.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    /*Condition if*/
                    if (snapshot.exists()){
                        /*retrieve the data*/
                        /*check data*/
                        /*looping forEach*/
                       try {
                           UsersData usersData = snapshot.getValue(UsersData.class);
                          if (usersData != null){
                              tv_expert.setText("Halo " + usersData.getUsername());
                          }
                       } catch (NullPointerException e){
                           Toast.makeText(ExpertActivity.this,
                                   "Username has been null to produce null pointer exception" + e.getMessage(),
                                   Toast.LENGTH_SHORT).show();
                       }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Failed Retrieve", error.getMessage());
                }
            });

            DatabaseReference dbRefExpert = FirebaseDatabase.getInstance()
                    .getReference("profile_images")
                    .child(mUsers.getUid());
            /*retrieve the image to display in the home activity*/
            dbRefExpert.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        /*snapshot data to the captured*/
                          try {
                               UsersData usersData = snapshot.getValue(UsersData.class);
                               if (usersData != null){
                                   if (usersData.getImageURL().equals("default")){
                                       circleImageView.setImageResource(R.drawable.profile_image);
                                 } else {
                                    Picasso.get()
                                        .load(usersData.getImageURL())
                                        .into(circleImageView);
                                   }
                               }
                          } catch (NullPointerException e){
                              Toast.makeText(ExpertActivity.this,
                                      "Image  has been null to produce null pointer exception" + e.getMessage(),
                                      Toast.LENGTH_SHORT).show();
                          }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Failed Retrieve Images", error.getMessage());
                }
            });
        }

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
                dialogForm.show(getSupportFragmentManager(), DialogForm.class.getSimpleName());
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
        if (id == R.id.changePsw) {
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