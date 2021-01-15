package com.example.belajar_android_pengenalan_material_design.fragmentExpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajar_android_pengenalan_material_design.POJO.UserDiagnosa;
import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.adapter.DiagnosaAdapterExpert;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragmentExpert extends Fragment {
    private View itemView;
    /*Membuat FirebaseFireStore*/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference diagnosaRef = db.collection("diagnosa");

    /*Menginisialisasi adapter diagnosa*/
    private DiagnosaAdapterExpert adapter;

    /*Create Constructor*/
    public HomeFragmentExpert() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         itemView = inflater.inflate(R.layout.fragment_home_expert, container, false);
         setUpRecyclerView();
         return itemView;
    }

    private void setUpRecyclerView() {
        /*membuat Query untuk mengurutkan data Diagnosa yang paling tertinggi nilainya*/
        Query query = diagnosaRef.orderBy("persentase", Query.Direction.DESCENDING);

        /*Membuat FirebaseRecyclerOption*/
        FirestoreRecyclerOptions<UserDiagnosa> options = new FirestoreRecyclerOptions.Builder<UserDiagnosa>()
                .setQuery(query, UserDiagnosa.class)
                .build();
        adapter = new DiagnosaAdapterExpert(options);
        RecyclerView recyclerView = itemView.findViewById(R.id.recyclerView_Data_Diagnosa);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}