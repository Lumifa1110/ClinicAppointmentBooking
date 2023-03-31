package com.example.hyv_hpv_clinicbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    val REQUEST_CODE = 1111;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, LoginPage::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }
}