package com.example.hyv_hpv_clinicbooking.Activity

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.hyv_hpv_clinicbooking.Fragment.*
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserHomePage : AppCompatActivity() {

    var bottomNavBar: BottomNavigationView? = null
    var userHomeFrament = UserHomeFragment()
    var doctorListFragment = DoctorListFragment()
    var historyAppoimentFragment = HistoryAppoimentFragment()
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
}