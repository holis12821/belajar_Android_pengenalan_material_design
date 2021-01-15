package com.example.belajar_android_pengenalan_material_design.fragmentUsers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belajar_android_pengenalan_material_design.POJO.UserDiagnosa;
import com.example.belajar_android_pengenalan_material_design.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class HomeDiagnosaUsersFragment extends Fragment {

    private View itemView;
    private TextView tv_tingkat_stres, tv_hasil, tv_solusi;
    private DecimalFormat precision;
    private String TingkatStres;
    private double Persentase;
    private String Solusi;
    private EditText edt_NPM, edt_Nama, edt_Jenis_Kelamin, edt_jurusan, edt_Universitas;

    /*Membuat FireAuth*/
    private FirebaseAuth mAuth;

    /*Rules Stres Ringan 1*/
    /*Define checkbox*/
    private CheckBox cb_iya_G48, cb_iya_G29, cb_iya_G03, cb_iya_G44, cb_iya_G38, cb_iya_G27, cb_iya_G45;
    /*Act Rules Stres Ringan 1*/
    private AutoCompleteTextView act_G48, act_G29, act_G03, act_G44, act_G38, act_G27, act_G45;

    /*Rules Stres Ringan 2*/
    /*Checkbox rules stres ringan 2*/
    private CheckBox cb_G34, cb_G09, cb_G17, cb_G50;
    /*act rules stres ringan 2*/
    private AutoCompleteTextView act_G34, act_G09, act_G17, act_G50;

    /*Rules Stres Sedang 1*/
    private CheckBox cb_G16, cb_G18, cb_G05, cb_G07, cb_G04, cb_G08, cb_G28;
    /*Act Rules Stres Sedang 1*/
    private AutoCompleteTextView act_G16, act_G18, act_G05, act_G07, act_G04, act_G08, act_G28;

    /*Rules Stres Sedang 2*/
    private CheckBox cb_G30, cb_G12, cb_G02, cb_G13, cb_G39, cb_G49, cb_G46, cb_G47;
    /*act rules Stres Sedang 2*/
    private AutoCompleteTextView act_G30, act_G12, act_G02, act_G13, act_G39, act_G49, act_G46, act_G47;

    /*Checkbox rules stres sedang 3*/
    private CheckBox cb_G24, cb_G37, cb_G43, cb_G35, cb_G36, cb_G23;
    /*act rules Stres Sedang 3*/
    private AutoCompleteTextView act_G24, act_G37, act_G43, act_G35, act_G36, act_G23;

    /*Checkbox rules stres berat*/
    private CheckBox cb_G06, cb_G33, cb_G21, cb_G15, cb_G20, cb_G40, cb_G10, cb_G41;
    /*act rules Stres Berat*/
    private AutoCompleteTextView act_G06, act_G33, act_G21, act_G15, act_G20, act_G40, act_G10, act_G41;

    /*Checkbox untuk stres sangat berat*/
    private CheckBox cb_G19, cb_G11, cb_G42, cb_G31, cb_G14, cb_G26, cb_G22, cb_G32, cb_G01, cb_G25;
    /*act rules Stres Sangat Berat*/
    private AutoCompleteTextView act_G19, act_G11, act_G42, act_G31, act_G14, act_G26, act_G22, act_G32, act_G01, act_G25;

    /*Rules Stres ringan 1 untuk nilai CF user*/
    private final String[] Cf_UserG48 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG29 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG03 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG44 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG38 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG27 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG45 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    /*Rules Stres Ringan 2 untuk nilai CF user*/
    private final String[] Cf_UserG34 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG09 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG17 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG50 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};

    /*Rules Stres Sedang 1 untuk nilai CF user*/
    private final String[] Cf_UserG16 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG18 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG05 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG07 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG04 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG08 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG28 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};

    /*Rules Stres Sedang 2 untuk nilai CF user*/
    private final String[] Cf_UserG30 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG12 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG02 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG13 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG39 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG49 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG46 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG47 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};

    /*Rules Stres Sedang 3 untuk nilai CF user*/
    private final String[] Cf_UserG24 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG37 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG43 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG35 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG36 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG23 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};

    /*Rules Stres Berat untuk nilai CF user*/
    private final String[] Cf_UserG06 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG33 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG21 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG15 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG20 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG40 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG10 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG41 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};

    /*Rules Stres Sangat Berat untuk nilai CF user*/
    private final String[] Cf_UserG19 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG11 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG42 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG31 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG14 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG26 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG22 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG32 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG01 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};
    private final String[] Cf_UserG25 = {"", "0", "0.2", "0.4", "0.6", "0.8", "1"};


    /*Constructor*/
    public HomeDiagnosaUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.example.belajar_android_pengenalan_material_design.fragment
        itemView = inflater.inflate(R.layout.fragment_diagnosa_home_user, container, false);

        /*Membuat FirebaseAuth*/
        mAuth = FirebaseAuth.getInstance();

        /*Membuat FindViewById untuk EditText*/
        edt_NPM = itemView.findViewById(R.id.edt_NPM);
        edt_Nama = itemView.findViewById(R.id.edt_Name);
        edt_Jenis_Kelamin = itemView.findViewById(R.id.edt_JK);
        edt_jurusan = itemView.findViewById(R.id.edt_Jurusan);
        edt_Universitas = itemView.findViewById(R.id.edt_Universitas);

        /*Define Checkbox Stres ringan 1 */
        cb_iya_G48 = itemView.findViewById(R.id.cb_G48);
        cb_iya_G29 = itemView.findViewById(R.id.cb_G29);
        cb_iya_G03 = itemView.findViewById(R.id.cb_G03);
        cb_iya_G44 = itemView.findViewById(R.id.cb_G44);
        cb_iya_G38 = itemView.findViewById(R.id.cb_G38);
        cb_iya_G27 = itemView.findViewById(R.id.cb_G27);
        cb_iya_G45 = itemView.findViewById(R.id.cb_G45);
        /*Act rules stres ringan 1*/
        act_G48 = itemView.findViewById(R.id.AutoComplete_tv_G48);
        act_G29 = itemView.findViewById(R.id.AutoComplete_tv_G29);
        act_G03 = itemView.findViewById(R.id.AutoComplete_tv_G03);
        act_G44 = itemView.findViewById(R.id.AutoComplete_tv_G44);
        act_G38 = itemView.findViewById(R.id.AutoComplete_tv_G38);
        act_G27 = itemView.findViewById(R.id.AutoComplete_tv_G27);
        act_G45 = itemView.findViewById(R.id.AutoComplete_tv_G45);

        /*Define Checkbox Rules 2 Stres ringan*/
        cb_G34 = itemView.findViewById(R.id.cb_G34);
        cb_G09 = itemView.findViewById(R.id.cb_G09);
        cb_G17 = itemView.findViewById(R.id.cb_G17);
        cb_G50 = itemView.findViewById(R.id.cb_G50);
        /*act rules Stres Ringan 2*/
        act_G34 = itemView.findViewById(R.id.AutoComplete_tv_G34);
        act_G09 = itemView.findViewById(R.id.AutoComplete_tv_G09);
        act_G17 = itemView.findViewById(R.id.AutoComplete_tv_G17);
        act_G50 = itemView.findViewById(R.id.AutoComplete_tv_G50);

        /*Checkbox Rules Stres Sedang 1*/
        cb_G16 = itemView.findViewById(R.id.cb_G16);
        cb_G18 = itemView.findViewById(R.id.cb_G18);
        cb_G05 = itemView.findViewById(R.id.cb_G05);
        cb_G07 = itemView.findViewById(R.id.cb_G07);
        cb_G04 = itemView.findViewById(R.id.cb_G04);
        cb_G08 = itemView.findViewById(R.id.cb_G08);
        cb_G28 = itemView.findViewById(R.id.cb_G28);
        /*act Rules Stres Sedang 1*/
        act_G16 = itemView.findViewById(R.id.AutoComplete_tv_G16);
        act_G18 = itemView.findViewById(R.id.AutoComplete_tv_G18);
        act_G05 = itemView.findViewById(R.id.AutoComplete_tv_G05);
        act_G07 = itemView.findViewById(R.id.AutoComplete_tv_G07);
        act_G04 = itemView.findViewById(R.id.AutoComplete_tv_G04);
        act_G08 = itemView.findViewById(R.id.AutoComplete_tv_G08);
        act_G28 = itemView.findViewById(R.id.AutoComplete_tv_G28);

        /*Checkbox Rules Stres Sedang 2*/
        cb_G30 = itemView.findViewById(R.id.cb_G30);
        cb_G12 = itemView.findViewById(R.id.cb_G12);
        cb_G02 = itemView.findViewById(R.id.cb_G02);
        cb_G13 = itemView.findViewById(R.id.cb_G13);
        cb_G39 = itemView.findViewById(R.id.cb_G39);
        cb_G49 = itemView.findViewById(R.id.cb_G49);
        cb_G46 = itemView.findViewById(R.id.cb_G46);
        cb_G47 = itemView.findViewById(R.id.cb_G47);

        /*act Rules Stres Sedang 2*/
        act_G30 = itemView.findViewById(R.id.AutoComplete_tv_G30);
        act_G12 = itemView.findViewById(R.id.AutoComplete_tv_G12);
        act_G02 = itemView.findViewById(R.id.AutoComplete_tv_G02);
        act_G13 = itemView.findViewById(R.id.AutoComplete_tv_G13);
        act_G39 = itemView.findViewById(R.id.AutoComplete_tv_G39);
        act_G49 = itemView.findViewById(R.id.AutoComplete_tv_G49);
        act_G46 = itemView.findViewById(R.id.AutoComplete_tv_G46);
        act_G47 = itemView.findViewById(R.id.AutoComplete_tv_G47);

        /*Checkbox Rules Stres Sedang 3*/
        cb_G24 = itemView.findViewById(R.id.cb_G24);
        cb_G37 = itemView.findViewById(R.id.cb_G37);
        cb_G43 = itemView.findViewById(R.id.cb_G43);
        cb_G35 = itemView.findViewById(R.id.cb_G35);
        cb_G36 = itemView.findViewById(R.id.cb_G36);
        cb_G23 = itemView.findViewById(R.id.cb_G23);

        /*act Rules Stres Sedang 3*/
        act_G24 = itemView.findViewById(R.id.AutoComplete_tv_G24);
        act_G37 = itemView.findViewById(R.id.AutoComplete_tv_G37);
        act_G43 = itemView.findViewById(R.id.AutoComplete_tv_G43);
        act_G35 = itemView.findViewById(R.id.AutoComplete_tv_G35);
        act_G36 = itemView.findViewById(R.id.AutoComplete_tv_G36);
        act_G23 = itemView.findViewById(R.id.AutoComplete_tv_G23);

        /*Checkbox Rules Stres Berat*/
        cb_G06 = itemView.findViewById(R.id.cb_G06);
        cb_G33 = itemView.findViewById(R.id.cb_G33);
        cb_G21 = itemView.findViewById(R.id.cb_G21);
        cb_G15 = itemView.findViewById(R.id.cb_G15);
        cb_G20 = itemView.findViewById(R.id.cb_G20);
        cb_G40 = itemView.findViewById(R.id.cb_G40);
        cb_G10 = itemView.findViewById(R.id.cb_G10);
        cb_G41 = itemView.findViewById(R.id.cb_G41);

        /*act Rules Stres Berat*/
        act_G06 = itemView.findViewById(R.id.AutoComplete_tv_G06);
        act_G33 = itemView.findViewById(R.id.AutoComplete_tv_G33);
        act_G21 = itemView.findViewById(R.id.AutoComplete_tv_G21);
        act_G15 = itemView.findViewById(R.id.AutoComplete_tv_G15);
        act_G20 = itemView.findViewById(R.id.AutoComplete_tv_G20);
        act_G40 = itemView.findViewById(R.id.AutoComplete_tv_G40);
        act_G10 = itemView.findViewById(R.id.AutoComplete_tv_G10);
        act_G41 = itemView.findViewById(R.id.AutoComplete_tv_G41);

        /*Checkbox Rules Stres Sangat Berat*/
        cb_G19 = itemView.findViewById(R.id.cb_G19);
        cb_G11 = itemView.findViewById(R.id.cb_G11);
        cb_G42 = itemView.findViewById(R.id.cb_G42);
        cb_G31 = itemView.findViewById(R.id.cb_G31);
        cb_G14 = itemView.findViewById(R.id.cb_G14);
        cb_G26 = itemView.findViewById(R.id.cb_G26);
        cb_G22 = itemView.findViewById(R.id.cb_G22);
        cb_G32 = itemView.findViewById(R.id.cb_G32);
        cb_G01 = itemView.findViewById(R.id.cb_G01);
        cb_G25 = itemView.findViewById(R.id.cb_G25);

        /*act Rules Stres Sangat Berat*/
        act_G19 = itemView.findViewById(R.id.AutoComplete_tv_G19);
        act_G11 = itemView.findViewById(R.id.AutoComplete_tv_G11);
        act_G42 = itemView.findViewById(R.id.AutoComplete_tv_G42);
        act_G31 = itemView.findViewById(R.id.AutoComplete_tv_G31);
        act_G14 = itemView.findViewById(R.id.AutoComplete_tv_G14);
        act_G26 = itemView.findViewById(R.id.AutoComplete_tv_G26);
        act_G22 = itemView.findViewById(R.id.AutoComplete_tv_G22);
        act_G32 = itemView.findViewById(R.id.AutoComplete_tv_G32);
        act_G01 = itemView.findViewById(R.id.AutoComplete_tv_G01);
        act_G25 = itemView.findViewById(R.id.AutoComplete_tv_G25);

        /*Define TextView*/
        tv_tingkat_stres = itemView.findViewById(R.id.tv_tingkat_stres);
        tv_hasil = itemView.findViewById(R.id.tv_hasil);
        tv_solusi = itemView.findViewById(R.id.tv_solusi);

        Button btn_proses = itemView.findViewById(R.id.btn_simpan_diagnosa);

        /*Define Array Adapter agar ACT aktif*/
        /*agar user bisa memilih bobot cf user*/
        /*Rules Stres Ringan 1*/
        /*G48*/
        final ArrayAdapter<String> adapter_G48 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG48);
        act_G48.setThreshold(1);
        act_G48.setAdapter(adapter_G48);

        /*G29*/
        final ArrayAdapter<String> adapter_G29 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG29);
        act_G29.setThreshold(1);
        act_G29.setAdapter(adapter_G29);

        /*G03*/
        final ArrayAdapter<String> adapter_G03 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG03);
        act_G03.setThreshold(1);
        act_G03.setAdapter(adapter_G03);

        /*G44*/
        final ArrayAdapter<String> adapter_G44 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG44);
        act_G44.setThreshold(1);
        act_G44.setAdapter(adapter_G44);

        /*G38*/
        final ArrayAdapter<String> adapter_G38 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG38);
        act_G38.setThreshold(1);
        act_G38.setAdapter(adapter_G38);

        /*G27*/
        final ArrayAdapter<String> adapter_G27 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG27);
        act_G27.setThreshold(1);
        act_G27.setAdapter(adapter_G27);

        /*G45*/
        final ArrayAdapter<String> adapter_G45 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG45);
        act_G45.setThreshold(1);
        act_G45.setAdapter(adapter_G45);

        /*membuat Adapter act Rules stres ringan 2*/
        /*G34*/
        final ArrayAdapter<String> adapter_G34 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG34);
        act_G34.setThreshold(1);
        act_G34.setAdapter(adapter_G34);

        /*G09*/
        final ArrayAdapter<String> adapter_G09 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG09);
        act_G09.setThreshold(1);
        act_G09.setAdapter(adapter_G09);

        /*G17*/
        final ArrayAdapter<String> adapter_G17 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG17);
        act_G17.setThreshold(1);
        act_G17.setAdapter(adapter_G17);

        /*G50*/
        final ArrayAdapter<String> adapter_G50 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG50);
        act_G50.setThreshold(1);
        act_G50.setAdapter(adapter_G50);

        /*Membuat Adapter act Rules Stres Sedang 1*/
        /*G16*/
        final ArrayAdapter<String> adapter_G16 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG16);
        act_G16.setThreshold(1);
        act_G16.setAdapter(adapter_G16);

        /*G18*/
        final ArrayAdapter<String> adapter_G18 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG18);
        act_G18.setThreshold(1);
        act_G18.setAdapter(adapter_G18);

        /*G05*/
        final ArrayAdapter<String> adapter_G05 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG05);
        act_G05.setThreshold(1);
        act_G05.setAdapter(adapter_G05);

        /*G07*/
        final ArrayAdapter<String> adapter_G07 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG07);
        act_G07.setThreshold(1);
        act_G07.setAdapter(adapter_G07);

        /*G04*/
        final ArrayAdapter<String> adapter_G04 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG04);
        act_G04.setThreshold(1);
        act_G04.setAdapter(adapter_G04);

        /*G08*/
        final ArrayAdapter<String> adapter_G08 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG08);
        act_G08.setThreshold(1);
        act_G08.setAdapter(adapter_G08);

        /*G28*/
        final ArrayAdapter<String> adapter_G28 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG28);
        act_G28.setThreshold(1);
        act_G28.setAdapter(adapter_G28);

        /*membuat Adapter act Rules stres sedang 2*/
        /*G30*/
        final ArrayAdapter<String> adapter_G30 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG30);
        act_G30.setThreshold(1);
        act_G30.setAdapter(adapter_G30);

        /*G12*/
        final ArrayAdapter<String> adapter_G12 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG12);
        act_G12.setThreshold(1);
        act_G12.setAdapter(adapter_G12);

        /*G02*/
        final ArrayAdapter<String> adapter_G02 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG02);
        act_G02.setThreshold(1);
        act_G02.setAdapter(adapter_G02);

        /*G13*/
        final ArrayAdapter<String> adapter_G13 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG13);
        act_G13.setThreshold(1);
        act_G13.setAdapter(adapter_G13);

        /*G39*/
        final ArrayAdapter<String> adapter_G39 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG39);
        act_G39.setThreshold(1);
        act_G39.setAdapter(adapter_G39);

        /*G49*/
        final ArrayAdapter<String> adapter_G49 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG49);
        act_G49.setThreshold(1);
        act_G49.setAdapter(adapter_G49);

        /*G46*/
        final ArrayAdapter<String> adapter_G46 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG46);
        act_G46.setThreshold(1);
        act_G46.setAdapter(adapter_G46);

        /*G47*/
        final ArrayAdapter<String> adapter_G47 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG47);
        act_G47.setThreshold(1);
        act_G47.setAdapter(adapter_G47);

        /*membuat Adapter act Rules stres sedang 3*/
        /*G24*/
        final ArrayAdapter<String> adapter_G24 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG24);
        act_G24.setThreshold(1);
        act_G24.setAdapter(adapter_G24);

        /*G37*/
        final ArrayAdapter<String> adapter_G37 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG37);
        act_G37.setThreshold(1);
        act_G37.setAdapter(adapter_G37);

        /*G43*/
        final ArrayAdapter<String> adapter_G43 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG43);
        act_G43.setThreshold(1);
        act_G43.setAdapter(adapter_G43);

        /*G35*/
        final ArrayAdapter<String> adapter_G35 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG35);
        act_G35.setThreshold(1);
        act_G35.setAdapter(adapter_G35);

        /*G36*/
        final ArrayAdapter<String> adapter_G36 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG36);
        act_G36.setThreshold(1);
        act_G36.setAdapter(adapter_G36);

        /*G23*/
        final ArrayAdapter<String> adapter_G23 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG23);
        act_G23.setThreshold(1);
        act_G23.setAdapter(adapter_G23);

        /*membuat Adapter act Rules stres berat*/
        /*G06*/
        final ArrayAdapter<String> adapter_G06 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG06);
        act_G06.setThreshold(1);
        act_G06.setAdapter(adapter_G06);

        /*G33*/
        final ArrayAdapter<String> adapter_G33 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG33);
        act_G33.setThreshold(1);
        act_G33.setAdapter(adapter_G33);

        /*G21*/
        final ArrayAdapter<String> adapter_G21 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG21);
        act_G21.setThreshold(1);
        act_G21.setAdapter(adapter_G21);

        /*G15*/
        final ArrayAdapter<String> adapter_G15 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG15);
        act_G15.setThreshold(1);
        act_G15.setAdapter(adapter_G15);

        /*G20*/
        final ArrayAdapter<String> adapter_G20 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG20);
        act_G20.setThreshold(1);
        act_G20.setAdapter(adapter_G20);

        /*G40*/
        final ArrayAdapter<String> adapter_G40 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG40);
        act_G40.setThreshold(1);
        act_G40.setAdapter(adapter_G40);

        /*G10*/
        final ArrayAdapter<String> adapter_G10 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG10);
        act_G10.setThreshold(1);
        act_G10.setAdapter(adapter_G10);

        /*G41*/
        final ArrayAdapter<String> adapter_G41 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG41);
        act_G41.setThreshold(1);
        act_G41.setAdapter(adapter_G41);

        /*membuat Adapter act Rules stres sangat berat*/
        /*G19*/
        final ArrayAdapter<String> adapter_G19 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG19);
        act_G19.setThreshold(1);
        act_G19.setAdapter(adapter_G19);

        /*G11*/
        final ArrayAdapter<String> adapter_G11 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG11);
        act_G11.setThreshold(1);
        act_G11.setAdapter(adapter_G11);

        /*G42*/
        final  ArrayAdapter<String> adapter_G42 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG42);
        act_G42.setThreshold(1);
        act_G42.setAdapter(adapter_G42);

        /*G31*/
        final ArrayAdapter<String> adapter_G31 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG31);
        act_G31.setThreshold(1);
        act_G31.setAdapter(adapter_G31);

        /*G14*/
        final ArrayAdapter<String> adapter_G14 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG14);
        act_G14.setThreshold(1);
        act_G14.setAdapter(adapter_G14);

        /*G26*/
        final ArrayAdapter<String> adapter_G26 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG26);
        act_G26.setThreshold(1);
        act_G26.setAdapter(adapter_G26);

        /*G22*/
        final ArrayAdapter<String> adapter_G22 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG22);
        act_G22.setThreshold(1);
        act_G22.setAdapter(adapter_G22);

        /*G32*/
        final ArrayAdapter<String> adapter_G32 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG32);
        act_G32.setThreshold(1);
        act_G32.setAdapter(adapter_G32);

        /*G01*/
        final ArrayAdapter<String> adapter_G01 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG01);
        act_G01.setThreshold(1);
        act_G01.setAdapter(adapter_G01);

        /*G25*/
        final ArrayAdapter<String> adapter_G25 = new ArrayAdapter<>(itemView.getContext(), android.R.layout.select_dialog_item, Cf_UserG25);
        act_G25.setThreshold(1);
        act_G25.setAdapter(adapter_G25);

        /*Listener untuk Stres Ringan 1*/
        /*Define Listener untuk G48*/
        act_G48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Define AlertDialog untuk menampilkan title item*/
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih Nilai Gejala 1").setAdapter(adapter_G48, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G48.setText(Cf_UserG48[which]);
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        /*Define Listener untuk G29*/
        act_G29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih Nilai Gejala 2").setAdapter(adapter_G29, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G29.setText(Cf_UserG29[which]);
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G03*/
        act_G03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih Nilai Gejala 3").setAdapter(adapter_G03, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G03.setText(Cf_UserG03[which]);
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G44*/
        act_G44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih Nilai Gejala 4").setAdapter(adapter_G44, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G44.setText(Cf_UserG44[which]);
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G38*/
        act_G38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih Nilai Gejala 5").setAdapter(adapter_G38, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G38.setText(Cf_UserG38[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G27*/
        act_G27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih Nilai Gejala 6").setAdapter(adapter_G27, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G27.setText(Cf_UserG27[which]);

                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G45*/
        act_G45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 7").setAdapter(adapter_G45, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G45.setText(Cf_UserG45[which]);
                    }
                }).create().show();
            }
        });

        /*Define Act Rules dan listener untuk Stres Ringan 2*/
        /*Define Listener untuk G34*/
        act_G34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 8").setAdapter(adapter_G34, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G34.setText(Cf_UserG34[which]);
                    }
                }).create().show();
            }
        });
        /*Define Listener untuk G09*/
        act_G09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 9").setAdapter(adapter_G09, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G09.setText(Cf_UserG09[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G17*/
        act_G17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 10").setAdapter(adapter_G17, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G17.setText(Cf_UserG17[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G50*/
        act_G50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 11").setAdapter(adapter_G50, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G50.setText(Cf_UserG50[which]);
                    }
                }).create().show();
            }
        });

        /*Define act Rules Stres Sedang  1*/
        /*Define Listener untuk G16*/
        act_G16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 12").setAdapter(adapter_G16, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G16.setText(Cf_UserG16[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G18*/
        act_G18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 13").setAdapter(adapter_G18, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G18.setText(Cf_UserG18[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G05*/
        act_G05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 14").setAdapter(adapter_G05, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G05.setText(Cf_UserG05[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G07*/
        act_G07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 15").setAdapter(adapter_G07, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G07.setText(Cf_UserG07[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G04*/
        act_G04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 16").setAdapter(adapter_G04, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G04.setText(Cf_UserG04[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G08*/
        act_G08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 17").setAdapter(adapter_G08, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G08.setText(Cf_UserG08[which]);
                    }
                }).create().show();
            }
        });

        /*Define Listener untuk G28*/
        act_G28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 18").setAdapter(adapter_G28, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G28.setText(Cf_UserG28[which]);
                    }
                }).create().show();
            }
        });

        /*Membuat Listener untuk Stres Sedang 2*/
        /*Mendefinisikan Listener untuk act gejala G30*/
        act_G30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 19").setAdapter(adapter_G30, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G30.setText(Cf_UserG30[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala act G12*/
        act_G12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 20").setAdapter(adapter_G12, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G12.setText(Cf_UserG12[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala act G02*/
        act_G02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih  nilai Gejala 21").setAdapter(adapter_G02, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G02.setText(Cf_UserG02[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala act G13*/
        act_G13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 22").setAdapter(adapter_G13, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G13.setText(Cf_UserG13[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala act G39*/
        act_G39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 23").setAdapter(adapter_G39, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G39.setText(Cf_UserG39[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G49*/
        act_G49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 24").setAdapter(adapter_G49, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G49.setText(Cf_UserG49[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G46*/
        act_G46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 25").setAdapter(adapter_G46, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G46.setText(Cf_UserG46[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G47*/
        act_G47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 26").setAdapter(adapter_G47, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G47.setText(Cf_UserG47[which]);
                    }
                }).create().show();
            }
        });

        /*Listener untuk Stres Sedang 3*/
        /*Rules Gejala G24*/
        act_G24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 27").setAdapter(adapter_G24, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G24.setText(Cf_UserG24[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G37*/
        act_G37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 28").setAdapter(adapter_G37, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G37.setText(Cf_UserG37[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G43*/
        act_G43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 29").setAdapter(adapter_G43, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G43.setText(Cf_UserG43[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G35*/
        act_G35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 30").setAdapter(adapter_G35, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G35.setText(Cf_UserG35[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G36*/
        act_G36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 31").setAdapter(adapter_G36, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G36.setText(Cf_UserG36[which]);
                    }
                }).create().show();

            }
        });

        /*Rules Gejala G23*/
        act_G23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 32").setAdapter(adapter_G23, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G23.setText(Cf_UserG23[which]);
                    }
                }).create().show();
            }
        });

        /*Listener untuk Stres Berat*/
        /*Rules Gejala G06*/
        act_G06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 33").setAdapter(adapter_G06, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G06.setText(Cf_UserG06[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G33*/
        act_G33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 34").setAdapter(adapter_G33, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G33.setText(Cf_UserG33[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G21*/
        act_G21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 35").setAdapter(adapter_G21, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G21.setText(Cf_UserG21[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G15*/
        act_G15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 36").setAdapter(adapter_G15, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G15.setText(Cf_UserG15[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G20*/
        act_G20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 37").setAdapter(adapter_G20, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G20.setText(Cf_UserG20[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G40*/
        act_G40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 38").setAdapter(adapter_G40, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G40.setText(Cf_UserG40[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G10*/
        act_G10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 39").setAdapter(adapter_G10, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G10.setText(Cf_UserG10[which]);
                    }
                }).create().show();
            }
        });

        /*Rules Gejala G41*/
        act_G41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 40").setAdapter(adapter_G41, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G41.setText(Cf_UserG41[which]);
                    }
                }).create().show();
            }
        });

        /*Listener untuk Stres Sangat Berat*/
        /*G19*/
        act_G19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 41").setAdapter(adapter_G19, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G19.setText(Cf_UserG19[which]);
                    }
                }).create().show();
            }
        });

        /*G11*/
        act_G11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 42").setAdapter(adapter_G11, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G11.setText(Cf_UserG11[which]);
                    }
                }).create().show();
            }
        });

        /*G42*/
        act_G42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 43").setAdapter(adapter_G42, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G42.setText(Cf_UserG42[which]);
                    }
                }).create().show();
            }
        });

        /*G31*/
        act_G31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 44").setAdapter(adapter_G31, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G31.setText(Cf_UserG31[which]);
                    }
                }).create().show();
            }
        });

        /*G14*/
        act_G14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 45").setAdapter(adapter_G14, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G14.setText(Cf_UserG14[which]);
                    }
                }).create().show();
            }
        });

        /*G26*/
        act_G26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 46").setAdapter(adapter_G26, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G26.setText(Cf_UserG26[which]);
                    }
                }).create().show();
            }
        });

        /*G22*/
        act_G22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 47").setAdapter(adapter_G22, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G22.setText(Cf_UserG22[which]);
                    }
                }).create().show();
            }
        });

        /*G32*/
        act_G32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 48").setAdapter(adapter_G32, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G32.setText(Cf_UserG32[which]);
                    }
                }).create().show();
            }
        });

        /*G01*/
        act_G01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 49").setAdapter(adapter_G01, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G01.setText(Cf_UserG01[which]);
                    }
                }).create().show();
            }
        });

        /*G25*/
        act_G25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Pilih nilai Gejala 50").setAdapter(adapter_G25, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act_G25.setText(Cf_UserG25[which]);
                    }
                }).create().show();
            }
        });



        /*Menonaktifkan checkbox dan menonaktifkan AutoCompleteTextView ketika rules checkbox G44 diceklis maka akan menonaktifkan rules Checkbox gejala G34 dst*/
        cb_iya_G44.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    cb_G34.setEnabled(false);
                    cb_G09.setEnabled(false);
                    cb_G17.setEnabled(false);
                    cb_G50.setEnabled(false);

                    act_G34.setEnabled(false);
                    act_G09.setEnabled(false);
                    act_G17.setEnabled(false);
                    act_G50.setEnabled(false);
                } else{
                    cb_G34.setEnabled(true);
                    cb_G09.setEnabled(true);
                    cb_G17.setEnabled(true);
                    cb_G50.setEnabled(true);

                    act_G34.setEnabled(true);
                    act_G09.setEnabled(true);
                    act_G17.setEnabled(true);
                    act_G50.setEnabled(true);
                }
            }
        });

        /*Menonaktifkan cb dan act G48 sampai G45*/
        cb_G34.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){

                    cb_iya_G44.setEnabled(false);
                    cb_iya_G38.setEnabled(false);
                    cb_iya_G27.setEnabled(false);
                    cb_iya_G45.setEnabled(false);

                    act_G44.setEnabled(false);
                    act_G38.setEnabled(false);
                    act_G27.setEnabled(false);
                    act_G45.setEnabled(false);
                } else{
                    cb_iya_G44.setEnabled(true);
                    cb_iya_G38.setEnabled(true);
                    cb_iya_G27.setEnabled(true);
                    cb_iya_G45.setEnabled(true);

                    act_G44.setEnabled(true);
                    act_G38.setEnabled(true);
                    act_G27.setEnabled(true);
                    act_G45.setEnabled(true);
                }
            }
        });

        /*Menonaktifkan cb dan act G30-G47, G24-G23*/
        cb_G18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (compoundButton.isChecked()){
                    cb_G30.setEnabled(false);
                    cb_G12.setEnabled(false);
                    cb_G02.setEnabled(false);
                    cb_G13.setEnabled(false);
                    cb_G39.setEnabled(false);
                    cb_G49.setEnabled(false);
                    cb_G46.setEnabled(false);
                    cb_G47.setEnabled(false);

                    act_G30.setEnabled(false);
                    act_G12.setEnabled(false);
                    act_G02.setEnabled(false);
                    act_G13.setEnabled(false);
                    act_G39.setEnabled(false);
                    act_G49.setEnabled(false);
                    act_G46.setEnabled(false);
                    act_G47.setEnabled(false);

                    cb_G24.setEnabled(false);
                    cb_G37.setEnabled(false);
                    cb_G43.setEnabled(false);
                    cb_G35.setEnabled(false);
                    cb_G36.setEnabled(false);
                    cb_G23.setEnabled(false);

                    act_G24.setEnabled(false);
                    act_G37.setEnabled(false);
                    act_G43.setEnabled(false);
                    act_G35.setEnabled(false);
                    act_G36.setEnabled(false);
                    act_G23.setEnabled(false);

                } else {
                    cb_G30.setEnabled(true);
                    cb_G12.setEnabled(true);
                    cb_G02.setEnabled(true);
                    cb_G13.setEnabled(true);
                    cb_G39.setEnabled(true);
                    cb_G49.setEnabled(true);
                    cb_G46.setEnabled(true);
                    cb_G47.setEnabled(true);

                    act_G30.setEnabled(true);
                    act_G12.setEnabled(true);
                    act_G02.setEnabled(true);
                    act_G13.setEnabled(true);
                    act_G39.setEnabled(true);
                    act_G49.setEnabled(true);
                    act_G46.setEnabled(true);
                    act_G47.setEnabled(true);

                    cb_G24.setEnabled(true);
                    cb_G37.setEnabled(true);
                    cb_G43.setEnabled(true);
                    cb_G35.setEnabled(true);
                    cb_G36.setEnabled(true);
                    cb_G23.setEnabled(true);

                    act_G24.setEnabled(true);
                    act_G37.setEnabled(true);
                    act_G43.setEnabled(true);
                    act_G35.setEnabled(true);
                    act_G36.setEnabled(true);
                    act_G23.setEnabled(true);
                }
            }
        });

        /*Menonaktifkan checkbox dan act G18 - G28, G24- G23 jika user klik G30*/
        cb_G30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (compoundButton.isChecked()){
                    cb_G18.setEnabled(false);
                    cb_G05.setEnabled(false);
                    cb_G07.setEnabled(false);
                    cb_G04.setEnabled(false);
                    cb_G08.setEnabled(false);
                    cb_G28.setEnabled(false);

                    act_G18.setEnabled(false);
                    act_G05.setEnabled(false);
                    act_G07.setEnabled(false);
                    act_G04.setEnabled(false);
                    act_G08.setEnabled(false);
                    act_G28.setEnabled(false);

                    cb_G24.setEnabled(false);
                    cb_G37.setEnabled(false);
                    cb_G43.setEnabled(false);
                    cb_G35.setEnabled(false);
                    cb_G36.setEnabled(false);
                    cb_G23.setEnabled(false);

                    act_G24.setEnabled(false);
                    act_G37.setEnabled(false);
                    act_G43.setEnabled(false);
                    act_G35.setEnabled(false);
                    act_G36.setEnabled(false);
                    act_G23.setEnabled(false);
                } else{
                    cb_G18.setEnabled(true);
                    cb_G05.setEnabled(true);
                    cb_G07.setEnabled(true);
                    cb_G04.setEnabled(true);
                    cb_G08.setEnabled(true);
                    cb_G28.setEnabled(true);

                    act_G18.setEnabled(true);
                    act_G05.setEnabled(true);
                    act_G07.setEnabled(true);
                    act_G04.setEnabled(true);
                    act_G08.setEnabled(true);
                    act_G28.setEnabled(true);

                    cb_G24.setEnabled(true);
                    cb_G37.setEnabled(true);
                    cb_G43.setEnabled(true);
                    cb_G35.setEnabled(true);
                    cb_G36.setEnabled(true);
                    cb_G23.setEnabled(true);

                    act_G24.setEnabled(true);
                    act_G37.setEnabled(true);
                    act_G43.setEnabled(true);
                    act_G35.setEnabled(true);
                    act_G36.setEnabled(true);
                    act_G23.setEnabled(true);
                }
            }
        });

        /*Menonaktifkan checkbox dan act G18 - G28, G30- G47 jika user klik G24*/
        cb_G24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (compoundButton.isChecked()){
                    cb_G18.setEnabled(false);
                    cb_G05.setEnabled(false);
                    cb_G07.setEnabled(false);
                    cb_G04.setEnabled(false);
                    cb_G08.setEnabled(false);
                    cb_G28.setEnabled(false);

                    act_G18.setEnabled(false);
                    act_G05.setEnabled(false);
                    act_G07.setEnabled(false);
                    act_G04.setEnabled(false);
                    act_G08.setEnabled(false);
                    act_G28.setEnabled(false);

                    cb_G30.setEnabled(false);
                    cb_G12.setEnabled(false);
                    cb_G02.setEnabled(false);
                    cb_G13.setEnabled(false);
                    cb_G39.setEnabled(false);
                    cb_G49.setEnabled(false);
                    cb_G46.setEnabled(false);
                    cb_G47.setEnabled(false);

                    act_G30.setEnabled(false);
                    act_G12.setEnabled(false);
                    act_G02.setEnabled(false);
                    act_G13.setEnabled(false);
                    act_G39.setEnabled(false);
                    act_G49.setEnabled(false);
                    act_G46.setEnabled(false);
                    act_G47.setEnabled(false);
                } else{
                    cb_G18.setEnabled(true);
                    cb_G05.setEnabled(true);
                    cb_G07.setEnabled(true);
                    cb_G04.setEnabled(true);
                    cb_G08.setEnabled(true);
                    cb_G28.setEnabled(true);

                    act_G18.setEnabled(true);
                    act_G05.setEnabled(true);
                    act_G07.setEnabled(true);
                    act_G04.setEnabled(true);
                    act_G08.setEnabled(true);
                    act_G28.setEnabled(true);

                    cb_G30.setEnabled(true);
                    cb_G12.setEnabled(true);
                    cb_G02.setEnabled(true);
                    cb_G13.setEnabled(true);
                    cb_G39.setEnabled(true);
                    cb_G49.setEnabled(true);
                    cb_G46.setEnabled(true);
                    cb_G47.setEnabled(true);

                    act_G30.setEnabled(true);
                    act_G12.setEnabled(true);
                    act_G02.setEnabled(true);
                    act_G13.setEnabled(true);
                    act_G39.setEnabled(true);
                    act_G49.setEnabled(true);
                    act_G46.setEnabled(true);
                    act_G47.setEnabled(true);
                }

            }
        });


        btn_proses.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                /*Define Algorima Certainty Factor Rules Stres Ringan 1*/
                setUpCertaintyFactorRulesStresRingan1();
                /*Define Algorima Certainty Factor Rules Stres Ringan 2*/
                setUpCertaintyFactorRulesStresRingan2();
                /*Define Algorima Certainty Factor Rules Stres Sedang 1*/
                setUpCertaintyFactorRulesStresSedang1();
                /*Define Algorima Certainty Factor Rules Stres Sedang 2*/
                setUpCertaintyFactorRulesStresSedang2();
                /*Define Algorima Certainty Factor Rules Stres Sedang 3*/
                setUpCertaintyFactorRulesStresSedang3();
                /*Define Algorima Certainty Factor Rules Stres Berat*/
                setUpCertaintyFactorRulesStresBerat();
                /*Define Algorima Certainty Factor Rules Stres Sangat Berat*/
                setUpCertaintyFactorRulesStresSangatBerat();
                /*Define Database method setUpDatabase Reference*/
                setUpDatabaseReference();
            }
        });

        return itemView;
    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresRingan1() {
        /*Rules Stres Ringan 1*/
        /*Disini perhitungan Certainty Factor*/

        if (cb_iya_G48.isChecked() && cb_iya_G29.isChecked() && cb_iya_G03.isChecked() && cb_iya_G44.isChecked() && cb_iya_G38.isChecked() && cb_iya_G27.isChecked() && cb_iya_G45.isChecked()) {

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G48 = Double.parseDouble(act_G48.getText().toString());
            double _G29 = Double.parseDouble(act_G29.getText().toString());
            double _G03 = Double.parseDouble(act_G03.getText().toString());
            double _G44 = Double.parseDouble(act_G44.getText().toString());
            double _G38 = Double.parseDouble(act_G38.getText().toString());
            double _G27 = Double.parseDouble(act_G27.getText().toString());
            double _G45 = Double.parseDouble(act_G45.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G48 = 0.4;
            final double CfPakar_G29 = 1.0;
            final double CfPakar_G03 = 0.8;
            final double CfPakar_G44 = 1.0;
            final double CfPakar_G38 = 0.6;
            final double CfPakar_G27 = 0.8;
            final double CfPakar_G45 = 1.0;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double resultCount_G48_CF1 = CfPakar_G48 * _G48;
            double resultCount_G29_CF2 = CfPakar_G29 * _G29;
            double resultCount_G03_CF3 = CfPakar_G03 * _G03;
            double resultCount_G44_CF4 = CfPakar_G44 * _G44;
            double resultCount_G38_CF5 = CfPakar_G38 * _G38;
            double resultCount_G27_CF6 = CfPakar_G27 * _G27;
            double resultCount_G45_CF7 = CfPakar_G45 * _G45;

            /*Definisikan DecimalFormat untuk mengambil 2 angka dibelakang koma*/
            /*Define Format simbol*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung cF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G48_CF1 + resultCount_G29_CF2 * (1 - resultCount_G48_CF1);
            double CfCombine_Old_CF3 = CfCombine_CF1_CF2 + resultCount_G03_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombine_Old_CF3 + resultCount_G44_CF4 * (1 - CfCombine_Old_CF3);
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G38_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G27_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G45_CF7 * (1 - CfCombineCfOld_CF6);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF7 * 100;
            /*Kesimpulan*/
            String tingkatStres = "Anda menderita Stres Ringan";

            TingkatStres = tingkatStres;

            tv_tingkat_stres.setText(tingkatStres);

            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Tetaplah semangat dan yakin anda bisa mengatasi masalah dan stres anda dan selalu berusaha untuk mengatasi setiap masalah yang anda lalui.";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresRingan2() {
        /*Rules Stres Ringan 2*/
        /*Disini perhitungan Certainty Factor*/

        if (cb_iya_G48.isChecked() && cb_iya_G29.isChecked() && cb_iya_G03.isChecked() &&
                cb_G34.isChecked() && cb_G09.isChecked() && cb_G17.isChecked() && cb_G50.isChecked()) {

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G48 = Double.parseDouble(act_G48.getText().toString());
            double _G29 = Double.parseDouble(act_G29.getText().toString());
            double _G03 = Double.parseDouble(act_G03.getText().toString());
            double _G34 = Double.parseDouble(act_G34.getText().toString());
            double _G09 = Double.parseDouble(act_G09.getText().toString());
            double _G17 = Double.parseDouble(act_G17.getText().toString());
            double _G50 = Double.parseDouble(act_G50.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G48 = 0.4;
            final double CfPakar_G29 = 1.0;
            final double CfPakar_G03 = 0.8;
            final double CfPakar_G34 = 0.6;
            final double CfPakar_G09 = 1.0;
            final double CfPakar_G17 = 0.4;
            final double CfPakar_G50 = 0.6;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double  resultCount_G48_CF1 =  CfPakar_G48 * _G48;
            double  resultCount_G29_CF2 = CfPakar_G29 * _G29;
            double  resultCount_G03_CF3 = CfPakar_G03 * _G03;
            double resultCount_G34_CF4 = CfPakar_G34 * _G34;
            double resultCount_G09_CF5 = CfPakar_G09 * _G09;
            double resultCount_G17_CF6 = CfPakar_G17 * _G17;
            double resultCount_G50_CF7 = CfPakar_G50 * _G50;

            /*Mensetting Format Decimal untuk mengambil 2 angka decimal dibelakang koma*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung CF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G48_CF1 + resultCount_G29_CF2 * (1 - resultCount_G48_CF1);
            double CfCombineCfOld_CF3 = CfCombine_CF1_CF2 + resultCount_G03_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombineCfOld_CF3 + resultCount_G34_CF4 * (1 - CfCombineCfOld_CF3);
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G09_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G17_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G50_CF7 * (1 - CfCombineCfOld_CF6);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF7 * 100;

            /*Kesimpulan*/
            String tingkatStres = "Anda menderita Stres Ringan";
            TingkatStres = tingkatStres;
            tv_tingkat_stres.setText(tingkatStres);

            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Tetaplah semangat dan yakin anda bisa mengatasi masalah dan stres anda dan selalu berusaha untuk mengatasi setiap masalah yang anda lalui serta semangatlah dalam situasi apapun.";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);

        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresSedang1() {
        /*Rules Stres Sedang 1*/
        /*Disini perhitungan Certainty Factor*/
        if(cb_G16.isChecked() &&  cb_G18.isChecked() && cb_G05.isChecked() && cb_G07.isChecked()
                && cb_G04.isChecked() &&
                cb_G08.isChecked() && cb_G28.isChecked()){

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G16 = Double.parseDouble(act_G16.getText().toString());
            double _G18 = Double.parseDouble(act_G18.getText().toString());
            double _G05 = Double.parseDouble(act_G05.getText().toString());
            double _G07 = Double.parseDouble(act_G07.getText().toString());
            double _G04 = Double.parseDouble(act_G04.getText().toString());
            double _G08 = Double.parseDouble(act_G08.getText().toString());
            double _G28 = Double.parseDouble(act_G28.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G16 =  1.0;
            final double CfPakar_G18 = 1.0;
            final double CfPakar_G05 = 0.4;
            final double CfPakar_G07 = 0.6;
            final double CfPakar_G04 = 0.6;
            final double CfPakar_G08 = 0.4;
            final double CfPakar_G28 = 0.8;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double  resultCount_G16_CF1 = CfPakar_G16 * _G16;
            double  resultCount_G18_CF2 = CfPakar_G18 * _G18;
            double  resultCount_G05_CF3 = CfPakar_G05 * _G05;
            double  resultCount_G07_CF4 = CfPakar_G07 * _G07;
            double  resultCount_G04_CF5 = CfPakar_G04 * _G04;
            double  resultCount_G08_CF6 = CfPakar_G08 * _G08;
            double resultCount_G28_CF7 = CfPakar_G28 * _G28;

            /*Mensetting Format Decimal untuk mengambil 2 angka decimal dibelakang koma*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung CF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G16_CF1 + resultCount_G18_CF2 * (1 - resultCount_G16_CF1);
            double CfCombineCfOld_CF3= CfCombine_CF1_CF2 + resultCount_G05_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombineCfOld_CF3 + resultCount_G07_CF4 * ( 1- CfCombineCfOld_CF3 );
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G04_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G08_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G28_CF7 * (1 - CfCombineCfOld_CF6);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF7 * 100;

            /*Kesimpulan*/
            String tingkatStres = "Anda Menderita Stres Sedang";
            TingkatStres = tingkatStres;
            tv_tingkat_stres.setText(tingkatStres);

            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Tenangkanlah diri anda sejenak dan berusahalah untuk memanajemen waktu dengan benar sehingga ketika anda dikejar deadline tugas akhir anda dapat dengan cepat menyelesaikannya.";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresSedang2(){
        /*Rules Stres Sedang 2*/
        /*Disini perhitungan Certainty Factor*/
        if (cb_G16.isChecked() && cb_G30.isChecked() && cb_G12.isChecked()
                &&  cb_G02.isChecked() && cb_G13.isChecked() && cb_G39.isChecked() && cb_G49.isChecked()
                && cb_G46.isChecked() && cb_G47.isChecked()){

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G16 = Double.parseDouble(act_G16.getText().toString());
            double _G30 = Double.parseDouble(act_G30.getText().toString());
            double _G12 = Double.parseDouble(act_G12.getText().toString());
            double _G02 = Double.parseDouble(act_G02.getText().toString());
            double _G13 = Double.parseDouble(act_G13.getText().toString());
            double _G39 = Double.parseDouble(act_G39.getText().toString());
            double _G49 = Double.parseDouble(act_G49.getText().toString());
            double _G46 = Double.parseDouble(act_G46.getText().toString());
            double _G47 = Double.parseDouble(act_G47.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G16 =  1.0;
            final double CfPakar_G30 = 0.8;
            final double CfPakar_G12 = 0.8;
            final double CfPakar_G02 = 0.4;
            final double CfPakar_G13 = 1.0;
            final double CfPakar_G39 = 0.8;
            final double CfPakar_G49 = 0.8;
            final double CfPakar_G46 = 0.4;
            final double CfPakar_G47 = 0.6;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double resultCount_G16_CF1 = CfPakar_G16 * _G16;
            double resultCount_G30_CF2 = CfPakar_G30 * _G30;
            double resultCount_G12_CF3 = CfPakar_G12 * _G12;
            double resultCount_G02_CF4 = CfPakar_G02 * _G02;
            double resultCount_G13_CF5 = CfPakar_G13 * _G13;
            double resultCount_G39_CF6 = CfPakar_G39 * _G39;
            double resultCount_G49_CF7 = CfPakar_G49 * _G49;
            double resultCount_G46_CF8 = CfPakar_G46 * _G46;
            double resultCount_G47_CF9 = CfPakar_G47 * _G47;

            /*Mensetting Format Decimal untuk mengambil 2 angka decimal dibelakang koma*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung CF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G16_CF1 + resultCount_G30_CF2 * (1 - resultCount_G16_CF1);
            double CfCombineCfOld_CF3 = CfCombine_CF1_CF2 + resultCount_G12_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombineCfOld_CF3 + resultCount_G02_CF4 * (1 - CfCombineCfOld_CF3);
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G13_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G39_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G49_CF7 * (1 - CfCombineCfOld_CF6);
            double CfCombineCfOld_CF8 = CfCombineCfOld_CF7 + resultCount_G46_CF8 * (1 - CfCombineCfOld_CF7);
            double CfCombineCfOld_CF9 = CfCombineCfOld_CF8 + resultCount_G47_CF9 * ( 1 - CfCombineCfOld_CF8);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF9 * 100;

            /*Kesimpulan*/
            String tingkatStres = "Anda menderita Stres Sedang";
            TingkatStres = tingkatStres;
            tv_tingkat_stres.setText(tingkatStres);

            /*Persentase*/
            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Tenangkanlah diri anda sejenak dan berusahalah untuk memanajemen waktu dengan benar dan buatlah daftar agenda yang akan anda lakukan, buatlah sedetail mungkin agar anda dapat memanajemen waktu dengan seksama sehingga stres yang sedang anda alami dapat anda atasi.";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);
        }


    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresSedang3(){
        /*Rules Stres Sedang 3*/
        /*Disini perhitungan Certainty Factor*/
        if (cb_G16.isChecked() && cb_G24.isChecked() && cb_G37.isChecked() && cb_G43.isChecked()
                && cb_G35.isChecked() && cb_G36.isChecked() && cb_G23.isChecked()){

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G16 = Double.parseDouble(act_G16.getText().toString());
            double _G24 = Double.parseDouble(act_G24.getText().toString());
            double _G37 = Double.parseDouble(act_G37.getText().toString());
            double _G43 = Double.parseDouble(act_G43.getText().toString());
            double _G35 = Double.parseDouble(act_G35.getText().toString());
            double _G36 = Double.parseDouble(act_G36.getText().toString());
            double _G23 = Double.parseDouble(act_G23.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G16 = 1.0;
            final double CfPakar_G24 = 0.8;
            final double CfPakar_G37 = 0.4;
            final double CfPakar_G43 = 0.4;
            final double CfPakar_G35 = 0.8;
            final double CfPakar_G36 = 0.8;
            final double CfPakar_G23 = 0.8;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double resultCount_G16_CF1 = CfPakar_G16 * _G16;
            double resultCount_G24_CF2 = CfPakar_G24 * _G24;
            double resultCount_G37_CF3 = CfPakar_G37 * _G37;
            double resultCount_G43_CF4 = CfPakar_G43 * _G43;
            double resultCount_G35_CF5 = CfPakar_G35 * _G35;
            double resultCount_G36_CF6 = CfPakar_G36 * _G36;
            double resultCount_G23_CF7 = CfPakar_G23 * _G23;

            /*Mensetting Format Decimal untuk mengambil 2 angka decimal dibelakang koma*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung CF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G16_CF1 + resultCount_G24_CF2 * (1 - resultCount_G16_CF1);
            double CfCombineCfOld_CF3 = CfCombine_CF1_CF2 + resultCount_G37_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombineCfOld_CF3 + resultCount_G43_CF4 * (1 - CfCombineCfOld_CF3);
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G35_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G36_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G23_CF7 * (1 - CfCombineCfOld_CF6);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF7 * 100;

            /*Kesimpulan*/
            String tingkatStres = "Anda  menderita Stres Sedang";
            TingkatStres = tingkatStres;
            tv_tingkat_stres.setText(tingkatStres);

            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Tenangkanlah diri anda sejenak dan berusahalah untuk memanajemen waktu dengan benar dan buatlah daftar agenda yang akan anda lakukan, buatlah sedetail mungkin agar anda dapat memanajemen waktu dengan seksama sehingga stres yang sedang anda alami dapat anda atasi.";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresBerat(){
        /*Rules Stres Berat*/
        /*Disini perhitungan Certainty Factor*/
        if(cb_G06.isChecked() && cb_G33.isChecked() && cb_G21.isChecked() && cb_G15.isChecked() && cb_G20.isChecked()
                && cb_G40.isChecked() && cb_G10.isChecked() && cb_G41.isChecked()){

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G06 = Double.parseDouble(act_G06.getText().toString());
            double _G33 = Double.parseDouble(act_G33.getText().toString());
            double _G21 = Double.parseDouble(act_G21.getText().toString());
            double _G15 = Double.parseDouble(act_G15.getText().toString());
            double _G20 = Double.parseDouble(act_G20.getText().toString());
            double _G40 = Double.parseDouble(act_G40.getText().toString());
            double _G10 = Double.parseDouble(act_G10.getText().toString());
            double _G41 = Double.parseDouble(act_G41.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G06 = 0.4;
            final double CfPakar_G33 = 0.4;
            final double CfPakar_G21 = 0.4;
            final double CfPakar_G15 = 0.4;
            final double CfPakar_G20 = 0.6;
            final double CfPakar_G40 = 0.6;
            final double CfPakar_G10 = 0.6;
            final double CfPakar_G41 = 0.4;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double resultCount_G06_CF1 = CfPakar_G06 * _G06;
            double resultCount_G33_CF2 = CfPakar_G33 * _G33;
            double resultCount_G21_CF3 = CfPakar_G21 * _G21;
            double resultCount_G15_CF4 = CfPakar_G15 * _G15;
            double resultCount_G20_CF5 = CfPakar_G20 * _G20;
            double resultCount_G40_CF6 = CfPakar_G40 * _G40;
            double resultCount_G10_CF7 = CfPakar_G10 * _G10;
            double resultCount_G41_CF8 = CfPakar_G41 * _G41;

            /*Mensetting Format Decimal untuk mengambil 2 angka decimal dibelakang koma*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung CF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G06_CF1 + resultCount_G33_CF2 * (1 - resultCount_G06_CF1);
            double CfCombineCfOld_CF3 = CfCombine_CF1_CF2 + resultCount_G21_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombineCfOld_CF3 + resultCount_G15_CF4 * (1 - CfCombineCfOld_CF3);
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G20_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G40_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G10_CF7 * (1 - CfCombineCfOld_CF6);
            double CfCombineCfOld_CF8 = CfCombineCfOld_CF7 + resultCount_G41_CF8 * (1 - CfCombineCfOld_CF7);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF8 * 100;

            /*Kesimpulan*/
            String tingkatStres = "Anda  menderita Stres Berat";
            TingkatStres = tingkatStres;
            tv_tingkat_stres.setText(tingkatStres);

            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Anda harus tahu bahwa banyak orang-orang disekitar anda yang mensupport anda, kegagalan bukanlah sesuatu yang sangat menyeramkan, bangkit dan jangan pernah meyerah karna kegagalan adalah awal dari kesuksesan !!!.";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setUpCertaintyFactorRulesStresSangatBerat(){
        /*Rules Stres Sangat Berat*/
        /*Disini perhitungan Certainty Factor*/
        if (cb_G19.isChecked() && cb_G11.isChecked() && cb_G42.isChecked() && cb_G31.isChecked() &&
                cb_G14.isChecked() && cb_G26.isChecked() && cb_G22.isChecked() && cb_G32.isChecked() && cb_G01.isChecked() && cb_G25.isChecked()){

            /*Mengambil nilai Autocomplete TextView yaitu CF user*/
            double _G19 = Double.parseDouble(act_G19.getText().toString());
            double _G11 = Double.parseDouble(act_G11.getText().toString());
            double _G42 = Double.parseDouble(act_G42.getText().toString());
            double _G31 = Double.parseDouble(act_G31.getText().toString());
            double _G14 = Double.parseDouble(act_G14.getText().toString());
            double _G26 = Double.parseDouble(act_G26.getText().toString());
            double _G22 =  Double.parseDouble(act_G22.getText().toString());
            double _G32 = Double.parseDouble(act_G32.getText().toString());
            double _G01 = Double.parseDouble(act_G01.getText().toString());
            double _G25 = Double.parseDouble(act_G25.getText().toString());

            /*Membuat CFPakar*/
            final double CfPakar_G19 = 0.8;
            final double CfPakar_G11 = 0.4;
            final double CfPakar_G42 = 0.6;
            final double CfPakar_G31 = 1.0;
            final double CfPakar_G14 = 0.4;
            final double CfPakar_G26 = 0.6;
            final double CfPakar_G22 = 1.0;
            final double CfPakar_G32 = 0.6;
            final double CfPakar_G01 = 0.4;
            final double CfPakar_G25 = 0.4;

            /*Mengalikan CF pakar dengan Cf user*/
            /*Cf pakar * cf User*/
            double resultCount_G19_CF1 = CfPakar_G19 * _G19;
            double resultCount_G11_CF2 = CfPakar_G11 * _G11;
            double resultCount_G42_CF3 = CfPakar_G42 * _G42;
            double resultCount_G31_CF4 = CfPakar_G31 * _G31;
            double resultCount_G14_CF5 = CfPakar_G14 * _G14;
            double resultCount_G26_CF6 = CfPakar_G26 * _G26;
            double resultCount_G22_CF7 = CfPakar_G22 * _G22;
            double resultCount_G32_CF8 = CfPakar_G32 * _G32;
            double resultCount_G01_CF9 = CfPakar_G01 * _G01;
            double resultCount_G25_CF10 = CfPakar_G25 * _G25;

            /*Mensetting Format Decimal untuk mengambil 2 angka decimal dibelakang koma*/
            precision = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            precision.setDecimalFormatSymbols(dfs);

            /*Menghitung CF Combine*/
            double CfCombine_CF1_CF2 = resultCount_G19_CF1 + resultCount_G11_CF2 * (1 - resultCount_G19_CF1);
            double CfCombineCfOld_CF3 = CfCombine_CF1_CF2 + resultCount_G42_CF3 * (1 - CfCombine_CF1_CF2);
            double CfCombineCfOld_CF4 = CfCombineCfOld_CF3 + resultCount_G31_CF4 * (1 - CfCombineCfOld_CF3);
            double CfCombineCfOld_CF5 = CfCombineCfOld_CF4 + resultCount_G14_CF5 * (1 - CfCombineCfOld_CF4);
            double CfCombineCfOld_CF6 = CfCombineCfOld_CF5 + resultCount_G26_CF6 * (1 - CfCombineCfOld_CF5);
            double CfCombineCfOld_CF7 = CfCombineCfOld_CF6 + resultCount_G22_CF7 * (1 - CfCombineCfOld_CF6);
            double CfCombineCfOld_CF8 = CfCombineCfOld_CF7 + resultCount_G32_CF8 * ( 1- CfCombineCfOld_CF7);
            double CfCombineCfOld_CF9 = CfCombineCfOld_CF8 + resultCount_G01_CF9 * ( 1 - CfCombineCfOld_CF8);
            double CfCombineCfOld_CF10 = CfCombineCfOld_CF9 + resultCount_G25_CF10 * (1 - CfCombineCfOld_CF9);

            /*Menghitung persentase dari hasil perhitungan metode CF*/
            double persentase = CfCombineCfOld_CF10 * 100;

            /*Kesimpulan*/
            String tingkatStres = "Anda  menderita Stres Sangat Berat";
            TingkatStres = tingkatStres;
            tv_tingkat_stres.setText(tingkatStres);

            Persentase = persentase;
            tv_hasil.setText("dengan persentase tingkat kepercayaan yaitu = " + precision.format(persentase) + "%");

            String solusi = "Anda harus menghubungi Psikolog untuk berkonsultasi tentang Stres Sangat Berat yang anda alami, Silahkah anda ke menu chat dan mintalah request chat kepada Psikolog yang tersedia, Terimakasih ):";
            Solusi = solusi;
            tv_solusi.setText("Solusi : " + solusi);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpDatabaseReference() {
        try {
            /*Define Edit Text*/
            final String _EditNPM = edt_NPM.getText().toString().trim();
            final String _EditNama = edt_Nama.getText().toString().trim();
            final String _Edit_Jenis_Kelamin = edt_Jenis_Kelamin.getText().toString().trim();
            final String _EditJurusan = edt_jurusan.getText().toString().trim();
            final String _EditUniversitas = edt_Universitas.getText().toString().trim();
            /*Buat kondisi if untuk mengecek validasi jika kosong maka error*/
            if (TextUtils.isEmpty(_EditNPM)){
                input(edt_NPM, "NPM");
            } else if (TextUtils.isEmpty(_EditNama)){
                input(edt_Nama, "Nama");
            } else if(TextUtils.isEmpty(_Edit_Jenis_Kelamin)){
                input(edt_Jenis_Kelamin, "Jenis Kelamin");
            } else if(TextUtils.isEmpty(_EditJurusan)){
                input(edt_jurusan, "Jurusan");
            } else if (TextUtils.isEmpty(_EditUniversitas)){
                input(edt_Universitas, "Universitas");
            } else {
                /*Membuat FirebaseFireStore*/
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                /*Membuat FirebaseUser*/
                FirebaseUser mUser = mAuth.getCurrentUser();
                assert mUser != null;
                String userId = mUser.getUid();
                /*Menambahkan DocumentReference untuk memasukan data pada FirebaseFirestore*/
                DocumentReference df = firebaseFirestore.collection("diagnosa").document(mUser.getUid());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("diagnosa").child(userId);

                //Membuat HashMap
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", userId);
                hashMap.put("npm", _EditNPM);
                hashMap.put("nama", _EditNama);
                hashMap.put("jenis_kelamin", _Edit_Jenis_Kelamin);
                hashMap.put("jurusan", _EditJurusan);
                hashMap.put("universitas", _EditUniversitas);
                hashMap.put("tingkat_stres", TingkatStres);
                hashMap.put("solusi", Solusi);
                hashMap.put("persentase", precision.format(Persentase));
                /*menambahkan data hashMap pada FirebaseFireStore*/
                df.set(hashMap);
                /*Menambahkan data pada DatabaseReference*/
                databaseReference.push().setValue(new UserDiagnosa(hashMap.get("key"), hashMap.get("npm"), hashMap.get("nama"),
                        hashMap.get("jenis_kelamin"), hashMap.get("jurusan"), hashMap.get("universitas"), hashMap.get("tingkat_stres"),
                        hashMap.get("solusi"), hashMap.get("persentase")))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(itemView.getContext(), "Diagnosa dan Biodata User Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), FragmentDetailDiagnosaUsers.class);
                                ArrayList<UserDiagnosa> userDiagnosa = new ArrayList<>();
                                intent.putParcelableArrayListExtra(FragmentDetailDiagnosaUsers.EXTRA_PARCEL_DIAGNOSA, userDiagnosa);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(itemView.getContext(), "DiagnosaUser Gagal Disimpan", Toast.LENGTH_SHORT).show();
                        Log.e("Gagal Disimpan", Objects.requireNonNull(e.getMessage()));
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(itemView.getContext(), "Failure Error, Isilah Biodata Anda Terlebih Dahulu Pada Menu Tombol Floating Action Button !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void input(EditText txt, String  s) {
        txt.setError(s + ": tidak boleh kosong");
        txt.requestFocus();
    }
}
