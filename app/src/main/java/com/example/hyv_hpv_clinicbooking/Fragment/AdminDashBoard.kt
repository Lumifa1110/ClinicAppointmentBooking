package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.hyv_hpv_clinicbooking.Activity.AdminHomePage
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class AdminDashBoard : Fragment() {
    lateinit var doctorList: ArrayList<BacSi>
    lateinit var patientList: ArrayList<BenhNhan>
    var tabHost: TabHost? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_dash_board, container, false)
        tabHost = view.findViewById(R.id.tabAdmin)
        tabHost!!.setup()
        var tabSpec: TabHost.TabSpec? = null

        tabSpec = tabHost!!.newTabSpec("statistic")
        tabSpec.setContent(R.id.fragment_admin_statistic)
        tabSpec.setIndicator("Thống kê", null)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec("medicine")
        tabSpec.setContent(R.id.fragment_admin_medicine)
        tabSpec.setIndicator("Nhà thuốc", null)
        tabHost!!.addTab(tabSpec)
        return view
    }
}