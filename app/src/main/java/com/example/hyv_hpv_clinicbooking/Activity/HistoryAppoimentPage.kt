package com.example.hyv_hpv_clinicbooking.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.HistoryAppoinmentAdapter
import com.example.hyv_hpv_clinicbooking.Data

import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class HistoryAppoimentPage : AppCompatActivity() {
    private var doctorList = ArrayList<BacSi>()
    private var appoinments = ArrayList<KeDon>()


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAppoinmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_appoiment_page)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var data = Data()
        doctorList = data.generateDoctorData()
        appoinments = data.generateKeDonData()
        adapter = HistoryAppoinmentAdapter(doctorList, appoinments)
        recyclerView.adapter = adapter
    }





//    private fun generateAppoinmentListData(): ArrayList<AppoinmentList> {
//        var result = ArrayList<AppoinmentList>()
//        var appoinment: AppoinmentList = AppoinmentList()
//        var doctor: Doctor = Doctor()
//        for(i in appoinments) {
//            appoinment.MaDon = i.MaDon
//            appoinment.MaBacSi = i.MaBacSi
//            appoinment.MaBenhNhan = i.MaBenhNhan
//            appoinment.Ngay = i.NgayGio?.toLocalDate().toString()
//            val hour = i.NgayGio?.hour
//            val minute = i.NgayGio?.minute
//
//            appoinment.Thoigian = hour.toString() + " giờ "  +minute.toString() + " phút"
//            for(j in doctorList) {
//                if(i.MaBacSi == j.MaBacSi) {
//                    appoinment.HoTen = j.HoTen
//                    appoinment.TenChuyenKhoa = j.TenChuyenKhoa
//                    appoinment.Image = j.Image
//                    result.add(appoinment)
//                }
//            }
//        }
//        return result
//    }
}

//class AppoinmentList {
//    var MaDon: Int ?= 0
//    var Ngay: String ?= ""
//    var Thoigian: String ?= ""
//    var MaBacSi: Int ?= 0
//    var MaBenhNhan: Int ?= 0
//    var GhiChu: String ?= ""
//    var HoTen: String ?= ""
//    var Image:Int ?= null
//    var TenChuyenKhoa: String = ""
//}