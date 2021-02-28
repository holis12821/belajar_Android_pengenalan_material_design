package com.example.belajar_android_pengenalan_material_design.tab;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.belajar_android_pengenalan_material_design.fragmentusers.ChatsFragment;
import com.example.belajar_android_pengenalan_material_design.fragmentusers.HomeDiagnosaUsersFragment;

public class MyAdapter  extends FragmentPagerAdapter {
    /*define field class*/
    private final String[] titles = {"Diagnosa User","Chat"};
    /*Define Constructor*/
    public MyAdapter(FragmentManager fm){
        super(fm);
    }

    /*Override method getItem and getCount*/
    /*karena class MyAdapter mengekstends FragmentPagerAdapter maka harus*/
    /*mengimplementasikan methodnya*/
    @NonNull
    @Override
    public Fragment getItem(int position){
        Fragment frag = null;
        if (position == 0){
            frag = new HomeDiagnosaUsersFragment();
        } else if (position == 1) {
            frag = new ChatsFragment();
        }
        Bundle b = new Bundle();
        b.putInt("position", position);
        assert frag != null;
        frag.setArguments(b);
        return frag;
    }
    @Override
    public int getCount(){
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

}
