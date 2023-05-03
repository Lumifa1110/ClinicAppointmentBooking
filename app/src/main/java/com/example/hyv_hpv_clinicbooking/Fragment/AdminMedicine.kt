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
//    private fun showAddMedicineDialog() {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Thêm thuốc mới")
//
//        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
//        val nameET = dialogLayout.findViewById<EditText>(R.id.nameMedicine)
//        builder.setView(dialogLayout)
//
//        builder.setPositiveButton("Thêm thuốc") { dialog, which ->
//            val newType = nameET.text.toString().trim()
//            if (newType.isNotEmpty()) {
//                // Perform the action of adding the new type and description here
//                Toast.makeText(requireContext(), "Loại thuốc mới: $newType\nMô tả: ", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Loại thuốc và mô tả không thể để trống", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        builder.setNegativeButton("Huỷ bỏ") { dialog, which ->
//            dialog.dismiss()
//        }
//
//        val alert: AlertDialog = builder.create()
//        alert.setCanceledOnTouchOutside(false)
//        alert.show()
//    }
}