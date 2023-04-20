package com.example.hyv_hpv_clinicbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var emailET : EditText
    private lateinit var confirmBTN : Button
    private lateinit var backBTN : Button

    private fun initWidgets() {
        emailET = findViewById(R.id.emailET)
        confirmBTN = findViewById(R.id.confirmBTN)
        backBTN = findViewById(R.id.backBTN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        initWidgets()
        initListeners()

        val intent = intent
        val email = intent.getStringExtra("reset password")

        emailET.setText(email)
    }

    private fun initListeners() {
        confirmBTN.setOnClickListener {
            resetPassword()
        }

        backBTN.setOnClickListener {
            finish()
        }
    }

    private fun resetPassword() {
        Firebase.auth.sendPasswordResetEmail(emailET.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext
                        , getString(R.string.toastSendEmailSuccess)
                        , Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                else {
                    Toast.makeText(applicationContext
                        , getString(R.string.toastSendEmailFail)
                        , Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }
}