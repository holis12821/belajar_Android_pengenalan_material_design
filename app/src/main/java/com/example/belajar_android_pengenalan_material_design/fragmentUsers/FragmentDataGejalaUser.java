package com.example.belajar_android_pengenalan_material_design.fragmentUsers;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.belajar_android_pengenalan_material_design.POJO.Gejala;
import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.adapter.GejalaAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FragmentDataGejalaUser extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference gejalaRef = db.collection("gejala_stres");

    private GejalaAdapter adapter;

    public FragmentDataGejalaUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_data_gejala_user, container, false);

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = gejalaRef.orderBy("kode_gejala", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Gejala> options = new FirestoreRecyclerOptions.Builder<Gejala>()
                .setQuery(query, Gejala.class)
                .build();
        adapter = new GejalaAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
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
