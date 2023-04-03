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

class LoginPage : AppCompatActivity() {
    val REQUEST_CODE = 1111;

    var username: EditText?= null
    var password: EditText?= null
    var loginBTN: Button?= null
    var showPassword: ImageButton?= null
    var backBTN: ImageButton?= null
    var dangKi: TextView?= null
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val patient_username:String = "patient"
        val patient_password:String = "123"
        val doctor_username:String = "doctor"
        val doctor_password:String = "123"

        username = findViewById(R.id.usernameET)
        password = findViewById(R.id.passwordET)
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

        loginBTN?.setOnClickListener {
            println(username?.text)
            println(password?.text)
            if(patient_username == username?.text.toString() && patient_password == password?.text.toString()) {
                val intent = Intent(this, UserHomePage::class.java)
                startActivityForResult(intent, REQUEST_CODE)
//                val editor = sharedPreferences.edit()
//                editor.clear()
//                editor.putString("isLogin", "Login")
//                editor.putString("username", username?.text.toString())
//                editor.commit()
            }

            if(doctor_username == username?.text.toString() && doctor_password == password?.text.toString()) {
                val intent = Intent(this, DoctorHomePage::class.java)
                startActivityForResult(intent, REQUEST_CODE)
//                val editor = sharedPreferences.edit()
//                editor.clear()
//                editor.putString("isLogin", "Login")
//                editor.putString("username", username?.text.toString())
//                editor.commit()
            }
        }

        backBTN?.setOnClickListener {

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

        dangKi?.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }
}