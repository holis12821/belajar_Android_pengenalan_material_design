package com.example.belajar_android_pengenalan_material_design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.belajar_android_pengenalan_material_design.adapter.ReferenceGridAdapter
import com.example.belajar_android_pengenalan_material_design.model.ReferenceData
import com.example.belajar_android_pengenalan_material_design.onclick.OnReferenceListener
import kotlinx.android.synthetic.main.bar_layout.*
import kotlinx.android.synthetic.main.content_reference.*

class ReferenceActivity : AppCompatActivity() {
    /*create lateinit field*/
    private lateinit var list: ArrayList<ReferenceData>
    private lateinit var listener: OnReferenceListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)

        /*set Toolbar*/
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Daftar Acuan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*Listener toolbar*/
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun setAdapterReference(){
        setOnClickListenerReference()
        val adapter = ReferenceGridAdapter(list, listener)
        referenceList.setHasFixedSize(true)
        referenceList.layoutManager = GridLayoutManager(applicationContext, 2)
        referenceList.itemAnimator = DefaultItemAnimator()
        referenceList.adapter = adapter
    }
    /*create function to handle onClick to start a new Activity*/
    private fun setOnClickListenerReference() {
        listener = object : OnReferenceListener {
            override fun onReferenceClick(itemView: View, position: Int) {
                /*create when to identify listener onClick*/
                when(list[position]){
                  //list[position]
            }
        }
     }
   }
}