package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.hyv_hpv_clinicbooking.Fragment.FragmentUserHomeView
import com.example.hyv_hpv_clinicbooking.R

class UserHomePage : AppCompatActivity() {
    var cardHome: CardView? = null
    var cardUser: CardView? = null
    var cardSchedule: CardView? = null
    var cardTreatment: CardView? = null
    val homeUserFragment = FragmentUserHomeView()
    val doctorFragment = DoctorHomePage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home_page)
        cardHome = findViewById(R.id.cardNavHome)
        cardUser = findViewById(R.id.cardNavUser)
        supportFragmentManager.beginTransaction().replace(R.id.container, homeUserFragment).commit()
        cardHome!!.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, homeUserFragment).commit()
        }
        cardUser!!.setOnClickListener {
            Toast.makeText(this, "View User Information", Toast.LENGTH_SHORT).show()
        }
    }
}