package com.example.belajar_android_pengenalan_material_design.loadingProgres;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.belajar_android_pengenalan_material_design.R;

public class LoadingProgress extends DialogFragment {
    private LottieAnimationView animationView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_loading, container, false);
        getDialog().setCancelable(false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        animationView = v.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();
        animationView.setProgress(0.5f);
        animationView.cancelAnimation();
        return v;
    }
}
