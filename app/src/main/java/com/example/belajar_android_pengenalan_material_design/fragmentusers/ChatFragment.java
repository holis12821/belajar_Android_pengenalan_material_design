package com.example.belajar_android_pengenalan_material_design.fragmentusers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajar_android_pengenalan_material_design.R;

public class   ChatFragment extends Fragment {

    private View view;
    private RecyclerView chatList;

    /*Constructor Null*/
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.example.belajar_android_pengenalan_material_design.fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        
        chatList = view.findViewById(R.id.recyclerView_chat_list);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
