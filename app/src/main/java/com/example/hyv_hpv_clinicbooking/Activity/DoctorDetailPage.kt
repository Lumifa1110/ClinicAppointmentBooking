package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorDetailAdapter
import com.example.hyv_hpv_clinicbooking.Fragment.DoctorListFragment
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DoctorDetailPage : AppCompatActivity() {
    var adapter : DoctorDetailAdapter? = null
    var doctor: BacSi?= null

    var nameTV: TextView?= null
    var imageIV: ImageView?= null
    var specialistTV: TextView?= null
    var addressTV: TextView?= null
    var phoneTV: TextView?= null
    var backBtn: ImageButton ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_detail_page)

        val grid = findViewById<GridView>(R.id.doctorGridView)
        nameTV = findViewById(R.id.doctorName)
        imageIV = findViewById(R.id.doctorImage)
        specialistTV = findViewById(R.id.doctorSpecialist)
        addressTV = findViewById(R.id.doctorAddress)
        phoneTV = findViewById(R.id.doctorPhone)
        backBtn = findViewById(R.id.back_button)
        doctor = intent.getParcelableExtra<BacSi>("doctor") as BacSi

//        doctor = BacSi()
//        doctor?.HoTen = "Yogesh Batra"
//        doctor?.Image = R.drawable.doctor1
//        doctor?.TenChuyenKhoa = "Nha sĩ"
//        doctor?.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
//        doctor?.SoDienThoai = "0123456789"
//        doctor?.SLBenhNhan = 1234
//        doctor?.SoNamTrongNghe = 4
//        doctor?.SoCuocHen = 100

        nameTV?.setText("Bác sĩ " + doctor?.HoTen)
        imageIV?.setImageResource(doctor?.Image!!)
        specialistTV?.setText("Chuyên khoa: " + doctor?.TenChuyenKhoa)
        addressTV?.setText("Địa chỉ: "+ doctor?.DiaChi)
        phoneTV?.setText("Liên hệ: " + doctor?.SoDienThoai)

        var ìnforList = generateInforData() //implemened below
        adapter = DoctorDetailAdapter(this, ìnforList)
        grid.adapter = adapter
        grid.setOnItemClickListener { adapterView, view, i, l ->
            view.isEnabled = false
        }

        backBtn?.setOnClickListener {
            val intent = Intent(this, UserHomePage::class.java)
            intent.putExtra("fragment", "doctor_list")
            startActivity(intent)
        }
    }



    private fun generateInforData(): ArrayList<Infor> {
        var result = ArrayList<Infor>()
        var infor: Infor = Infor()
        infor.title = "Bệnh nhân"
        infor.amount = doctor?.SLBenhNhan.toString()
        infor.icon = R.drawable.patient
        result.add(infor)

        infor = Infor()
        infor.title = "Kinh nghiệm"
        infor.amount = doctor?.SoNamTrongNghe.toString()
        infor.icon = R.drawable.year
        result.add(infor)

        infor = Infor()
        infor.title = "Lịch hẹn"
        infor.amount = doctor?.SoCuocHen.toString()
        infor.icon = R.drawable.appointment
        result.add(infor)
        return result
    }
}


class Infor {
    var title: String = ""
    var amount: String = "0"
    var icon: Int ?= null
}