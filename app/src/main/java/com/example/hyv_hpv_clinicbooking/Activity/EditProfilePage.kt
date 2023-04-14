package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.hyv_hpv_clinicbooking.Fragment.DoctorProfile
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class EditProfilePage : AppCompatActivity() {
    var backBTN: ImageButton?= null
    var saveBTN: Button?= null
    var nameET: EditText?= null
    var phoneET: EditText?= null
    var addressET: EditText?= null
    var emailET: EditText?= null

    var taiKhoanBS:BacSi?= null
    var taiKhoanND:BacSi?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)

        backBTN = findViewById(R.id.backBTN)
        saveBTN = findViewById(R.id.saveBTN)

        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        addressET = findViewById(R.id.addressET)
        emailET = findViewById(R.id.emailET)

        val loaiTaiKhoan = intent.getStringExtra("loaiTaiKhoan")
        if(loaiTaiKhoan == "BacSi") {
            taiKhoanBS = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBS?.HoTen)
            phoneET?.setText(taiKhoanBS?.SoDienThoai)
            addressET?.setText(taiKhoanBS?.DiaChi)
            emailET?.setText(taiKhoanBS?.Email)
        }


        backBTN?.setOnClickListener {
            val intent = Intent(this, DoctorHomePage::class.java)
            intent.putExtra("fragment", "profile")
            startActivity(intent)
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