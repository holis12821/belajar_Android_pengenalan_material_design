package com.example.belajar_android_pengenalan_material_design.fragmentusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.belajar_android_pengenalan_material_design.R
import kotlinx.android.synthetic.main.fragment_reference_book.*

class ReferenceFragmentBook : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reference_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*element view is here*/
        /*add element pdfViewer*/
        pdfBook.fromAsset("jurnal.pdf")
                .load()
    }
}