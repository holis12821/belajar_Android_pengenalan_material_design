package com.example.belajar_android_pengenalan_material_design.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajar_android_pengenalan_material_design.model.UserDiagnosa;
import com.example.belajar_android_pengenalan_material_design.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DiagnosaAdapterExpert extends FirestoreRecyclerAdapter<UserDiagnosa, DiagnosaAdapterExpert.DiagnosaViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DiagnosaAdapterExpert(@NonNull FirestoreRecyclerOptions<UserDiagnosa> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull DiagnosaViewHolder diagnosaViewHolder, int i, @NonNull UserDiagnosa userDiagnosa) {
            diagnosaViewHolder.tv_npm.setText("NPM : " + userDiagnosa.getNpm());
            diagnosaViewHolder.tv_nama.setText("Nama : " + userDiagnosa.getNama());
            diagnosaViewHolder.tv_jk.setText("Jenis Kelamin : " + userDiagnosa.getJenis_kelamin());
            diagnosaViewHolder.tv_jurusan.setText("Jurusan : " + userDiagnosa.getJurusan());
            diagnosaViewHolder.tv_universitas.setText("Institusi : " + userDiagnosa.getUniversitas());
            diagnosaViewHolder.tv_tingkat_stres.setText("Tingkat Stres : " + userDiagnosa.getTingkat_stres());
            diagnosaViewHolder.tv_solusi.setText("Solusi : " + userDiagnosa.getSolusi());
            diagnosaViewHolder.tv_persentase.setText("Persentase : " + userDiagnosa.getPersentase() + "%");

            diagnosaViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Anda Mengklik Data Hasil Diagnosa Tingkat Stres Mahasiswa Tingkat Akhir", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @NonNull
    @Override
    public DiagnosaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_diagnosa, viewGroup, false);
        return new DiagnosaViewHolder(itemView);
    }

    /*membuat InnerClass*/
    static class DiagnosaViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_npm;
        private final TextView tv_nama;
        private final TextView tv_jk;
        private final TextView tv_jurusan;
        private final TextView tv_universitas;
        private final TextView tv_tingkat_stres;
        private final TextView tv_solusi;
        private final TextView tv_persentase;
        private final CardView cardView;
        public DiagnosaViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_npm = itemView.findViewById(R.id.tv_NPM);
             tv_nama = itemView.findViewById(R.id.tv_Nama);
             tv_jk = itemView.findViewById(R.id.tv_jenis_kelamin);
             tv_jurusan = itemView.findViewById(R.id.tv_Jurusan);
             tv_universitas = itemView.findViewById(R.id.tv_Universitas);
             tv_tingkat_stres = itemView.findViewById(R.id.tv_tingkat_stres);
             tv_solusi = itemView.findViewById(R.id.tv_Solusi);
             tv_persentase = itemView.findViewById(R.id.tv_Persentase);
             cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
