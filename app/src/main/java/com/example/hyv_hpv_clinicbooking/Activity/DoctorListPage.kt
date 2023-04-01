package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Model.Doctor
import com.example.hyv_hpv_clinicbooking.R
import java.util.*
import kotlin.collections.ArrayList

class DoctorListPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<Doctor>()
    private lateinit var adapter: DoctorListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_page)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mList = generateCompanyData()
        adapter = DoctorListAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {
        println(query)

        if (query != null) {
            val filteredList = ArrayList<Doctor>()
            for (i in mList) {
                if (i.HoTen!!.lowercase(Locale.ROOT)!!.contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun generateCompanyData(): ArrayList<Doctor> {
        var result = ArrayList<Doctor>()

        var doctor: Doctor = Doctor()
        doctor.MaBacSi = 1
        doctor.HoTen = "Lưu Minh Phát"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = Doctor()
        doctor.MaBacSi = 2
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.HoTen = "Pham Tran Minh Ngoc 1"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = Doctor()
        doctor.MaBacSi = 3
        doctor.HoTen = "Pham Tran Minh Ngoc 2"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor5
        result.add(doctor)

        doctor = Doctor()
        doctor.MaBacSi = 4
        doctor.HoTen = "Nga Anh VU"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = Doctor()
        doctor.MaBacSi = 5
        doctor.HoTen = "Nguyen Dinh Van"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor5

        result.add(doctor)
        return result
    }
}