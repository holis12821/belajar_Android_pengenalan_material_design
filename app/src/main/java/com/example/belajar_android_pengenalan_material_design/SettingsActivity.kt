 package com.example.belajar_android_pengenalan_material_design

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        /*Initialize FirebaseAuth and database  Reference*/
        dbRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        /*define button to onClickListener*/
        btn_update_status.setOnClickListener {
            updateSettings()
        }
    }

    @SuppressLint("ShowToast")
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
                    dbRef.child("user").child(currentUserId)
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

    private fun sendAdminActivity() {
       val intentAdmin = Intent(this@SettingsActivity, ExpertActivity::class.java)
        startActivity(intentAdmin)
        finish()
    }
}