package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R

class PrescriptionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DonThuoc>()
    private lateinit var adapter: PrescriptionActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        var data = Data()
//        mList = generateDonThuocData()
//        adapter = PrescriptionActivity(mList)
//        recyclerView.adapter = adapter

    }


}

class DonThuoc {
    val TenThuoc: String ?= ""
    val SoLuong: Int ?= 0
    val DonVi: String ?= ""
    val CachDung: String ?= ""
}