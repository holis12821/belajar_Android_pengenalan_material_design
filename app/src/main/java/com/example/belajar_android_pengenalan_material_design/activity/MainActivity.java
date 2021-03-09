package com.example.belajar_android_pengenalan_material_design.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.model.UsersData;
import com.example.belajar_android_pengenalan_material_design.tab.MyAdapter;
import com.example.belajar_android_pengenalan_material_design.tab.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        final TextView tv_User = findViewById(R.id.user);
        final CircleImageView circleImageView = findViewById(R.id.profile_image);

        /*instance DatabaseReference to retrieve image and username*/
        /*instance the firebase user to get that current User*/
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        /*create conditoon if, where if condition is null than, next the condition.
         * if not null than running to the instance database reference */
        if (firebaseUser != null) {
            DatabaseReference refUser = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(firebaseUser.getUid());
            /*Display the username*/
            refUser.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        /*retrieve the data username*/
                        UsersData user = snapshot.getValue(UsersData.class);
                        if (user != null) {
                            tv_User.setText("Halo " + user.getUsername());
                            /*create condition if, if the image  has been null than retrieve the image default to a display profil*/
                            if (user.getImageURL().equals("default")) {
                                circleImageView.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get()
                                        .load(user.getImageURL())
                                        .into(circleImageView);
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Error display username", error.getMessage());
                }
            });
        }

        /*Create onClick listener to show in the profile image*/
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        /*Define ViewPager*/
        ViewPager mViewPager = findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        /*Define SlidingTabLayout*/
        SlidingTabLayout mSlidingTabLayout = findViewById(R.id.stl_tabs);
        /*Call Back Method in Activity Main*/
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);

        /*Setup View Pager with SlidingTabLayout*/
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
        } else if (id == R.id.location) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        } else if (id == R.id.referenceInfo) {
            startActivity(new Intent(MainActivity.this, ReferenceActivity.class));
        } else if (id == R.id.logout) {
            /*Define Firebase to logout user*/
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        return true;

    }
}
