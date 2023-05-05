package com.example.hyv_hpv_clinicbooking.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hyv_hpv_clinicbooking.R

class AdminMedicine : Fragment() {
    var addButton: ImageButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_admin_medicine, container, false)
        // Inflate the layout for this fragment

        return view
    }

}