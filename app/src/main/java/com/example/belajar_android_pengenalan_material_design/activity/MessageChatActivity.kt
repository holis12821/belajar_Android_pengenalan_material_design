@file:Suppress("DEPRECATION")

package com.example.belajar_android_pengenalan_material_design.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belajar_android_pengenalan_material_design.R
import com.example.belajar_android_pengenalan_material_design.adapter.DisplayChatAdapter
import com.example.belajar_android_pengenalan_material_design.model.Chats
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Continuation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_message_chat.*

class  MessageChatActivity : AppCompatActivity() {
    /*Create firebase user on field*/
    private var mUsers: FirebaseUser? = null
    private var visitId: String? = ""
    private var timeStamp: String? = null
    private var hisImage: String? = null

    /*Initialize DisplayChatAdapter*/
    private var displayChatAdapter: DisplayChatAdapter? = null
    private var mChatList: MutableList<Chats>? = null

    /*Create companion Object*/
    companion object {
        const val EXTRA_VISIT_ID = "visit_id"
        const val EXTRA_USERNAME = "username"
        const val EXTRA_PROFIL_IMAGE = "imageUrl"
        const val REQUEST_IMAGE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)
        /*init toolbar*/
        setSupportActionBar(toolbar_message)
        toolbar_message.title = ""
        mUsers = FirebaseAuth.getInstance().currentUser
        visitId = intent?.getStringExtra(EXTRA_VISIT_ID).toString()
        /*get a data to display the component UI*/
        displayUsers()
        /*display to message*/
        retrieveMessage(mUsers?.uid, visitId!!, hisImage)
        /*declaration RecyclerView*/
        recyclerViewMessage.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerViewMessage.layoutManager = linearLayoutManager

