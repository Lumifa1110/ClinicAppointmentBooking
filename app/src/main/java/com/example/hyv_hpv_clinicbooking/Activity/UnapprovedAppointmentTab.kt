package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Fragment.FireMissilesDialogFragment
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R

class UnapprovedAppointmentTab : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<LichHenKham>()
    private lateinit var adapter: DoctorAppoinmentList
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var newList = ArrayList<LichHenKham>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unapproved_appointment_tab)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        var data = Data()
        mList = data.generateScheduleData()
        newList = mList.filter { it.MaTrangThai == 0 } as ArrayList<LichHenKham>
        timeList = data.generateTimeData()
        patientList = data.generatePatientData()
        println("MA bac si day la :")

        adapter = DoctorAppoinmentList(newList, timeList, patientList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            showAlertDialog(index)
        }
    }

    private fun showAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Duyệt cuộc hẹn")
        alertDialog.setMessage("Bạn có muốn duyệt cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            Toast.makeText(this, "Cuộc hẹn đã duyệt", Toast.LENGTH_LONG).show()
            mList.forEach { item ->
                if (item.MaLichHen == newList[index].MaLichHen) {
                    item.MaTrangThai = 1
                }
            }
            newList.removeAt(index)
            adapter?.notifyDataSetChanged()

        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}