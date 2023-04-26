package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.hyv_hpv_clinicbooking.R
import com.example.hyv_hpv_clinicbooking.ResetPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBTN: Button
    private lateinit var showPassword: ImageButton
    private lateinit var dangKi: TextView
    private lateinit var resetPasswordBTN: TextView

    // Firebase
    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth

    private fun initWidgets() {
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        loginBTN = findViewById(R.id.loginBTN)
        dangKi = findViewById(R.id.dangKi)
        resetPasswordBTN = findViewById(R.id.resetPasswordBTN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        initWidgets()

        database = Firebase.database.reference

        initListener()
    }

    private fun initListener() {

//        showPassword?.setOnClickListener {
//            if(showPassword?.tag == "showPassword") {
//                showPassword?.setTag("hidePassword")
//                password?.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
//                showPassword?.setBackgroundResource(R.drawable.view)
//
//            }else if(showPassword?.tag == "hidePassword") {
//                showPassword?.setTag("showPassword")
//                password?.setTransformationMethod(PasswordTransformationMethod.getInstance())
//            }
//        }

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
                        val user = auth.currentUser
                        // Login success
                        Toast.makeText(applicationContext
                            , getString(R.string.toastLoginSuccess)
                            , Toast.LENGTH_SHORT)
                            .show()
                        // Move to Login page
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login fail
                        Toast.makeText(applicationContext
                            , getString(R.string.toastLoginFail)
                            , Toast.LENGTH_SHORT)
                            .show()
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
}