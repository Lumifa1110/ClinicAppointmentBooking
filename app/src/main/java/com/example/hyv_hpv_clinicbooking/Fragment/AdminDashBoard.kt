package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.hyv_hpv_clinicbooking.Activity.AdminHomePage
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class AdminDashBoard : Fragment() {
    var btnShowListDoctor: CardView? = null
    var btnShowListPatient: CardView? = null
    var numAllTV: TextView? = null
    var numDocTV: TextView? = null
    var numPatTV: TextView? = null
    lateinit var doctorList: ArrayList<BacSi>
    lateinit var patientList: ArrayList<BenhNhan>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_dash_board, container, false)
        var data = Data()
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
        doctorList = data.generateDoctorData()
        patientList = data.generatePatientData()
        val numofDoctor = doctorList.size
        val numofPatient = patientList.size
        val numofAll = numofDoctor + numofPatient
        numAllTV = view.findViewById(R.id.numOfAll)
        numDocTV = view.findViewById(R.id.numOfDoctor)
        numPatTV = view.findViewById(R.id.numOfPatient)
        numAllTV!!.setText(numofAll.toString())
        numDocTV!!.setText(numofDoctor.toString())
        numPatTV!!.setText(numofPatient.toString())
        return view
    }
}