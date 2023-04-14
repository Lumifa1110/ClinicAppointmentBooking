package com.example.hyv_hpv_clinicbooking.Activity

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.hyv_hpv_clinicbooking.Fragment.*
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DoctorHomePage : AppCompatActivity() {
    var bottomNavBar: BottomNavigationView? = null
    var doctorHomeFrament = DoctorHomeFragment()
    var appoinmentManagementFragment = AppoinmentManagementFragment()
    var doctorArrangeDayFragment = DoctorChooseFreeTimeFragment()
    var doctorProfileFragment = DoctorProfile()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_page)
        bottomNavBar = findViewById(R.id.doctorNavBar) as BottomNavigationView


        supportFragmentManager.beginTransaction().replace(R.id.container, doctorHomeFrament).commit()

        val tab = intent.getStringExtra("fragment")
        if(tab.equals("profile")) {
            val function = intent.getStringExtra("function")
            if(function == "edit") {
                //Cap Nhat DB
            }
            supportFragmentManager.beginTransaction().replace(R.id.container, doctorProfileFragment).commit()
            bottomNavBar?.menu!!.get(3).isChecked = true
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
                    supportFragmentManager.beginTransaction().replace(R.id.container, doctorArrangeDayFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.doctorProfile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, doctorProfileFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}