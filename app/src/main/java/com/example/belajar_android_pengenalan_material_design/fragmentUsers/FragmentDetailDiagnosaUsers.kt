package com.example.belajar_android_pengenalan_material_design.fragmentUsers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.belajar_android_pengenalan_material_design.R

class FragmentDetailDiagnosaUsers : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_diagnosa_users, container, false)
    }
}