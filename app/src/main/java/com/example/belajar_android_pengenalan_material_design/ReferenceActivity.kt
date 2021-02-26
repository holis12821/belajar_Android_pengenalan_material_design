package com.example.belajar_android_pengenalan_material_design

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belajar_android_pengenalan_material_design.adapter.ReferenceGridAdapter
import com.example.belajar_android_pengenalan_material_design.fragmentusers.ReferenceFragmentBook
import com.example.belajar_android_pengenalan_material_design.model.ReferenceData
import com.example.belajar_android_pengenalan_material_design.onclick.OnReferenceListener
import kotlinx.android.synthetic.main.bar_layout.*
import kotlinx.android.synthetic.main.content_reference.*

@Suppress("SameParameterValue")
class ReferenceActivity : AppCompatActivity() {
    /*create lateinit field*/
    private var list: ArrayList<ReferenceData> = arrayListOf()
    private lateinit var listener: OnReferenceListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)
        /*class back method setAdapterReference*/
        setAdapterReference()
        /*set Toolbar*/
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Daftar Acuan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*Listener toolbar*/
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setAdapterReference(){
        setOnClickListenerReference()
        val adapter = ReferenceGridAdapter(list, listener)
        referenceList.setHasFixedSize(true)
        referenceList.layoutManager = LinearLayoutManager(this)
        referenceList.itemAnimator = DefaultItemAnimator()
        referenceList.adapter = adapter
    }
    /*create function to handle onClick to start a new Activity*/
    private fun setOnClickListenerReference() {
        /*Create anonymous object or anonymous class*/
        listener = object : OnReferenceListener {
            override fun onReferenceClick(referenceData: ReferenceData) {
                /*define when to handle click*/
                when (referenceData.judul) {
                    "Manajemen stres, cemas dan depresi"
                    -> gotoURLBooks1("https://tokopedia.link/dSJvrvQ96db")
                    "Manajemen stres"
                    -> gotoURLBook2("http://bit.ly/ManjemenStres")
                    "Stres dan cara menguranginya"
                    -> gotoPDFJurnal()
                }

        }
     }
   }
    /*create function to defining implemented for implisit intent*/
    private fun gotoURLBook2(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun gotoURLBooks1(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun gotoPDFJurnal() {
        /*create fragment*/
       val fragmentManager = supportFragmentManager
       val referenceFragmentBook = ReferenceFragmentBook()
        /*Akses Fragment menggunakan manager fragment dengan class FragmentManager*/
       val fragment = fragmentManager.findFragmentByTag(ReferenceFragmentBook::class.simpleName)
        /*Keyword instanceof atau is di kotlin digunakan untuk membandingkan sebuah objek, apakah objek dari class fragment*/
        /*merupakan instance dari class HomeFragment, atau di variabel fragment apakah bertipe data HomeFragment*/
        /*pastinya bukan walaupun class HomeFragment extends  fragment*/
        /*tetapi class homefragment sudah di instansiasi jadi mereka merupakan 2 objek yg berbeda*/
        /*walaupun HomeFragment extend ke fragment*/
        /*yang diberikan kedalam objek bertipe class fragment adalah nama objek class dari  fragment itu sendiri
        * jadi bukan instanceof dari HomeFragment jadi sama saja memberikan objeknya sendiri karena method findViewByTag bertipe class fragment*/
        /*Otomatis method tersebut akan mereturn type nya ke dalam method itu sendiri yg bertipe class fragment jadi objek class fragment diberikan kedalam method tersebut*/
        /*jadi variabel objek class fragment sudah mendapatkan objeknya tanpa di instance*/
        /*jadi menghasilkan nilai false tapi terdapat operator negasi sehingga nilainya dibalik menjadi true*/
        /*Karena HomeFragment di instansiasi jadi antara fragment dan HomeFragment adalah objek yg berbeda walaupun
        * HomeFragment extend ke Fragment dan class Fragment juga memiliki objek sendiri juga*/
        /*jadi masing-masing class mengintansiasi classnya tersebut jadi walaupun dia sebenarnya instanceof
        * tetapi jika di intansiasi masing-masing class tersebut dia bukan merupakan instanceof*/

        if (fragment !is ReferenceFragmentBook){
            /*create log tag to identified fragment by a name*/
            Log.d("ReferenceFragmentBook", "Fragment Name : ${ReferenceFragmentBook::class.simpleName}")
            /*mulai transaksi pada fragment dan ganti atau replace layout container frame layout menjadi fragment*/
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frameReference,
                    referenceFragmentBook, ReferenceFragmentBook::class.simpleName) /*karena kita mencari fragment berdasarkan tag*/
                    .commit()
        }
    }
}