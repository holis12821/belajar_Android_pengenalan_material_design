package com.example.belajar_android_pengenalan_material_design.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.model.screenItem.ScreenItem;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    private final Context mContext;
    /*Define Interface List*/
    /*Bertipe Parameter class ScreenItem*/
    List<ScreenItem> mListScreen;

    /*Define Constructor*/
    public IntroViewPagerAdapter(Context mContext, List<ScreenItem> mListScreen){
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View layoutScreen = inflater.inflate(R.layout.layout_screen, null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description  = layoutScreen.findViewById(R.id.intro_description);

        /*untuk mengambil nilai field" dan memberikan nilai field" pada class ScreenItem*/
        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int Position, @NonNull Object object){
        container.removeView((View)object);
    }
}
