package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyv_hpv_clinicbooking.Fragment.DoctorListFragment
import com.example.hyv_hpv_clinicbooking.Fragment.HistoryAppoimentFragment
import com.example.hyv_hpv_clinicbooking.Fragment.UserHomeFragment
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserHomePage : AppCompatActivity() {

    var bottomNavBar: BottomNavigationView? = null
    var userHomeFrament = UserHomeFragment()
    var doctorListFragment = DoctorListFragment()
    var historyAppoimentFragment = HistoryAppoimentFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home_page)
        bottomNavBar = findViewById(R.id.userNavBar) as BottomNavigationView


        supportFragmentManager.beginTransaction().replace(R.id.container, userHomeFrament).commit()

        val doctor = intent.getStringExtra("fragment")
        if(doctor.equals("doctor_list")) {
            supportFragmentManager.beginTransaction().replace(R.id.container, doctorListFragment).commit()
            doctorListFragment.setHasOptionsMenu(true)
        }

        if(doctor.equals("history_appoinment_list")) {
            supportFragmentManager.beginTransaction().replace(R.id.container, historyAppoimentFragment).commit()
            historyAppoimentFragment.setHasOptionsMenu(true)
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
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }


    }
}