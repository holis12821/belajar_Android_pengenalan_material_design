package com.example.belajar_android_pengenalan_material_design.fragmentExpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.belajar_android_pengenalan_material_design.R;

public class ChatFragmentExpert extends Fragment {

    public ChatFragmentExpert() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.example.belajar_android_pengenalan_material_design.fragment
        return inflater.inflate(R.layout.fragment_chat_expert, container, false);
    }
}
