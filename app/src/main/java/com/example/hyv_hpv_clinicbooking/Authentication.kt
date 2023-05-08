package com.example.hyv_hpv_clinicbooking

import BenhNhan
import android.app.Activity
import android.util.Log
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthenticationHelper
{
    private var userDB : DatabaseReference = Firebase.database.getReference("Users")
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
                            userDB.child(role).child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
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
                            userDB.child(role).child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
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