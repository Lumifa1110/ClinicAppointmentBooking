package com.example.hyv_hpv_clinicbooking.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth

    private val roles = arrayOf<String>("BenhNhan", "BacSi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        database = Firebase.database.getReference("Users")
        auth = FirebaseAuth.getInstance()

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        else {
            getUserRole(user)
        }
    }

    private fun getUserRole(user: FirebaseUser) {
        for (role in roles) {
            val query = database.child(role)
                .orderByChild("email")
                .equalTo(user.email)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.i("Login as ", role)
                        startHomePage(role)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }

    private fun startHomePage(role: String) {
        val intent : Intent
        when (role) {
            "BenhNhan" -> {
                intent = Intent(this, UserHomePage::class.java)
                startActivity(intent)
            }
            "BacSi" -> {
                intent = Intent(this, DoctorHomePage::class.java)
                startActivity(intent)
            }
            "Admin" -> {
                intent = Intent(this, AdminHomePage::class.java)
                startActivity(intent)
            }
        }
    }
}