package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.PrescriptionAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R

class PrescriptionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DonThuoc>()
    private lateinit var adapter: PrescriptionAdapter
    private var backBtn: ImageButton ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        recyclerView = findViewById(R.id.recyclerView)
        backBtn = findViewById(R.id.back_button)
        val doctor = intent.getParcelableExtra<BacSi>("doctor") as BacSi
        val appoinment = intent.getParcelableExtra<BacSi>("appoinment") as KeDon

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val prescriptions = appoinment.DonThuoc.split("\n").toTypedArray()
        for(prescription in prescriptions) {
            var item = prescription.split(";").toTypedArray()
            var donthuoc = DonThuoc()
            donthuoc.TenThuoc = item[0]
            donthuoc.SoLuong = item[1].toInt()
            donthuoc.DonVi = item[2]
            donthuoc.CachDung = item[3]
            mList.add(donthuoc)
        }
        adapter = PrescriptionAdapter(mList)
        recyclerView.adapter = adapter

        backBtn?.setOnClickListener {
            val intent = Intent(this, UserHomePage::class.java)
            intent.putExtra("fragment", "history_appoinment_list")
            startActivity(intent)
        }
    }
}

class DonThuoc {
    var TenThuoc: String ?= ""
    var SoLuong: Int ?= 0
    var DonVi: String ?= ""
    var CachDung: String ?= ""
}