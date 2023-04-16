package com.example.hyv_hpv_clinicbooking.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.hyv_hpv_clinicbooking.Activity.AdminHomePage
import com.example.hyv_hpv_clinicbooking.R

class AdminDashBoard : Fragment() {
    var btnShowListDoctor: CardView? = null
    var btnShowListPatient: CardView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_dash_board, container, false)
        btnShowListDoctor = view.findViewById(R.id.listDoctors)
        btnShowListDoctor!!.setOnClickListener {
            val listDoctor = DoctorManagement()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, listDoctor).commit()
            (requireActivity() as AdminHomePage).bottomNavBar!!.selectedItemId = R.id.adminDoctors
        }
        btnShowListPatient = view.findViewById(R.id.listPatients)
        btnShowListPatient!!.setOnClickListener {
            val listPatient = PatientManagement()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, listPatient).commit()
            (requireActivity() as AdminHomePage).bottomNavBar!!.selectedItemId = R.id.adminPatients
        }
        return view
    }
}