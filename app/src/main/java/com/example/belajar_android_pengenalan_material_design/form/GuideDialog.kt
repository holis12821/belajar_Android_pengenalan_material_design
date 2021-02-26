package com.example.belajar_android_pengenalan_material_design.form

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.belajar_android_pengenalan_material_design.MainActivity
import com.example.belajar_android_pengenalan_material_design.R
import kotlinx.android.synthetic.main.guide_layout.*

class GuideDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.guide_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_back.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}