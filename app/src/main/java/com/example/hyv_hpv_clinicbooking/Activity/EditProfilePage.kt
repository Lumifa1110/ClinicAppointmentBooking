package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class EditProfilePage : AppCompatActivity() {
    var backBTN: ImageButton?= null
    var saveBTN: Button?= null
    var nameET: EditText?= null
    var phoneET: EditText?= null
    var addressET: EditText?= null
    var addressTV: TextView?= null
    var emailET: EditText?= null

    var taiKhoanBS:BacSi?= null
    var taiKhoanBN:BenhNhan?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)

        backBTN = findViewById(R.id.backBTN)
        saveBTN = findViewById(R.id.saveBTN)

        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        addressET = findViewById(R.id.addressET)
        emailET = findViewById(R.id.emailET)

        addressTV = findViewById(R.id.addressTV)

        val loaiTaiKhoan = intent.getStringExtra("loaiTaiKhoan")
        if(loaiTaiKhoan == "BacSi") {
            taiKhoanBS = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBS?.HoTen)
            phoneET?.setText(taiKhoanBS?.SoDienThoai)
            addressET?.setText(taiKhoanBS?.DiaChi)
            emailET?.setText(taiKhoanBS?.Email)
        }

        if(loaiTaiKhoan == "BenhNhan") {
            taiKhoanBN = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBN?.HoTen)
            phoneET?.setText(taiKhoanBN?.SoDienThoai)
            addressET?.setVisibility(View.GONE)
            addressTV?.setVisibility(View.GONE)
            emailET?.setText(taiKhoanBN?.Email)
        }

        if (loaiTaiKhoan == "adminBacSi") {
            taiKhoanBS = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBS?.HoTen)
            phoneET?.setText(taiKhoanBS?.SoDienThoai)
            addressET?.setText(taiKhoanBS?.DiaChi)
            emailET?.setText(taiKhoanBS?.Email)
        }


        backBTN?.setOnClickListener {
            if(loaiTaiKhoan == "BacSi") {
                val intent = Intent(this, DoctorHomePage::class.java)
                intent.putExtra("fragment", "profile")
                startActivity(intent)
            }

            if(loaiTaiKhoan == "BenhNhan") {
                val intent = Intent(this, UserHomePage::class.java)
                intent.putExtra("fragment", "profile")
                startActivity(intent)
            }

            if(loaiTaiKhoan == "adminBacSi") {
                val intent = Intent(this, AdminHomePage::class.java)
                intent.putExtra("loadfragment", "doctorManagement")
                startActivity(intent)
            }
        }
        saveBTN?.setOnClickListener {
            taiKhoanBS?.HoTen = nameET?.text.toString()
            taiKhoanBS?.SoDienThoai = phoneET?.text.toString()
            taiKhoanBS?.DiaChi = addressET?.text.toString()
            taiKhoanBS?.Email = emailET?.text.toString()

            val intent = Intent(this, DoctorHomePage::class.java)
            intent.putExtra("fragment", "profile")
            intent.putExtra("function", "edit")
            intent.putExtra("taikhoanmoi", taiKhoanBS)
            startActivity(intent)
        }
    }
}