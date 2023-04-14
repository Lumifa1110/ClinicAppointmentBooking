package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R

class ApprovedAppointmentTab : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<LichHenKham>()
    private lateinit var adapter: DoctorAppoinmentList
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var newList = ArrayList<LichHenKham>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approved_appointment_tab)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        var data = Data()
        mList = data.generateScheduleData()
        newList = mList.filter { it.MaTrangThai == 1 } as ArrayList<LichHenKham>
        timeList = data.generateTimeData()
        patientList = data.generatePatientData()
        println("MA bac si day la :")

        adapter = DoctorAppoinmentList(newList, timeList, patientList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            val intent = Intent(this, PrescriptionActivity::class.java)

            timeList.forEach { item ->
                if (item.MaThoiGian == newList[index].MaThoiGian) {
                    intent.putExtra("date", item.Ngay)
                    intent.putExtra("time", item.GioBatDau)
                }
            }

            patientList.forEach { item ->
                if (item.MaBenhNhan == newList[index].MaBenhNhan) {
                    intent.putExtra("patient_name", item.HoTen)
                }
            }
            startActivity(intent)
        }
    }
}