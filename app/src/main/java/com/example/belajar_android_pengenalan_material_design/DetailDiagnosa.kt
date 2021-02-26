package com.example.belajar_android_pengenalan_material_design

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailDiagnosa : AppCompatActivity() {
    /*Initialize Field in the activity*/
    private lateinit var tvnama: TextView
    private lateinit var tvtingkatstres: TextView
    private lateinit var tvpersentase: TextView
    private lateinit var tvsolusi: TextView

    companion object {
        /*Object Companion or objek pendamping*/
        const val NAMA: String = "nama"
        const val TINGKAT_STRES: String = "tingkat_stres"
        const val PERSENTASE: String = "persentase"
        const val SOLUSI: String = "solusi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_diagnosa)
        /*call back function*/
        dataDiagnosa()
        /*keep on state activity*/
        if (savedInstanceState != null){
            val name = savedInstanceState.getString(NAMA)
            val tingkatStres = savedInstanceState.getString(TINGKAT_STRES)
            val persentase = savedInstanceState.getString(PERSENTASE)
            val solusi = savedInstanceState.getString(SOLUSI)
            /*print the text to result diagnosis*/
            tvnama.text = name
            tvtingkatstres.text = tingkatStres
            tvpersentase.text = persentase
            tvsolusi.text = solusi
        }
    }

  @SuppressLint("SetTextI18n")
  private fun dataDiagnosa(){
        /*define fineViewById with View*/
        this.tvnama = findViewById(R.id.tv_nama)
        this.tvtingkatstres = findViewById(R.id.tv_tingkat_stres)
        this.tvpersentase = findViewById(R.id.tv_persentase)
        this.tvsolusi = findViewById(R.id.tv_solusi)

        /*get data to from intent to move activity*/
        val name = intent.getStringExtra(NAMA)
        val tingkatStres = intent.getStringExtra(TINGKAT_STRES)
        val persentase = intent.getStringExtra(PERSENTASE)
        val solusi = intent.getStringExtra(SOLUSI)
        /*print the text to result diagnosis*/
        tvnama.text = "Nama : $name"
        tvtingkatstres.text = "Tingkat Stres : $tingkatStres"
        tvpersentase.text = "Persentase : $persentase %"
        tvsolusi.text = "Solusi : $solusi"
      }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NAMA, tvnama.text.toString())
        outState.putString(TINGKAT_STRES, tvtingkatstres.text.toString())
        outState.putString(PERSENTASE, tvpersentase.text.toString())
        outState.putString(SOLUSI, tvsolusi.text.toString())
    }
  }