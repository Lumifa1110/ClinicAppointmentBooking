package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {

    private lateinit var phoneET : EditText
    private lateinit var emailET : EditText
    private lateinit var nameET : EditText
    private lateinit var passwordET : EditText
    private lateinit var registerBtn : Button

    private lateinit var database : DatabaseReference

    private fun initWidgets() {
        phoneET = findViewById(R.id.phoneET)
        emailET = findViewById(R.id.emailET)
        nameET = findViewById(R.id.nameET)
        passwordET = findViewById(R.id.passwordET)
        registerBtn = findViewById(R.id.registerBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        initWidgets()

        database = Firebase.database.reference

        registerBtn.setOnClickListener {
            if (!editTextIsEmpty()) {
                // Get EditText input
                val name = nameET.text.toString()
                val email = emailET.text.toString()
                val phone = phoneET.text.toString()
                val password = passwordET.text.toString()
                // Generate key
                val userId = database.push().key
                writeNewUser(userId!!, name, email, phone, password)
            }
        }
    }

    private fun editTextIsEmpty() : Boolean {
        if (phoneET.text.toString() == "" ||
            emailET.text.toString() == "" ||
            nameET.text.toString() == "" ||
            passwordET.text.toString() == "")
        {
            Toast.makeText(applicationContext
                , "Please fill in every field"
                , Toast.LENGTH_SHORT)
                .show()
            return true
        }
        return false
    }

    // Write user to database
    private fun writeNewUser(userId: String, name: String, email: String, phone: String, password: String) {
        val user = User(name, email, phone, password)
        database.child("Users").child(userId).setValue(user)
    }
}