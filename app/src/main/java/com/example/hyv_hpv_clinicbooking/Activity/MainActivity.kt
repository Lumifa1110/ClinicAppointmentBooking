package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyv_hpv_clinicbooking.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
//        val intent = Intent(this, AdminHomePage::class.java)
        val intent = Intent(this, UserHomePage::class.java)

//        val intent = Intent(this, DoctorHomePage::class.java)
        startActivity(intent)

    }
}