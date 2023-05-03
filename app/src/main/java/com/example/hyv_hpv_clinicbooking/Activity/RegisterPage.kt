package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hyv_hpv_clinicbooking.Model.User
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {

    private lateinit var phoneET : EditText
    private lateinit var emailET : EditText
    private lateinit var nameET : EditText
    private lateinit var passwordET : EditText
    private lateinit var registerBtn : Button

    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth

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

        database = Firebase.database.getReference("Users")

        initListener()
    }

    private fun initListener() {
        registerBtn.setOnClickListener {
            onClickRegisterEmailPassword()
        }
    }

    private fun onClickRegisterEmailPassword() {
        if (!editTextIsEmpty()) {
            // Get EditText input
            val name = nameET.text.toString()
            val email = emailET.text.toString()
            val phone = phoneET.text.toString()
            val password = passwordET.text.toString()
            val role : String = "BenhNhan"

            // Init Firebase Authentication
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // create User
                        val user = BenhNhan(
                            HoTen = name,
                            SoDienThoai = phone,
                            Email = email,
                            PassWord = password
                        )
                        // update User profile in database
                        database.child(role).child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                            if (task.isSuccessful) {
                                // Register success
                                Toast.makeText(applicationContext
                                    , getString(R.string.toastRegisterSuccess)
                                    , Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else {
                                // Register fail
                                Toast.makeText(applicationContext
                                    , getString(R.string.toastRegisterFail)
                                    , Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        // Move to Login page
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Register fail
                        Toast.makeText(applicationContext
                            , getString(R.string.toastRegisterFail)
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
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
}