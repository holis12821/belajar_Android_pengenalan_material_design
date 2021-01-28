package com.example.belajar_android_pengenalan_material_design

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
        /*call back function reference*/
        reference()

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
        tvpersentase.text = "Persentase : $persentase"
        tvsolusi.text = "Solusi : $solusi"
      }

    private fun reference(){
        /*TextView for reference*/
        val tvReference1 = findViewById<TextView>(R.id.tv_referensi1)
        val tvReference2 = findViewById<TextView>(R.id.tv_referensi2)
        val tvReference3 = findViewById<TextView>(R.id.tv_referens3)
        /*the result Reference*/
        val referensi1 = """S. Sukadiyanto, Stress Dan Cara Menguranginya,
            |J.Cakrawala Pendidik., vol. 1, no. 1, pp. 55–66, 2010, doi: 10.21831/cp.v1i1.218.""".trimMargin()
        val referensi2 = """H. Dadang. 2011. Stres, Cemas dan Depresi.
            |Jakarta:FKUI
        """.trimMargin()
        val referensi3 = """G. Goliszek.2005. Manajemen Stres. 
            |Jakarta:PT Bhuana Ilmu Populer""".trimMargin()

        /*Assignment to TextView in reference*/
        tvReference1.text = referensi1
        tvReference2.text = referensi2
        tvReference3.text = referensi3
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NAMA, tvnama.text.toString())
        outState.putString(TINGKAT_STRES, tvtingkatstres.text.toString())
        outState.putString(PERSENTASE, tvpersentase.text.toString())
        outState.putString(SOLUSI, tvsolusi.text.toString())
    }
  }