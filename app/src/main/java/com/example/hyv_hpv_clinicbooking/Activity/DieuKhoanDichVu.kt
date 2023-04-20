package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.hyv_hpv_clinicbooking.R

class DieuKhoanDichVu : AppCompatActivity() {
    var backBTN: ImageButton?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieu_khoan_dich_vu)

        backBTN = findViewById(R.id.backBTN)

        val loaiTaiKhoan = intent.getStringExtra("loaiTaiKhoan")
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
        }
    }
}