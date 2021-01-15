package com.example.belajar_android_pengenalan_material_design.Form;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.belajar_android_pengenalan_material_design.POJO.Gejala;
import com.example.belajar_android_pengenalan_material_design.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class DialogForm extends DialogFragment {

    /*Define Field in DialogForm*/
    private EditText et_kode_gejala, et_nama_gejala, et_bobot_nilai_cf_gejala;
    private String kode_gejala, nama_gejala, pilih, key;
    private double bobot_nilai_cf;
    private ProgressBar progressBar;
    /*Define Database Reference with Firebase*/
   private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
   private final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
   private final HashMap<String,String> hashMap = new HashMap<>();
    /*Create Constructor yang digunakan untuk menberikan nilai field dari class DialogForm*/
    /*Melakukan Overloading Constructor*/

    /*Constructor untuk update data*/
    public DialogForm(String kode_gejala, String nama_gejala, double bobot_nilai_cf, String pilih, String key) {
        this.kode_gejala = kode_gejala;
        this.nama_gejala = nama_gejala;
        this.bobot_nilai_cf = bobot_nilai_cf;
        this.pilih = pilih;
        this.key = key;
    }

    /*Constructor untuk add data*/
    public DialogForm(String pilih){
        this.pilih = pilih;
    }
    /*Create Constructor Empty*/
    public DialogForm(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_form, container, false);

        /*Define atau inisialisasi find view by id pada fragment*/
        et_kode_gejala = v.findViewById(R.id.et_kode_gejala);
        et_nama_gejala = v.findViewById(R.id.et_nama_gejala);
        et_bobot_nilai_cf_gejala = v.findViewById(R.id.et_bobot_nilai_cf_gejala);

        et_kode_gejala.setText(kode_gejala);
        et_nama_gejala.setText(nama_gejala);
        et_bobot_nilai_cf_gejala.setText(String.valueOf(bobot_nilai_cf));

        /*Define progressBar*/
        progressBar = v.findViewById(R.id.progressBar);

        /*Define Button*/
        Button btn_simpan = v.findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(final View v) {
                String _kode_gejala = et_kode_gejala.getText().toString().trim();
                String _nama_gejala = et_nama_gejala.getText().toString().trim();
                double _bobot_nilai_cf = Double.parseDouble(et_bobot_nilai_cf_gejala.getText().toString().trim());

                if (TextUtils.isEmpty(_kode_gejala)){
                   input(et_kode_gejala, "Kode gejala");
                } else  if (TextUtils.isEmpty(_nama_gejala)){
                    input(et_nama_gejala, "Nama gejala");
                } else if(TextUtils.isEmpty(String.valueOf(_bobot_nilai_cf))){
                    input(et_bobot_nilai_cf_gejala, "Bobot nilai CF");
                } else{
                        if(pilih.equals("tambah")){
                            /*Define Progress Bar*/
                            progressBar.setVisibility(View.VISIBLE);
                            /*Mengambil Uid dari firebase pada firebaseUser*/
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();
                            /*Proses penambahan data menggunakan DatabaseReference*/
                            /*dan menggunakan firebaseFirestore*/
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("gejala_stres").child(userId);
                            /*Define HashMap*/
                            hashMap.put("key", userId);
                            hashMap.put("kode_gejala", _kode_gejala);
                            hashMap.put("nama_gejala", _nama_gejala);
                            hashMap.put("bobot_nilai_cf", String.valueOf(_bobot_nilai_cf));

                            databaseReference.push().setValue(new Gejala(hashMap.get("key"), hashMap.get("kode_gejala"), hashMap.get("nama_gejala"), Double.parseDouble(Objects.requireNonNull(hashMap.get("bobot_nilai_cf"))))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(v.getContext(), "Data Gejala Tersimpan", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Data Gejala Gagal Tersimpan" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }  if (pilih.equals("ubah")){
                            progressBar.setVisibility(View.VISIBLE);
                            /*Mengambil Uid dari firebase pada firebaseUser*/
                                assert firebaseUser != null;
                                String userId = firebaseUser.getUid();
                                /*Proses penambahan data menggunakan DatabaseReference*/
                                /*Instansiasi FirebaseFirestore*/

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("gejala_stres").child(userId);
                                /*Define HashMap*/
                                hashMap.put("key", userId);
                                hashMap.put("kode_gejala", _kode_gejala);
                                hashMap.put("nama_gejala", _nama_gejala);
                                hashMap.put("bobot_nilai_cf", String.valueOf(_bobot_nilai_cf));

                                databaseReference.child(key).setValue(new Gejala(hashMap.get("kode_gejala"), hashMap.get("nama_gejala"), Double.parseDouble(Objects.requireNonNull(hashMap.get("bobot_nilai_cf"))))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(), "Data Gejala Berhasil Diubah", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(), "Data Gejala Gagal Diubah ", Toast.LENGTH_SHORT).show();
                                    }
                                });


                        }
                }
            }
        });
        return v;

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

    private void input(EditText txt, String s){
        txt.setError(s + ": tidak boleh kosong");
        txt.requestFocus();
    }

}
