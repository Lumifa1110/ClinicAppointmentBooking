package com.example.hyv_hpv_clinicbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost

class test : AppCompatActivity() {
    var tabHost : TabHost? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        tabHost = this.findViewById(R.id.tabHost)
        tabHost?.setup()
        var tabSpec : TabHost.TabSpec? = null
        tabSpec = tabHost?.newTabSpec("unapprovedTab")
        tabSpec?.setIndicator("Chưa duyệt", null)
        tabSpec?.setContent(R.id.activity_unapproved_appointment_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("approvedTab")
        tabSpec?.setIndicator("Đã duyệt", null)
        tabSpec?.setContent(R.id.activity_approved_appointment_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("historyTab")
        tabSpec?.setIndicator("Lịch sử", null)
        tabSpec?.setContent(R.id.activity_history_appoinment_tab)
        tabHost?.addTab(tabSpec)
    }
}