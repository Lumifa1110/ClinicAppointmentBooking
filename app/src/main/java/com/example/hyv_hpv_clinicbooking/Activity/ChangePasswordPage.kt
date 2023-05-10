package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordPage : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var oldPasswordET: EditText
    private lateinit var newPasswordET: EditText
    private lateinit var confirmPasswordET: EditText
    private lateinit var changePasswordBTN: Button
    private lateinit var backBTN: ImageButton

    private lateinit var loaiTaiKhoan: String

    private fun initWidgets() {
        oldPasswordET = findViewById(R.id.oldPasswordET)
        newPasswordET = findViewById(R.id.newPasswordET)
        confirmPasswordET = findViewById(R.id.confirmPasswordET)
        backBTN = findViewById(R.id.backBTN)
        changePasswordBTN = findViewById(R.id.changePWBTN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password_page)

        auth = FirebaseAuth.getInstance()

        initWidgets()
        initListeners()

        loaiTaiKhoan = intent.getStringExtra("loaiTaiKhoan")!!
    }

    private fun initListeners() {
        changePasswordBTN.setOnClickListener {
            val oldPassword = oldPasswordET.text.toString()
            val newPassword = newPasswordET.text.toString()
            val confirmPassword = confirmPasswordET.text.toString()

            if (oldPassword == ""
                || newPassword == ""
                || confirmPassword == "") {
                Toast.makeText(applicationContext
                    , getString(R.string.toastEmptyEditText)
                    , Toast.LENGTH_SHORT)
                    .show()
            }
            else if (newPassword != confirmPassword) {
                Toast.makeText(applicationContext
                    , "Mật khẩu nhập lại không khớp"
                    , Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                val currentUser = auth.currentUser;
                val credential = EmailAuthProvider.getCredential(
                    currentUser!!.email!!,
                    oldPassword
                )
                // Kiểm tra mật khẩu hiện tại
                currentUser.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Cập nhật mật khẩu mới
                        currentUser.updatePassword(newPassword).addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Toast.makeText(applicationContext
                                    , "Thay đổi mật khẩu thành công"
                                    , Toast.LENGTH_SHORT)
                                    .show()
                                // Quay về homepage
                                finish()
                            }
                            else {
                                Toast.makeText(applicationContext
                                    , "Thay đổi mật khẩu thất bại"
                                    , Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(applicationContext
                            , "Nhập mật khẩu hiện tại không đúng"
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        backBTN.setOnClickListener {
            if(loaiTaiKhoan == "BacSi") {
                val intent = Intent(this, DoctorHomePage::class.java)
                intent.putExtra("fragment", "profile")
                startActivity(intent)
            }

            if(loaiTaiKhoan == "BenhNhan") {
                val intent = Intent(this, UserHomePage::class.java)
                intent.putExtra("fragment", "profile")
                startActivity(intent)
            }
        }
    }

    private fun changePassword() {

    }
}