package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase :  DatabaseReference = FirebaseDatabase.getInstance().getReference()

        // Write a message to the database
        val intent = Intent(this, AdminHomePage::class.java)
        startActivity(intent)

    }
}