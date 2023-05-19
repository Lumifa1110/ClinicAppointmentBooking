package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyv_hpv_clinicbooking.Fragment.AdminDashBoard
import com.example.hyv_hpv_clinicbooking.Fragment.DoctorManagement
import com.example.hyv_hpv_clinicbooking.Fragment.PatientManagement
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class AdminHomePage : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    var bottomNavBar: BottomNavigationView? = null
    var adminDashBoard = AdminDashBoard()
    var doctorManagement = DoctorManagement()
    var patientManagement = PatientManagement()
    private var selectedFragmentTag: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)

        auth = FirebaseAuth.getInstance()

        bottomNavBar = findViewById(R.id.adminNavBar) as BottomNavigationView
        supportFragmentManager.beginTransaction().replace(R.id.container, adminDashBoard).commit()
        selectedFragmentTag = intent.getStringExtra("loadfragment")
        when (selectedFragmentTag) {
            "dashboard" -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, adminDashBoard).commit()
                this.bottomNavBar!!.selectedItemId = R.id.adminHome
            }
            "doctorManagement" -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, doctorManagement).commit()
                this.bottomNavBar!!.selectedItemId = R.id.adminDoctors
            }
            "patientManagement" -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, patientManagement).commit()
                this.bottomNavBar!!.selectedItemId = R.id.adminPatients
            }
            else -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, adminDashBoard).commit()
                this.bottomNavBar!!.selectedItemId = R.id.adminHome
            }
        }
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
                    auth.signOut()
                    val intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}