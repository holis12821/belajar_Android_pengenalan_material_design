package com.example.belajar_android_pengenalan_material_design

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.example.belajar_android_pengenalan_material_design.`object`.CommonUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_settings.*

 class SettingsActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var userProfileImagesRef: StorageReference
    private var loadingDialog: Dialog? = null

    /*create object companion to initialize variabel const*/
    /*Companion object has same the static in the java*/
    companion object{
        const val GaLLERYPICT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        /*Initialize FirebaseAuth and database  Reference*/
        dbRef = FirebaseDatabase.getInstance().reference
        /*Initialize Firebase Storage*/
        userProfileImagesRef = FirebaseStorage.getInstance().reference.child("profile_images")
        mAuth = FirebaseAuth.getInstance()

        /*disable EditText if the user account has been created*/
        set_username.visibility = View.INVISIBLE

        /*define button to onClickListener*/
        btn_update_status.setOnClickListener {
            updateSettings()
        }
        retrieveUserInformation()


        /*Set Profile Image to onClickListener*/
        profile_image.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action =  Intent.ACTION_GET_CONTENT
            val image = "image/*"
            galleryIntent.type = image
            /*startActivityForResult*/
            startActivityForResult(galleryIntent, GaLLERYPICT)
        }
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         /*condition if*/
         if (requestCode == GaLLERYPICT && resultCode == RESULT_OK && data != null){
             /*mengambil data dari objek intent*/
             val imageUri: Uri? = data.data
             /*CropImage*/
             CropImage.activity()
                     .setGuidelines(CropImageView.Guidelines.ON)
                     .setAspectRatio(1,1)
                     .start(this)
         }
         if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
             val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
             /*condition if*/
             if (resultCode == RESULT_OK){
                 /*Progress bar Loading is here*/
                 showLoading()
                 /*Define Handler to handle tread time process animation progressDialog*/
                 Handler().postDelayed({
                     hideLoading()
                  }, 2000)
                 val resultUri: Uri? = result.uri
                 /*Define firebase Storage to access the permission on the device*/
                 val currentUserID = mAuth.currentUser?.uid
                 /*condition if*/
                 if (currentUserID != null){
                     val filePath: StorageReference = userProfileImagesRef.child("$currentUserID.jpg")
                     if (resultUri != null) {
                         filePath.putFile(resultUri).addOnCompleteListener { it ->
                             if (it.isSuccessful){
                                 Toast.makeText(this@SettingsActivity,
                                         "Profile Image Uploaded Successfully....",
                                         Toast.LENGTH_SHORT).show()
                                 /*downloadUri for retrieve image file*/
                                 val downloadUri = it.result
                                 val sDownloadUri = downloadUri.toString()
                                 dbRef.child("Users").child(currentUserID).child("image")
                                         .setValue(sDownloadUri)
                                         .addOnCompleteListener {
                                             /*if successful than show in the Toast*/
                                             if (it.isSuccessful){
                                                 Toast.makeText(this@SettingsActivity,
                                                         "Image save in Database, Successfully.... ",
                                                         Toast.LENGTH_SHORT).show()
                                                         loadingDialog?.dismiss()
                                             } else {
                                                 val message = it.exception.toString()
                                                 Toast.makeText(this@SettingsActivity,
                                                         "Error : $message",
                                                         Toast.LENGTH_SHORT).show()
                                                 loadingDialog?.dismiss()
                                             }
                                         }
                             } else{
                                 val message = it.exception.toString()
                                 Toast.makeText(this@SettingsActivity
                                 ,"Failed to uploaded image : $message",
                                 Toast.LENGTH_SHORT).show()
                                 loadingDialog?.dismiss()
                             }
                         }
                     }
                 }
             }
         }
     }

    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing) it.cancel()}
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(this)
    }

    private fun updateSettings() {
        /*initialize EditText*/
        val setUsername = set_username.text.toString()
        val setStatus = status.text.toString()
        /*Define when custom that similar if else if*/
        when {
            TextUtils.isEmpty(setUsername) -> {
                Toast.makeText(this, "Please write your username first....", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(setStatus) -> {
                Toast.makeText(this, "Please write your status", Toast.LENGTH_SHORT).show()
            }
            else -> {
                /*Initialize Current User ID to FirebaseAuth*/
                val currentUserId = mAuth.currentUser?.uid
                val profilMap = hashMapOf<String, String?>()
                /*Conditional if, if the current user id has containing null
                * then unfulfilled */
                if (currentUserId != null){
                    profilMap["uid"] = currentUserId
                    profilMap["name"] = setUsername
                    profilMap["status"] = setStatus
                    /*add data to  DatabaseReference*/
                    dbRef.child("Users").child(currentUserId)
                            .setValue(profilMap).addOnCompleteListener {
                                /*Conditional if*/
                                if (it.isSuccessful){
                                    sendAdminActivity()
                                    Toast.makeText(this, "Profile Updated Successfully....", Toast.LENGTH_SHORT).show()
                                } else {
                                    val message = it.exception.toString()
                                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                                }
                            }
                }

            }
        }

    }

    private fun retrieveUserInformation() {
        val currentUserId = mAuth.currentUser?.uid
        if (currentUserId != null) {
            dbRef.child("Users").child(currentUserId)
                    .addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if ((snapshot.exists())
                                    && (snapshot.hasChild("name")
                                            && (snapshot.hasChild("image")))){
                                /*Snapshoot for data in firebase*/
                                val retrieveUsername = snapshot.child("name").value.toString()
                                val retrieveStatus = snapshot.child("status").value.toString()
                                val retrieveProfileImage = snapshot.child("image").value.toString()
                                /*print the data*/
                                set_username.setText(retrieveUsername)
                                status.setText(retrieveStatus)
                                /*Picasso to showing the image*/
                                Picasso.get().load(retrieveProfileImage).into(profile_image)

                            } else if ((snapshot.exists()) && (snapshot.hasChild("name"))){
                                val retrieveUsername = snapshot.child("name").value.toString()
                                val retrieveStatus = snapshot.child("status").value.toString()
                                /*print the data*/
                                set_username.setText(retrieveUsername)
                                status.setText(retrieveStatus)
                            } else{
                                set_username.visibility = View.VISIBLE
                                Toast.makeText(this@SettingsActivity,
                                        "Please update your profile information",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                           Toast.makeText(this@SettingsActivity,
                           "Update status failed please  try again !!",
                           Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }

    private fun sendAdminActivity() {
       val intentAdmin = Intent(this@SettingsActivity, ExpertActivity::class.java)
        startActivity(intentAdmin)
        finish()
    }
}