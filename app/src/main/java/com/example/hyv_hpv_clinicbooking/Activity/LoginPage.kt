package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginPage : AppCompatActivity() {
    private val REQUEST_CODE = 1111;

    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBTN: Button
    private lateinit var showPassword: ImageButton
    private lateinit var backBTN: ImageButton
    private lateinit var dangKi: TextView
    private lateinit var sharedPreferences: SharedPreferences

    // Firebase
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Init Firebase User reference
        database = FirebaseDatabase.getInstance().getReference()

        val patient_username:String = "patient"
        val patient_password:String = "123"
        val doctor_username:String = "doctor"
        val doctor_password:String = "123"

        usernameET = findViewById(R.id.usernameET)
        passwordET = findViewById(R.id.passwordET)
        loginBTN = findViewById(R.id.loginBTN)
        backBTN = findViewById(R.id.backBTN)
        dangKi = findViewById(R.id.dangKi)
//        showPassword = findViewById(R.id.showPassword)

        //Lưu đăng nhập
        sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        //Check đã đăng nhập hay chưa
        var isLogin = sharedPreferences.getString("isLogin", null);
        var usernameSave = sharedPreferences.getString("username", null);

        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()

        if(isLogin == "Login") {
            val intent = Intent(this, UserHomePage::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        loginBTN.setOnClickListener {
            println(usernameET.text)
            println(passwordET.text)
            if(patient_username == usernameET.text.toString() && patient_password == passwordET.text.toString()) {
                val intent = Intent(this, UserHomePage::class.java)
                startActivityForResult(intent, REQUEST_CODE)
//                val editor = sharedPreferences.edit()
//                editor.clear()
//                editor.putString("isLogin", "Login")
//                editor.putString("username", username?.text.toString())
//                editor.commit()
            }

            if(doctor_username == usernameET.text.toString() && doctor_password == passwordET.text.toString()) {
                val intent = Intent(this, DoctorHomePage::class.java)
                startActivityForResult(intent, REQUEST_CODE)
//                val editor = sharedPreferences.edit()
//                editor.clear()
//                editor.putString("isLogin", "Login")
//                editor.putString("username", username?.text.toString())
//                editor.commit()
            }
        }

        backBTN.setOnClickListener {

        }

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
    }

    // Save User login data to Firebase
    private fun writeUserToFirebase() {
        val username = usernameET.text.toString()
        val password = passwordET.text.toString()
    }
}