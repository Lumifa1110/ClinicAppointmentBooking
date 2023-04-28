package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBTN: Button
    private lateinit var backBTN: ImageButton
    private lateinit var dangKi: TextView
    private lateinit var resetPasswordBTN: TextView
    private lateinit var createNewAccountBTN: TextView

    // Firebase
    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth

    private fun initWidgets() {
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        loginBTN = findViewById(R.id.loginBTN)
        dangKi = findViewById(R.id.dangKi)
        resetPasswordBTN = findViewById(R.id.resetPasswordBTN)
        createNewAccountBTN = findViewById(R.id.createNewAccountBTN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        initWidgets()

        database = Firebase.database.getReference("Users")

        initListener()
    }

    private fun initListener() {
        dangKi.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
        loginBTN.setOnClickListener {
            onClickLogin()
        }
        resetPasswordBTN.setOnClickListener {
            resetPassword()
        }
        createNewAccountBTN.setOnClickListener {
            createNewAccount()
        }
    }

    private fun onClickLogin() {
        if (!editTextIsEmpty()) {
            // Get EditText input
            val email = emailET.text.toString()
            val password = passwordET.text.toString()

            // Init Firebase Authentication
            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login success
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.toastLoginSuccess),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        // Get User role
                        val user = auth.currentUser
                        val role = getUserRole(user!!)
                        // Move to another page base on User role
                        startHomePage(role)
                    }
                }
        }
    }

    private fun editTextIsEmpty() : Boolean {
        if (emailET.text.toString() == "" ||
            passwordET.text.toString() == "")
        {
            Toast.makeText(applicationContext
                , getString(R.string.toastEmptyEditText)
                , Toast.LENGTH_SHORT)
                .show()
            return true
        }
        return false
    }

    private fun resetPassword() {
        val email = emailET.text.toString()
        if (email == "") {
            Toast.makeText(applicationContext
                , getString(R.string.toastEmptyEditText)
                , Toast.LENGTH_SHORT)
                .show()
        }
        else {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra("reset password", email)
            startActivity(intent)
        }
    }

        private fun createNewAccount() {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
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
            println("startHomePage1")
            val intent : Intent
            when (role) {
                // Benh nhan
                "patient" -> {
                    println("startHomePage1")
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