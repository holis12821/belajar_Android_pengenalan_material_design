package com.example.belajar_android_pengenalan_material_design.fragmentusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.belajar_android_pengenalan_material_design.R

class FragmentDetailDiagnosaUsers : Fragment() {

    companion object {
        const val EXTRA_PARCEL_DIAGNOSA = "Extra_Parcel_Diagnosa"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_diagnosa_users, container, false)
    }
}