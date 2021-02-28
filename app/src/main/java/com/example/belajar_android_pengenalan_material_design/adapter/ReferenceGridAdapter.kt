package com.example.belajar_android_pengenalan_material_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belajar_android_pengenalan_material_design.R
import com.example.belajar_android_pengenalan_material_design.model.ReferenceData
import com.example.belajar_android_pengenalan_material_design.onclick.OnReferenceListener
import kotlinx.android.synthetic.main.reference_list.view.*

class ReferenceGridAdapter(
        private val mItems: MutableList<ReferenceData>,
        private val onReferenceListener: OnReferenceListener
        ) : RecyclerView.Adapter<ReferenceGridAdapter.ViewHolder>() {

    /*Create block init*/
    /*init block akan dipanggil ketika sebuah
    constructor dipanggil dan dijalankan*/

    init {
        /*create reference data*/
        val reference1 = ReferenceData(
                /*Named argument*/
                judul = "Manajemen stres, cemas dan depresi",
                pengarang = "Prof. Dr. dr. H. Dadang Hawari, Psikiater",
                tahun = 2011,
                penerbit = "Universitas Indonesia",
                thumbnail = R.drawable.buku2,
                rating = R.drawable.rating_buku1
        )

        val reference2 = ReferenceData(
                judul = "Manajemen stres",
                pengarang = "Dr. Andrew Goliszek",
                tahun = 2005,
                penerbit = "PT Bhuana Ilmu Populer",
                thumbnail = R.drawable.buku3,
                rating = R.drawable.rating_buku2
        )

        val reference3 = ReferenceData(
                judul = "Stres dan cara menguranginya",
                pengarang = "Sukadiyanto",
                tahun = 2010,
                penerbit = "Cakrawala Pendidikan",
                thumbnail = R.drawable.jurnal,
                rating = R.drawable.rating_buku3
        )

    /*add the object data class to interface list*/
        mItems.add(reference1)
        mItems.add(reference2)
        mItems.add(reference3)

    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
       val v =  LayoutInflater.from(parent.context)
               .inflate(R.layout.reference_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reference = mItems[position]
        holder.setData(reference)

        /*onClick button*/
        holder.itemView.setOnClickListener {
            onReferenceListener.onReferenceClick(mItems[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(
            itemView) {
       /*create fun set data*/
      fun setData(data: ReferenceData?){
          /*set field attribute for reference book*/
           itemView.img_thumbnail.setImageResource(data?.thumbnail ?: R.drawable.ic_baseline_image_24)
           itemView.judul.text = data?.judul
           itemView.pengarang.text = data?.pengarang
           itemView.tahun.text = data?.tahun.toString()
           itemView.penerbit.text = data?.penerbit
           itemView.img_rating.setImageResource(data?.rating ?: R.drawable.ic_baseline_image_24)
           
      }

    }
}