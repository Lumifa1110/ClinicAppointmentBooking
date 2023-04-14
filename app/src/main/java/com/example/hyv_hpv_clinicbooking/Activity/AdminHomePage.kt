package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyv_hpv_clinicbooking.Fragment.AdminDashBoard
import com.example.hyv_hpv_clinicbooking.Fragment.DoctorManagement
import com.example.hyv_hpv_clinicbooking.Fragment.PatientManagement
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminHomePage : AppCompatActivity() {
    var bottomNavBar: BottomNavigationView? = null
    var adminDashBoard = AdminDashBoard()
    var doctorManagement = DoctorManagement()
    var patientManagement = PatientManagement()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)
        bottomNavBar = findViewById(R.id.adminNavBar) as BottomNavigationView
        supportFragmentManager.beginTransaction().replace(R.id.container, adminDashBoard).commit()
        bottomNavBar!!.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.adminHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, adminDashBoard).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.adminDoctors -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, doctorManagement).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.adminPatients -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, patientManagement).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.adminUser -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}