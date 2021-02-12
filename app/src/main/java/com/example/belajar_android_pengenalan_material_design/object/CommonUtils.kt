package com.example.belajar_android_pengenalan_material_design.`object`

import android.app.Dialog
import android.content.Context
import com.example.belajar_android_pengenalan_material_design.R

object CommonUtils {

    fun showLoadingDialog(
            context: Context
    ): Dialog {
        val progressDialog = Dialog(context)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

}