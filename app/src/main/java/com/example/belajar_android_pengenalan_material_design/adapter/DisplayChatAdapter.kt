package com.example.belajar_android_pengenalan_material_design.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belajar_android_pengenalan_material_design.R
import com.example.belajar_android_pengenalan_material_design.model.Chats
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
/*Create by Nurholis*/
/*14-03-2021*/
/*RecyclerView MultipleView*/
class DisplayChatAdapter (
        mContext : Context,
        private var mChatList: MutableList<Chats>,
        imageUrl : String
) : RecyclerView.Adapter<DisplayChatAdapter.ViewHolderChat>()  {

    private var mContext : Context? = null /**/
    private var imageUrl : String? = ""
    private var mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    /*init will always be executed when the constructor is called*/
    init {
        this.mContext = mContext
        this.imageUrl = imageUrl
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) : ViewHolderChat {
        return if (viewType == 1){
            val itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_chat_right, parent, false)
            ViewHolderChat(itemView)
        } else {
            val itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_chat_left, parent, false)
            ViewHolderChat(itemView)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderChat, position: Int) {
        val chat : Chats = mChatList[position]
        /*display profile*/
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.profile_image)
                .into(holder.profileImage)

        if (chat.message.equals("sent you an image") && !chat.url.equals("") /*jadi ada gambarnya gk kosong*/) {
            /*chat position right side*/
            if (chat.sender.equals(mUser?.uid)) {
                holder.showMessageText?.visibility  = View.GONE
                holder.rightImageView?.visibility = View.VISIBLE
                Picasso.get()
                        .load(chat.url)
                        .placeholder(R.drawable.profile_image)
                        .into(holder.rightImageView)
                /*chat position left side*/
                /*jadi jika user lain mengirim pesan balik yg sebelumnya gue pernah ngirim pesan ke user tersebut
                 sender id nya menjadi user lain dan otomatis gk sama dengan uid dri gue karena gue login saat itu*/
                /*agar tetap dijalankan maka di negasi jadi true*/
            } else if (!chat.sender.equals(mUser?.uid)){
                holder.showMessageText?.visibility  = View.GONE
                holder.leftImageView?.visibility = View.VISIBLE
                Picasso.get()
                        .load(chat.url)
                        .placeholder(R.drawable.profile_image)
                        .into(holder.leftImageView)
            }
        } /*Text Message*/ else {
            holder.showMessageText?.text = chat.message
            /*Convert time stamp to dd/mm/yyy hh:mm */
            val calendar : Calendar? = Calendar.getInstance(Locale.ENGLISH)
            calendar?.timeInMillis = chat.timestamp.toLong()
            val dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString()
            holder.timeChat?.text = dateTime
            /*is seen status message*/
            /*set seen or delivered status of message*/
            if (position == mChatList.size -1) { /*karena position index array dimulai dri 0 ya rumusnya untuk menentukan
                posisi dari index itu n -1 karena dimulai dri 0*/
                /*ketika kita scroll ke atas maka ukuran dari size arrayListnya akan
                 berkurang atau berubah ubah seiring dengan scroll yang kita lakukan*/
                if (chat.isIsseen){
                    holder.isSeen?.text = "Seen"
                    if (chat.message.equals("sent you an image") && !chat.url.equals("")){
                        val lp : RelativeLayout.LayoutParams? = holder.isSeen?.layoutParams as RelativeLayout.LayoutParams?
                        lp?.setMargins(0, 245, 10, 0)
                        holder.isSeen?.layoutParams = lp
                    }
                } else {
                    holder.isSeen?.text = "Sent"
                    if (chat.message.equals("sent you an image") && !chat.url.equals("")){
                        val lp : RelativeLayout.LayoutParams? = holder.isSeen?.layoutParams as RelativeLayout.LayoutParams?
                        lp?.setMargins(0, 245, 10, 0)
                        holder.isSeen?.layoutParams = lp
                    }
                }
            } else {
                holder.isSeen?.visibility  = View.GONE
            }
        }
    }

    override fun getItemCount(
    ) : Int {
        return mChatList.size /*ketika di scroll otomatis dia akan
         kembali ke posisi blmnya atau indexnya menurun jika scroll
         kebawah indexnya bertambah seiring dengan bertambahnya data*/
    }
    /*ini hal mendasar untuk recyclerView multipleview*/
    override fun getItemViewType(
            position: Int
    ) : Int {
        return if (mChatList[position]
                        .sender.equals(mUser?.uid)) {
            1 /*sebenarnya positionnya sama aja dari 0 dst, cuma tergantung dari sender dan receivernya, ketika sender
            (gue) mengirim pesan otomatis posisi objeknya adalah gue sender dan user lain receiver dan sebaliknya*/
            /*berdasarkan posisi misal 0 atau satu dan posisinya itu tergantung dri posisi UID atau sender atau receiver yg mengirim pesan
             datanya jika sih receiver yang pengirim pesan balik
            otomatis sender yg diambil adalah senderId dari sih receiver yang mengirim pesan balik ke sender*/
            /*otomatis senderId nya berbeda dari UID dimana user yg saat ini login atau dalam arti gue yg login sebagai sender nurholis*/
            /*dan ketika seseorang atau pakarnya mengirim pesan otomatis senderIdnya menjadi
            senderId dri receivernya dan penerimanya adalah gue nurholis jadi ketika receiver
            mengirim pesan balik maka senderId(menjadi ID dri receiver) nya berbeda dengan UID dri user yg login atau sih sender*/
        } else {
            0
        }
    }

    inner class ViewHolderChat(
            itemView : View
    ) : RecyclerView.ViewHolder(itemView) {
        val profileImage : CircleImageView? =
                itemView.findViewById(R.id.profile_image)
        val showMessageText : TextView? =
                itemView.findViewById(R.id.messageChat)
        val leftImageView : ImageView? =
                itemView.findViewById(R.id.left_image_view)
        val rightImageView : ImageView? =
                itemView.findViewById(R.id.Right_image_view)
        val timeChat : TextView? =
                itemView.findViewById(R.id.timeChat)
        val isSeen : TextView? =
                itemView.findViewById(R.id.isSeenChat)
    }
}