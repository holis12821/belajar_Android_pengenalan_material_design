package com.example.belajar_android_pengenalan_material_design.fragmentusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belajar_android_pengenalan_material_design.R
import com.example.belajar_android_pengenalan_material_design.adapter.ChatUsersAdaper
import com.example.belajar_android_pengenalan_material_design.model.UsersData
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {
    private var adapter : ChatUsersAdaper? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Create FirebaseFirestore*/
        val db : FirebaseFirestore  = FirebaseFirestore.getInstance()
        val searchRef = db.collection("Users")
        /*create method to retrieve the data*/
        retrieveOnData(searchRef)
    }

    private fun retrieveOnData(
            searchRef : CollectionReference
    ) {
        /*Create firebaseRecyclerOption*/
        /*create query for sorting the data order by ascending*/
        val query : Query = searchRef.orderBy("isAdmin",
                Query.Direction.ASCENDING)
        /*create FirebaseRecyclerOption*/
        val options : FirestoreRecyclerOptions<UsersData> =
                FirestoreRecyclerOptions.Builder<UsersData>()
                        .setQuery(query, UsersData::class.java)
                        .build()
        adapter = ChatUsersAdaper(options)
        listSearch.setHasFixedSize(true)
        listSearch.itemAnimator = DefaultItemAnimator()
        listSearch.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
        listSearch.layoutManager = LinearLayoutManager(context)
        listSearch.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

}