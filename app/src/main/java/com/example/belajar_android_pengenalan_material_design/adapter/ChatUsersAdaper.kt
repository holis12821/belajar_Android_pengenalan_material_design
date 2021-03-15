package com.example.belajar_android_pengenalan_material_design.adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belajar_android_pengenalan_material_design.R
import com.example.belajar_android_pengenalan_material_design.activity.MessageChatActivity
import com.example.belajar_android_pengenalan_material_design.model.UsersData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ChatUsersAdaper(
        options: FirestoreRecyclerOptions<UsersData>
 ) : FirestoreRecyclerAdapter<UsersData,
        ChatUsersAdaper.SearchViewHolder>(options) {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) : SearchViewHolder {
        val itemView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_search_item_layout,
                        parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int, userData: UsersData) {

        holder.userName?.text =  userData.username
        holder.email?.text =  userData.email
        Picasso.get()
                .load(userData.imageURL)
                .placeholder(R.drawable.profile_image)
                .into(holder.imgProfil)

        /*onClick item*/
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,
                    MessageChatActivity::class.java)
            intent.putExtra(MessageChatActivity.EXTRA_VISIT_ID, userData.userId)
            intent.putExtra(MessageChatActivity.EXTRA_USERNAME, userData.username)
            intent.putExtra(MessageChatActivity.EXTRA_PROFIL_IMAGE, userData.imageURL)
            /*Start Activity to intent with carrying data*/
            holder.itemView
                    .context
                    .startActivity(intent)
        }
    }

    inner class SearchViewHolder(
            itemView : View
    ) : RecyclerView.ViewHolder (itemView) {
        val userName : TextView? = itemView.findViewById(R.id.username)
        val email : TextView? = itemView.findViewById(R.id.email)
        val imgProfil : CircleImageView? = itemView.findViewById(R.id.profile_image)
    }
}