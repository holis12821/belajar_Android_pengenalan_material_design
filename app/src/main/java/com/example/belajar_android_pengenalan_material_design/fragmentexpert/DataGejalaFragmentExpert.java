package com.example.belajar_android_pengenalan_material_design.fragmentexpert;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.form.DialogForm;
import com.example.belajar_android_pengenalan_material_design.model.Gejala;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class DataGejalaFragmentExpert extends Fragment {

    private RecyclerView gejalaList;

    private DatabaseReference GejalaRef;
    /*Create Constructor*/
    public DataGejalaFragmentExpert() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.example.belajar_android_pengenalan_material_design.fragment

        /*Create Field varibel in the Fragment*/
        View privateGejalaView = inflater.inflate(R.layout.fragment_data_gejala_expert, container, false);

        /*Create Database Reference from Firebase*/
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        GejalaRef = FirebaseDatabase.getInstance().getReference("gejala_stres").child(currentUserID);

        gejalaList = privateGejalaView.findViewById(R.id.recyclerView_Data_Gejala);
        /*Set Layout RecyclerView*/
        gejalaList.setLayoutManager(new LinearLayoutManager(getContext()));

        return privateGejalaView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Gejala> options =
                new FirebaseRecyclerOptions.Builder<Gejala>()
                        .setQuery(GejalaRef, Gejala.class)
                        .build();

        /*Define Firebase RecyclerAdapter*/
        FirebaseRecyclerAdapter<Gejala, GejalaViewHolder> adapter =
                new FirebaseRecyclerAdapter<Gejala, GejalaViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final GejalaViewHolder holder, int position, @NonNull final Gejala model) {
                        final String userID  = getRef(position).getKey();

                        assert userID != null;
                        GejalaRef.child(userID).addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                        /*ternaary operator han been correspondence to elvis operator in kotlin*/
                                        final String retKodeGejala = Objects.requireNonNull(dataSnapshot.child("kode_gejala").getValue()).toString();
                                        final String retNama_Gejala = Objects.requireNonNull(dataSnapshot.child("nama_gejala").getValue()).toString();
                                        final double retBobot_Nilai_CF = Double.parseDouble(Objects.requireNonNull(
                                                dataSnapshot.child("bobot_nilai_cf").getValue()).toString());
                                        /*Menset nilai textview agar bisa ditampilkan*/
                                /*oleh hasil assignment dari variabel yang digunakan untuk menampung nilai dari field" pada
                                firebase yang diambil menggunakan dataSnapshot*/
                                        /*kita ambil nilainya menggunakan objek holder*/
                                        //yang bertipe Inner class static yaitu GejalaViewHolderUsers
                                        //karena tipe static kita tidaak perlu instansiasi
                                        //cukup akses class nya saja kita dapat langsung membuat
                                        //sebuah objek yang dapat mengakses field" varibel pada Inner Class
                                        //jadi compiler java akan otomatis membuatkan objek dari class tersebut

                                        holder.tv_kode_gejala.setText("Kode Gejala : " + retKodeGejala);
                                        holder.tv_nama_gejala.setText("Nama Gejala :" + retNama_Gejala);
                                        holder.tv_bobot_nilai_cf.setText("Bobot Nilai CF :" + retBobot_Nilai_CF);
                                        holder.cardView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                FragmentManager fragmentManager = getChildFragmentManager();
                                                DialogForm dialogForm = new DialogForm(retKodeGejala, retNama_Gejala, retBobot_Nilai_CF, "ubah", userID);
                                                dialogForm.show(fragmentManager, DialogForm.class.getSimpleName());
                                            }
                                        });

                                        holder.delete.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        GejalaRef.child(userID)
                                                                .removeValue()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Toast.makeText(getContext(),
                                                                                "Data Gejala Berhasil Terhapus",
                                                                                Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getContext(), "Data Gejala Gagal Terhapus", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).setMessage("Apakah yakin anda mau menghapus item data gejala : " + retKodeGejala + "?");
                                                builder.show();
                                            }
                                        });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public GejalaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
                        return new GejalaViewHolder(itemView);
                    }
                };

        adapter.notifyDataSetChanged();
        gejalaList.setAdapter(adapter);
        adapter.startListening();
    }

    static class GejalaViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_kode_gejala;
        private final TextView tv_nama_gejala;
        private final TextView tv_bobot_nilai_cf;
        private final CardView cardView;
        private final ImageView delete;
        public GejalaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_kode_gejala = itemView.findViewById(R.id.tv_kode_gejala);
            tv_nama_gejala = itemView.findViewById(R.id.tv_nama_gejala);
            tv_bobot_nilai_cf = itemView.findViewById(R.id.tv_bobot_nilai_cf);
            cardView = itemView.findViewById(R.id.card_view);
            delete = itemView.findViewById(R.id.img_Delete);
        }
    }
}