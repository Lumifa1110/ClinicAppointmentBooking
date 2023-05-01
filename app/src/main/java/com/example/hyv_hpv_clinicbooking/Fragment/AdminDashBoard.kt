package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.hyv_hpv_clinicbooking.Activity.AdminHomePage
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.Thuoc
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminDashBoard : Fragment() {
    lateinit var doctorList: ArrayList<BacSi>
    lateinit var patientList: ArrayList<BenhNhan>
    private lateinit var database : DatabaseReference
//    private lateinit var auth  : FirebaseAuth
    var tabHost: TabHost? = null
    var addMedicine: ImageButton? = null
    var addSpecialize: ImageButton? = null
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

        tabSpec = tabHost!!.newTabSpec("approval")
        tabSpec.setContent(R.id.fragment_approval_list)
        tabSpec.setIndicator("Danh sách chờ", null)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec("medicine")
        tabSpec.setContent(R.id.fragment_admin_medicine)
        tabSpec.setIndicator("Nhà thuốc", null)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec("specialize")
        tabSpec.setContent(R.id.fragment_admin_specialize)
        tabSpec.setIndicator("Chuyên khoa", null)
        tabHost!!.addTab(tabSpec)

        addMedicine = view.findViewById(R.id.addMedicine)
        addMedicine!!.setOnClickListener {
            Toast.makeText(requireContext(), "Add Medicine", Toast.LENGTH_SHORT).show()
            showAddMedicineDialog()
        }

        addSpecialize = view.findViewById(R.id.addSpecialize)
        addSpecialize!!.setOnClickListener {
            showAddSpecializeDialog()
        }
        return view
    }
    private fun showAddMedicineDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
        val nameET = dialogLayout.findViewById<EditText>(R.id.nameMedicine)
        database = Firebase.database.getReference("Medicines")

        builder.setView(dialogLayout)
        builder.setPositiveButton("Thêm thuốc") { dialog, which ->
            val newType = nameET.text.toString().trim()
            val newMedicine = Thuoc(newType)
            if (newType.isNotEmpty()) {
                database.setValue(newType)
                Toast.makeText(requireContext(), "Loại thuốc mới: $newType", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Loại thuốc và mô tả không thể để trống", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Huỷ bỏ") { dialog, which ->
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun showAddSpecializeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_specialize, null)
        val nameET = dialogLayout.findViewById<EditText>(R.id.nameMedicine)
        database = Firebase.database.getReference("Specializes")

        builder.setView(dialogLayout)
        builder.setPositiveButton("Thêm chuyên khoa") { dialog, which ->
            val newType = nameET.text.toString().trim()
            if (newType.isNotEmpty()) {
                // Perform the action of adding the new type and description here
                database.setValue(newType)
                Toast.makeText(requireContext(), "Chuyên khoa mới: $newType", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tên chuyên khoa để trống", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Huỷ bỏ") { dialog, which ->
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}