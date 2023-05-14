package com.example.hyv_hpv_clinicbooking.Activity

import Admin
import BenhNhan
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
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
    private lateinit var registerDoctorBtn : TextView
    private lateinit var showPasswordBTN: ImageButton

    private lateinit var userDB : DatabaseReference
    private lateinit var auth  : FirebaseAuth

    private fun initWidgets() {
        phoneET = findViewById(R.id.phoneET)
        emailET = findViewById(R.id.oldPasswordET)
        nameET = findViewById(R.id.nameET)
        passwordET = findViewById(R.id.newPasswordET)
        registerBtn = findViewById(R.id.registerBtn)
        registerDoctorBtn = findViewById(R.id.registerDoctorBTN)
        showPasswordBTN = findViewById(R.id.showPassword)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        initWidgets()

        userDB = Firebase.database.getReference("Users")

        initListener()
    }

    private fun initListener() {
        showPasswordBTN.setOnClickListener {
            val isPasswordVisible = passwordET.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            if (isPasswordVisible) {
                // Nếu mật khẩu đã được hiển thị, chuyển về chế độ ẩn
                passwordET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordBTN.setImageResource(R.drawable.invisible)
            } else {
                // Nếu mật khẩu đã bị ẩn, chuyển sang chế độ hiển thị
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordBTN.setImageResource(R.drawable.view)
            }

            // Đặt lại con trỏ văn bản vào cuối
            passwordET.setSelection(passwordET.text.length)
        }
        registerBtn.setOnClickListener {
            onClickRegisterEmailPassword()
        }
        registerDoctorBtn.setOnClickListener {
            val intent = Intent(this, RegisterDoctorPage::class.java)
            startActivity(intent)
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
                        auth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) { task2 ->
                            if (task2.isSuccessful) {
                                // create User
                                val key: String? = userDB.push().key
                                val user = BenhNhan(
                                    MaBenhNhan = key!!,
                                    HoTen = name,
                                    SoDienThoai = phone,
                                    Email = email,
                                    PassWord = password
                                )
                                // update User profile in database
                                userDB.child(role).child(key).setValue(user).addOnCompleteListener {
                                    if (task.isSuccessful) {
                                        // Register success
                                        Toast.makeText(applicationContext
                                            , "Đăng ký bệnh nhân thành công\nVui lòng chứng thực email"
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
                            }
                        }
                    } else {
                        if (password.length < 6) {
                            Toast.makeText(applicationContext
                                , "Mật khẩu phải có từ 6 kí tự trở lên"
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
                }
        }
    }

    private fun onClickRegisterEmailPasswordAdmin() {
        if (!editTextIsEmpty()) {
            // Get EditText input
            val name = nameET.text.toString()
            val email = emailET.text.toString()
            val phone = phoneET.text.toString()
            val password = passwordET.text.toString()
            val role : String = "Admin"

            // Init Firebase Authentication
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        auth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) { task2 ->
                            if (task2.isSuccessful) {
                                // create User
                                val key: String? = userDB.push().key
                                val user = Admin(
                                    Email = email,
                                )
                                // update User profile in database
                                userDB.child(role).child(key!!).setValue(user).addOnCompleteListener {
                                    if (task.isSuccessful) {
                                        // Register success
                                        Toast.makeText(applicationContext
                                            , "Đăng ký Admin thành công\nVui lòng chứng thực email"
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
                            }
                        }
                    } else {
                        if (password.length < 6) {
                            Toast.makeText(applicationContext
                                , "Mật khẩu phải có từ 6 kí tự trở lên"
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