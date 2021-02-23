package com.example.belajar_android_pengenalan_material_design

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.belajar_android_pengenalan_material_design.`object`.CommonUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.bar_layout.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class SettingsActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_IMAGE = 100
    }

    private lateinit var imageUri: Uri
    private var loadingDialog: Dialog? = null
    private lateinit var dbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        /*define toolbar*/
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*Listener to back the home expert*/
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, ExpertActivity::class.java))
        }


        /*Initialize FirebaseAuth*/
        mAuth = FirebaseAuth.getInstance()
        val mUsers: FirebaseUser? = mAuth.currentUser
        /*Initialize DatabaseReference to Realtime Database*/
        if (mUsers != null) {
            dbRef = FirebaseDatabase.getInstance().reference.child("Users").child(mUsers.uid)
        }
        storageReference = FirebaseStorage.getInstance().getReference("profile_images")

        /*add data profile*/
        btn_update_status.setOnClickListener {
            val image = when{
                /*jika imageUri sudah terisi, sudah terisi jika kita sudah upload foto ke firebase*/
                /*check is property has benn initialize*/
                :: imageUri.isInitialized -> imageUri
                mUsers?.photoUrl == null  -> Uri.parse("https://picsum.photos/id/1/200/300")
                else -> mUsers.photoUrl
            }
            UserProfileChangeRequest.Builder()
                    .setPhotoUri(image)
                    .build().also {
                        mUsers?.updateProfile(it)
                                ?.addOnCompleteListener { profile  ->
                                    if (profile.isSuccessful){
                                        Toast.makeText(this,
                                        "Profile Updated",
                                        Toast.LENGTH_SHORT).show()
                                    }
                                }?.addOnFailureListener { error ->
                                    Toast.makeText(this,
                                            "Profil Error Updated ${error.message}",
                                            Toast.LENGTH_SHORT).show()
                                }
                    }

            updateSettings()
        }

        /*RetrieveUserInformation*/
        retrieveUserInformation()

        /*id Button on click listener*/
        profile_image.setOnClickListener {
            intentActionContent()
        }
    }


    /*function Intent to access media*/
    private fun intentActionContent(){
        val galleryIntent = Intent()
        galleryIntent.action =  Intent.ACTION_GET_CONTENT
        val image = "image/*"
        galleryIntent.type = image
        /*startActivityForResult*/
        startActivityForResult(galleryIntent, REQUEST_IMAGE)
    }
    /*jadi kita tangkap hasilnya di method ini*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*Condition if*/
        /*jadi RESULT_OK akan men trigger jika kita masuk ke media penyimpanan internal
        * berhasil di klik gambarnya. return value nya RESULT_OK dan jika RESULT_OK == resultCode maka terpenuhi
        * karena RESULT_OK akan mengirim nilainya ke resultCode */
        if ((requestCode == REQUEST_IMAGE) && (resultCode == RESULT_OK) && (data != null)){
            /*Crop Image*/
            CropImage.activity()
                    ?.also {
                        it.setGuidelines(CropImageView.Guidelines.ON)
                        it.setAspectRatio(1, 1)
                        it.start(this)
                    }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            /*if result code = RESULT_OK then showing the image*/
            /*result code sama dengan RESULT_OK maka akan mentrigger jika gambar yg dipilih berhasil*/
            if (resultCode == RESULT_OK){
                /*Progress bar Loading is here*/
                showLoadingBar()
                /*Define Handler to handle tread time process animation progressDialog*/
                Handler().postDelayed({
                    hiddenLoadingBar()
                }, 5000)
                /*get Uri image*/
                imageUri = result.uri
                var bitMap: Bitmap? = null
                try {
                    @Suppress("DEPRECATION")
                    bitMap = MediaStore.Images
                            .Media
                            .getBitmap(contentResolver, imageUri)
                }catch (e: IOException){
                    e.printStackTrace()
                }
                /*define byte arrayOS*/
                val byteAOS = ByteArrayOutputStream()
                /*Compress file to JPEG*/
                bitMap?.compress(Bitmap.CompressFormat.JPEG, 100, byteAOS)
                /*Casting ByteArrayOutputStream toByteArray */
                val image = byteAOS.toByteArray()

                val imageReference = mAuth.currentUser?.uid?.let {
                    storageReference.child("$it.jpg")
                }
                /*Upload file image ke firebase storage yang mana menggunakan referensi*/
                imageReference?.putBytes(image)
                        ?.addOnCompleteListener {
                            /*if task successful*/
                            if (it.isSuccessful){
                                Toast.makeText(this,
                                        "Image added successfully",
                                Toast.LENGTH_SHORT).show()
                                /*mengambil URl tempat dimana foto tersebut kita upload ke firebase storage*/
                                imageReference.downloadUrl.addOnCompleteListener { task->
                                    task.result?.let { uri ->
                                        imageUri = uri
                                        val mapUri = hashMapOf<String, Any?>()
                                        mapUri["imageUri"] = imageUri.toString()
                                        val mUsers: FirebaseUser? = mAuth.currentUser
                                        val dbRefUri = mUsers?.uid?.let { user ->
                                            FirebaseDatabase.getInstance()
                                                    .reference
                                                    .child("profile_images")
                                                    .child(user)
                                        }
                                        /*push the data in realtime database*/
                                        dbRefUri?.push()
                                                ?.setValue(mapUri)
                                                ?.addOnCompleteListener {done ->
                                                    if (done.isSuccessful) {
                                                        Toast.makeText(this,
                                                        "Image added database successfully",
                                                        Toast.LENGTH_SHORT).show()
                                                    }
                                                    /*set error  request*/
                                                }?.addOnFailureListener {
                                                  Toast.makeText(this,
                                                  "image failed to added in database",
                                                  Toast.LENGTH_SHORT).show()
                                                }
                                        profile_image.setImageBitmap(bitMap) /*Showing the image to CircleImageView*/
                                    }
                                }
                            } else {
                                Toast.makeText(this,
                                        "Failed uploading image",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }

    /*function hidden loading animation*/
    private fun hiddenLoadingBar(){
        loadingDialog?.also {if (it.isShowing) it.cancel()}
    }

    private fun showLoadingBar(){
        hiddenLoadingBar()
        loadingDialog = CommonUtils.showLoadingDialog(this)
    }

    private fun updateSettings(){
        /*Initialize EditText*/
        val setUsername =  set_username.text.toString().trim()
        val setStatus = status.text.toString().trim()
        /*define when custom that similar if else if*/
        when {
            TextUtils.isEmpty(setUsername) -> {
                Toast.makeText(this,
                        "Please write your username first...",
                        Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(setStatus) -> {
                Toast.makeText(this,
                        "Please write your status",
                        Toast.LENGTH_SHORT).show()
            } else -> {
            val currentUserId = mAuth.currentUser?.uid
            val profilMap = hashMapOf<String, String?>()
            /*Conditional if, if the current user id has containing null
            * then unfulfilled */
            if (currentUserId != null){
                profilMap["uid"] = currentUserId
                profilMap["name"] = setUsername
                profilMap["status"] = setStatus
                /*add data to DatabaseReference*/
                dbRef.push()
                        .setValue(profilMap)
                        .addOnCompleteListener {
                            /*Condition if, if task successful*/
                            if (it.isSuccessful){
                                sendAdminActivity() /*Intent to adminActivity*/
                                Toast.makeText(this,
                                        "Profil Updated Successfully...",
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                val message = it.exception.toString()
                                Toast.makeText(this,
                                        "Profile Updated Error : $message",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            }
          }
        }
    }

    private fun retrieveUserInformation() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /*Snapshoot for data in firebase*/
                /*retrieve data users*/
                /*disable EditText if the user account has been created*/
               if (snapshot.exists()){
                   for(h in snapshot.children){
                       val retrieveUser = h.child("name").value.toString()
                       val retrieveStatus = h.child("status").value.toString()
                       /*print the data*/
                       set_username.setText(retrieveUser)
                       status.setText(retrieveStatus)
                   }
               } else {
                   Toast.makeText(this@SettingsActivity,
                   "Please update your profile information",
                   Toast.LENGTH_SHORT).show()
                   /*Progress bar Loading is here*/
                   showLoadingBar()
                   /*Define Handler to handle tread time process animation progressDialog*/
                   Handler().postDelayed({
                       hiddenLoadingBar()
                   }, 2300)
                   profile_image.setImageResource(R.drawable.profile_image)
               }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SettingsActivity,
                error.message,
                Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        /*Authentication Users*/
        val mUsers: FirebaseUser? = mAuth.currentUser
        if (mUsers != null /*alias login*/){
            if (mUsers.photoUrl != null){
                Picasso.get()
                        .load(mUsers.photoUrl)
                        .into(profile_image)
            }
        } else {
            Toast.makeText(this,
                    "Please login again !!",
                    Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
        }
    }

    private fun sendAdminActivity() {
        val intentAdmin = Intent(this@SettingsActivity, ExpertActivity::class.java)
        startActivity(intentAdmin)
        finish()
    }

}