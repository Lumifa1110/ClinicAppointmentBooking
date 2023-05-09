package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.hyv_hpv_clinicbooking.Activity.NotificationPage
import com.example.hyv_hpv_clinicbooking.R


class DoctorDashboard : Fragment() {

    private lateinit var notificationBTN : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidgets(view)
        initListeners()

    }

    private fun initWidgets(view: View) {
        notificationBTN = view.findViewById(R.id.notificationBTN)
    }

    private fun initListeners() {
        notificationBTN.setOnClickListener {
            val intent = Intent(context, NotificationPage::class.java)
            startActivity(intent)
        }
    }
}