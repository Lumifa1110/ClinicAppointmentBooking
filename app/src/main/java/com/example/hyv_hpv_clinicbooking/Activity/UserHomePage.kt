package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.content.ContentValues
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.hyv_hpv_clinicbooking.Fragment.*
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserHomePage : AppCompatActivity() {
    var bottomNavBar: BottomNavigationView? = null
    var userHomeFrament = UserHomeFragment()
    var doctorListFragment = DoctorListFragment()
    var historyAppoimentFragment = UserAppoinmentManagement()
    var userProfileFragment = UserProfile()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home_page)

        bottomNavBar = findViewById(R.id.userNavBar) as BottomNavigationView

        supportFragmentManager.beginTransaction().replace(R.id.container, userHomeFrament).commit()

        val doctor = intent.getStringExtra("fragment")
        if(doctor.equals("doctor_list")) {
            supportFragmentManager.beginTransaction().replace(R.id.container, doctorListFragment).commit()
            bottomNavBar?.menu?.getItem(1)?.isChecked = true

        }

        if(doctor.equals("history_appoinment_list")) {
            supportFragmentManager.beginTransaction().replace(R.id.container, historyAppoimentFragment).commit()
            bottomNavBar?.menu?.getItem(2)?.isChecked = true
        }

        if(doctor.equals("profile")) {
            val function = intent.getStringExtra("function")
            if(function == "edit") {
                //Cap Nhat DB
            }
            supportFragmentManager.beginTransaction().replace(R.id.container, userProfileFragment).commit()
            bottomNavBar?.menu!!.getItem(3).isChecked = true
        }

        bottomNavBar!!.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.userHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, userHomeFrament).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.userSearch -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, doctorListFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.userHistory -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, historyAppoimentFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.userProfile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, userProfileFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

//    fun readAppoinmentFromRealtimeDB(userId: Int){
//        val database = Firebase.database.getReference("LichHenKham")
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val lichHenKham = snapshot.getValue(LichHenKham::class.java)
//                    if(lichHenKham!!.MaBenhNhan == userId) {
//                        appoinmentList.add(lichHenKham!!)
//                    }
//                }
//
//                // TODO: Do something with the lichHenKhamList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }
//    fun readDoctorFromRealtimeDB() {
//        database = Firebase.database.getReference("BacSi")
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val doctor = snapshot.getValue(BacSi::class.java)
//                    doctorList.add(doctor!!)
//                }
//
//                // TODO: Do something with the lichHenKhamList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }
//
//    fun readTimeFromRealtimeDB() {
//        database = Firebase.database.getReference("ThoiGian")
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val time = snapshot.getValue(ThoiGian::class.java)
//                    timeList.add(time!!)
//                }
//
//                // TODO: Do something with the lichHenKhamList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }
//
//    fun readPrecriptionFromRealtimeDB() {
//        database = Firebase.database.getReference("KeDon")
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val prescription = snapshot.getValue(KeDon::class.java)
//                    prescriptionList.add(prescription!!)
//                }
//
//                // TODO: Do something with the lichHenKhamList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }
//
//    fun readAllDoctorFromRealtimeDB() {
//        database = Firebase.database.getReference("BacSi")
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val doctor = snapshot.getValue(BacSi::class.java)
//                    mList.add(doctor!!)
//                }
//
//                // TODO: Do something with the lichHenKhamList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }
}