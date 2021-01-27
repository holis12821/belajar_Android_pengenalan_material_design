package com.example.belajar_android_pengenalan_material_design

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.belajar_android_pengenalan_material_design.model.UserDiagnosa

class DetailDiagnosa : AppCompatActivity() {
    /*Initialize Field in the activity*/
    private lateinit var tvnpm: TextView
    private lateinit var tvnama: TextView
    private lateinit var tvjk: TextView
    private lateinit var tvjurusan: TextView
    private lateinit var tvuniversitas: TextView
    private lateinit var tvtingkatstres: TextView
    private lateinit var tvpersentase: TextView
    private lateinit var tvsolusi: TextView

    companion object {
        /*Object Companion or objek pendamping*/
        const val EXTRA_PARCELABLE: String = "EXTRA_PARCELABLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_diagnosa)

        /*call back function*/
        dataDiagnosa()
    }

  private fun dataDiagnosa(){
        /*define fineViewById with View*/
        this.tvnpm = findViewById(R.id.npm)
        this.tvnama = findViewById(R.id.tv_nama)
        this.tvjk = findViewById(R.id.tv_jk)
        this.tvjurusan = findViewById(R.id.tv_jurusan)
        this.tvuniversitas = findViewById(R.id.tv_universitas)
        this.tvtingkatstres = findViewById(R.id.tv_tingkat_stres)
        this.tvpersentase = findViewById(R.id.tv_persentase)
        this.tvsolusi = findViewById(R.id.tv_solusi)

      val dataDiagnosa = intent.getParcelableArrayListExtra<UserDiagnosa>(EXTRA_PARCELABLE) as ArrayList<UserDiagnosa>
      for (i in dataDiagnosa){
            val npm = "NPM : ${i.npm}"
            val nama = "Nama : ${i.nama}"
            val jk = "Jenis Kelamin : ${i.jenis_kelamin}"
            val jurusan = "Alamat : ${i.jurusan}"
            val universitas = "Universitas : ${i.universitas}"
            val tingkatstres = "Tingkat Stres : ${i.tingkat_stres}"
            val persentase = "Persentase : ${i.persentase}"
            val solusi = "Solusi : ${i.solusi}"

          tvnpm.text = npm
          tvnama.text = nama
          tvjk.text = jk
          tvjurusan.text = jurusan
          tvuniversitas.text = universitas
          tvtingkatstres.text = tingkatstres
          tvpersentase.text = persentase
          tvpersentase.text = solusi
      }
    }
}