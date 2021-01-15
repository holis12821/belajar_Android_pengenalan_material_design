package com.example.belajar_android_pengenalan_material_design.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajar_android_pengenalan_material_design.POJO.Gejala;
import com.example.belajar_android_pengenalan_material_design.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class GejalaAdapter extends FirestoreRecyclerAdapter<Gejala, GejalaAdapter.GejalaHolder> {

    public GejalaAdapter(@NonNull FirestoreRecyclerOptions<Gejala> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull GejalaHolder gejalaHolder, int i, @NonNull Gejala gejala) {
        gejalaHolder.textViewKodeGejala.setText("Kode Gejala  :" + gejala.getKode_gejala());
        gejalaHolder.textViewNamaGejala.setText("Nama Gejala  : " + gejala.getNama_gejala());
        gejalaHolder.textViewBobotNilaiCf.setText("Bobot Nilai CF : "+ gejala.getBobot_nilai_cf());
    }

    @NonNull
    @Override
    public GejalaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_users,
                parent, false);
        return new GejalaHolder(v);
    }

    static class GejalaHolder extends RecyclerView.ViewHolder{
        TextView textViewKodeGejala;
        TextView textViewNamaGejala;
        TextView textViewBobotNilaiCf;

        public GejalaHolder(@NonNull View itemView) {
            super(itemView);
            textViewKodeGejala = itemView.findViewById(R.id.tv_kode_gejala);
            textViewNamaGejala = itemView.findViewById(R.id.tv_nama_gejala);
            textViewBobotNilaiCf = itemView.findViewById(R.id.tv_bobot_nilai_cf);
        }
    }
}
