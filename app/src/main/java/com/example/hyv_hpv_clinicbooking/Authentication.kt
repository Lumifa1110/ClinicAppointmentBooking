package com.example.hyv_hpv_clinicbooking

import BenhNhan
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.hyv_hpv_clinicbooking.Activity.LoginPage
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

public class Authentication
{
    private var database : DatabaseReference = Firebase.database.getReference("Users")
    private lateinit var auth  : FirebaseAuth

    fun registerEmailPassword(
        context: Activity,
        role: String,
        name: String,
        phone: String,
        email: String,
        password: String)
    {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    // create User
                    when (role) {
                        "BenhNhan" -> {
                            val user = BenhNhan(
                                HoTen = name,
                                SoDienThoai = phone,
                                Email = email,
                                PassWord = password
                            )
                            // Write User to database
                            database.child(role).child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                                if (task.isSuccessful) {
                                    // Register success
                                    println(R.string.toastRegisterSuccess)
                                }
                                else {
                                    // Register fail
                                    println(R.string.toastRegisterFail)
                                }
                            }
                        }
                        "BacSi" -> {
                            val user = BacSi(
                                HoTen = name,
                                SoDienThoai = phone,
                                Email = email,
                                PassWord = password
                            )
                            // Write User to database
                            database.child(role).child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                                if (task.isSuccessful) {
                                    // Register success
                                    println(R.string.toastRegisterSuccess)
                                }
                                else {
                                    // Register fail
                                    println(R.string.toastRegisterFail)
                                }
                            }
                        }
                    }
                } else {
                    // Register fail
                    println(R.string.toastRegisterFail)
                }
            }
    }
}