        /*send message button*/
        send_message_btn.setOnClickListener {
            val message = text_Message.text.toString()
            /*condition if*/
            if (message == "") {
                Toast.makeText(applicationContext,
                        "Please write a message here",
                        Toast.LENGTH_SHORT).show()
            } else {
                sendMessageToUser(mUsers?.uid, visitId, message)
            }

            text_Message.setText("")
        }
        /*attachment image for send chat*/
        attach_image_file_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            startActivityForResult(intent, REQUEST_IMAGE)
        }
    }

    private fun retrieveMessage(
            senderId: String?,
            receiverId: String,
            hisImage: String?) {
        mChatList = mutableListOf() /*instance mutableListOf for ArrayList*/
        /*karena array list mengimplementasikan interface List dan MutableList*/
        val reference = FirebaseDatabase.getInstance()
                .reference
                .child("Chats")
        reference.addValueEventListener(object : ValueEventListener {
            /*jadi setiap kita menambahkan data otomatis listener
             akan mendengar perubahan event yaitu perubahan data yg terjadi*/
            /*jadi sebagai penghubung antara menampilkan data dan event perubahan*/
            override fun onDataChange(snapshot: DataSnapshot) {
                (mChatList as ArrayList<Chats>).clear() /*kita pastikan arraylistnya itu kosong*/
                for (i in snapshot.children) {
                    val chats = i.getValue(Chats::class.java)
                    /*Conditional if*/
                    if (chats?.receiver?.equals(senderId)!! && chats.sender.equals(receiverId)
                            || chats.receiver.equals(receiverId) && chats.sender.equals(senderId)) {
                        (mChatList as ArrayList<Chats>).add(chats)
                    }
                    displayChatAdapter = hisImage?.let { image ->
                        DisplayChatAdapter(this@MessageChatActivity,
                                mChatList as ArrayList<Chats>, image)
                    }
                    recyclerViewMessage.adapter = displayChatAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(MessageChatActivity::class.java.simpleName,
                        "Failed to retrieve data : ${error.message}")
            }
        })
    }

    private fun displayUsers() {
        val username = intent.getStringExtra(EXTRA_USERNAME)
        hisImage = intent.getStringExtra(EXTRA_PROFIL_IMAGE)
        /*set Text username to display profile*/
        user_message.text = username
        /*Defined Picasso  to retrieve the profile image*/
        Picasso.get()
                .load(hisImage)
                .placeholder(R.drawable.profile_image)
                .into(profile_image_message)
    }

    private fun sendMessageToUser(senderId: String?,
                                  receiverId: String?,
                                  message: String) {
        /*create DatabaseReference*/
        val mRef = FirebaseDatabase.getInstance()
                .reference
        val messageKey = mRef.push().key /*otomatis datanya akan tampil disemua user*/
        /*create Hashmap sebagai penyimpanan data pada firebase */
        /*Create timeStamp for time message*/
        timeStamp = System.currentTimeMillis().toString()

        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["sender"] = senderId
        messageHashMap["message"] = message
        messageHashMap["timestamp"] = timeStamp
        messageHashMap["receiver"] = receiverId
        messageHashMap["isseen"] = false
        messageHashMap["url"] = ""
        messageHashMap["messageId"] = messageKey

        mRef.child("Chats")
                .child(messageKey!!)
                .setValue(messageHashMap)
                .addOnCompleteListener { success ->
                    if (success.isSuccessful) {

                        val chatListReference = mUsers?.uid?.let { uid ->
                            visitId?.let { visit ->
                                FirebaseDatabase
                                        .getInstance()
                                        .reference
                                        .child("ChatList")
                                        .child(uid)
                                        .child(visit)
                            }
                        }
                        chatListReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (!snapshot.exists()) {
                                    chatListReference.child("id")
                                            .setValue(visitId)
                                }
                                val chatListReceiverRef = mUsers?.uid?.let { uid ->
                                    visitId?.let { visit ->
                                        FirebaseDatabase
                                                .getInstance()
                                                .reference
                                                .child("ChatList")
                                                .child(visit)
                                                .child(uid)
                                    }
                                }
                                chatListReceiverRef?.child("id")
                                        ?.setValue(mUsers?.uid)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@MessageChatActivity,
                                        "Failed add to chatlist in database",
                                        Toast.LENGTH_SHORT).show()
                            }
                        })

                        /*Implement the push notification using Firebase Cloud Messaging*/
                        val reference = mUsers?.uid?.let {
                            FirebaseDatabase.getInstance()
                                    .reference
                                    .child("Users")
                                    .child(it)
                        }

                    }
                }
    }

    @Suppress("DEPRECATION")
    /*jadi kita tangkap hasilnya di method ini*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*Condition if*/
        /*jadi RESULT_OK akan men trigger jika kita masuk ke media penyimpanan internal
        * berhasil di klik gambarnya. return value nya
        *  RESULT_OK dan jika RESULT_OK == resultCode maka terpenuhi
        * karena RESULT_OK akan mengirim nilainya ke resultCode */
        if ((requestCode == REQUEST_IMAGE)
                && (resultCode == Activity.RESULT_OK)
                && (data != null)) {
            /*Define progress bar*/
            val loadingBar = ProgressDialog(applicationContext)
            loadingBar.setMessage("Please wait, image is sending....")
            loadingBar.show()
            /*get data from intent to access image */
            val fileUri = data.data
            val storageReference = FirebaseStorage.getInstance()
                    .getReference("image_sent_message")
            val ref = FirebaseDatabase.getInstance()
                    .reference
            val messageId = ref.push().key
            val filePath = storageReference.child("$messageId.jpg")

            /*Create upload task*/
            val uploadTask: StorageTask<UploadTask.TaskSnapshot> = fileUri?.let {
                filePath.putFile(it)
            }!!

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation filePath.downloadUrl
            }).addOnCompleteListener { complete ->
                if (complete.isSuccessful) {
                    val downloadUri = complete.result
                    val url = downloadUri.toString()
                    timeStamp = System.currentTimeMillis().toString()
                    val messageHashMap = HashMap<String, Any?>()
                    messageHashMap["sender"] = mUsers?.uid
                    messageHashMap["message"] = "sent you an image."
                    messageHashMap["timestamp"] = timeStamp
                    messageHashMap["receiver"] = visitId
                    messageHashMap["isseen"] = false
                    messageHashMap["url"] = url
                    messageHashMap["messageId"] = messageId

                    if (messageId != null) {
                        ref.child("Chats")
                                .child(messageId)
                                .setValue(messageHashMap)
                    }
                    loadingBar.dismiss()
                }
            }
        }
    }
}