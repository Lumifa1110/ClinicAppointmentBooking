package com.example.hyv_hpv_clinicbooking.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth

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
            val role = getUserRole(user)
            startHomePage(role)
        }
    }

    private fun getUserRole(user: FirebaseUser): String {
        var role = ""
        database.child(user.uid).get().addOnSuccessListener {
            role = it.child("role").value.toString()
            Log.i("firebase", role)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return role
    }

    private fun startHomePage(role: String) {
        val intent : Intent
        when (role) {
            // Benh nhan
            "patient" -> {
                intent = Intent(this, UserHomePage::class.java)
                startActivity(intent)
            }
            // Bac si
            "doctor" -> {
                intent = Intent(this, DoctorHomePage::class.java)
                startActivity(intent)
            }
            // Admin
            "admin" -> {
                intent = Intent(this, AdminHomePage::class.java)
                startActivity(intent)
            }
        }
    }
}