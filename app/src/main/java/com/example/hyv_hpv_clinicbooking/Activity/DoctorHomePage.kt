package com.example.hyv_hpv_clinicbooking.Activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.hyv_hpv_clinicbooking.Fragment.*
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DoctorHomePage : AppCompatActivity() {
    var bottomNavBar: BottomNavigationView? = null
    var doctorHomeFrament = DoctorHomeFragment()
    var appoinmentManagementFragment = AppoinmentManagementFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_page)
        bottomNavBar = findViewById(R.id.doctorNavBar) as BottomNavigationView

        supportFragmentManager.beginTransaction().replace(R.id.container, doctorHomeFrament).commit()

        val doctor = intent.getStringExtra("fragment")
        if(doctor.equals("management_appoinment_list")) {
            supportFragmentManager.beginTransaction().replace(R.id.container, appoinmentManagementFragment).commit()
        }


        bottomNavBar!!.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.doctorHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, doctorHomeFrament).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.doctorAppoinment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, appoinmentManagementFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.doctorSchedule -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.doctorProfile -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}