package com.example.belajar_android_pengenalan_material_design.Form;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.belajar_android_pengenalan_material_design.R;

import java.util.Objects;

public class Guide extends DialogFragment {

    public Guide(){
        /*Create Empty Constructor*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View itemView = inflater.inflate(R.layout.guide_layout, container, false);
        CardView cardView = itemView.findViewById(R.id.cardView);

       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(itemView.getContext(), "Ini adalah Panduan yang digunakan untuk mengisi form diagnosa", Toast.LENGTH_SHORT).show();
           }
       });

        return  itemView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!= null){
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }
}
