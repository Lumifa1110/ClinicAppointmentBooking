package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class DoctorDetailKiemDuyet : AppCompatActivity() {
    var doctorCanDuyet:BacSi ?= null
    var maAccountCanDuyet: String ?= null

    var nameTV: TextView?= null
    var phoneTV: TextView?= null
    var addressTV: TextView?= null
    var soNamTrongNgheTV: TextView?=null
    var emailTV: TextView?= null
    var chuyenKhoaTV: TextView?= null
    var cccdTV: TextView?= null

    //cac nut can xu ly
    var backBTN: ImageButton?= null
    var duyetBTN: Button?= null
    var khongDuyetBTN: Button?= null

    //Xử lí db bên realm database
    private lateinit var database : DatabaseReference

    //firebase storage
    lateinit var storage: FirebaseStorage
    var storageReference: StorageReference? = null

    var CCCD_truoc:ImageView?= null
    var CCCD_sau:ImageView?= null
    var giay_phep:ImageView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_detail_kiem_duyet)

        doctorCanDuyet = intent.getParcelableExtra<BacSi>("doctor") as BacSi
        maAccountCanDuyet = doctorCanDuyet?.MaBacSi

        //gan cac bien thong tin ca nhan
        nameTV = findViewById(R.id.nameTV)
        phoneTV = findViewById(R.id.phoneTV)
        addressTV = findViewById(R.id.addressTV)
        soNamTrongNgheTV = findViewById(R.id.soNamTrongNgheTV)
        emailTV = findViewById(R.id.emailTV)
        chuyenKhoaTV = findViewById(R.id.chuyenKhoaTV)
        cccdTV = findViewById(R.id.cccdTV)
        //gan cac nut
        backBTN = findViewById(R.id.backBTN)
        duyetBTN = findViewById(R.id.duyetBtn)
        khongDuyetBTN = findViewById(R.id.khongDuyetBtn)

        //Set cac gia tri
        nameTV?.text = doctorCanDuyet?.HoTen
        phoneTV?.text = doctorCanDuyet?.SoDienThoai
        addressTV?.text = doctorCanDuyet?.DiaChi
        soNamTrongNgheTV?.text = doctorCanDuyet?.SoNamTrongNghe.toString()
        emailTV?.text = doctorCanDuyet?.Email
        chuyenKhoaTV?.text = doctorCanDuyet?.TenChuyenKhoa
        cccdTV?.text = doctorCanDuyet?.Cccd

        //lay anh cccd mat truoc
        storage = FirebaseStorage.getInstance();
        storageReference = storage.reference;
        val ref1: StorageReference =
            storageReference!!.child("BacSi/" + maAccountCanDuyet + "_CCCD_mat_truoc")
        ref1.downloadUrl.addOnSuccessListener { uri ->
            CCCD_truoc = findViewById(R.id.cccd_mat_truoc)
            Picasso.get().load(uri).into(CCCD_truoc);
        }.addOnFailureListener { Log.d("Test", " Failed!") }

        //lay anh cccd mat sau
        storage = FirebaseStorage.getInstance();
        storageReference = storage.reference;
        val ref2: StorageReference =
            storageReference!!.child("BacSi/" + maAccountCanDuyet + "_CCCD_mat_sau")
        ref2.downloadUrl.addOnSuccessListener { uri ->
            CCCD_sau = findViewById(R.id.cccd_mat_sau)
            Picasso.get().load(uri).into(CCCD_sau);
        }.addOnFailureListener { Log.d("Test", " Failed!") }

        //lay anh giay phep
        storage = FirebaseStorage.getInstance();
        storageReference = storage.reference;
        val ref3: StorageReference =
            storageReference!!.child("BacSi/" + maAccountCanDuyet + "_giay_phep")
        ref3.downloadUrl.addOnSuccessListener { uri ->
            giay_phep = findViewById(R.id.giay_phep)
            Picasso.get().load(uri).into(giay_phep);
        }.addOnFailureListener { Log.d("Test", " Failed!") }

        //xu li nut back
        backBTN?.setOnClickListener {
            val intent = Intent(this, AdminHomePage::class.java)
            intent.putExtra("loadfragment", "dashboard")
            startActivity(intent)
        }

        //xu li nut duyet
        duyetBTN?.setOnClickListener {
            database = Firebase.database.getReference("Users").child("BacSi")
            database.child(maAccountCanDuyet!!).setValue(doctorCanDuyet)
            Firebase.database.getReference("BacSiChoDuyet").child(maAccountCanDuyet!!).removeValue()

            val intent = Intent(this, AdminHomePage::class.java)
            intent.putExtra("loadfragment", "dashboard")
            startActivity(intent)
        }

        //xu li nut khong duyet
        khongDuyetBTN?.setOnClickListener {
            //xoa tai khoan bac si khoi db
            database = Firebase.database.getReference("BacSiChoDuyet")
            database.child(maAccountCanDuyet!!).removeValue()

            //xoa anh khoi firebase storage
            ref1.delete().addOnSuccessListener {
                // File deleted successfully
            }.addOnFailureListener {
                // Uh-oh, an error occurred!
            }
            ref2.delete().addOnSuccessListener {
                // File deleted successfully
            }.addOnFailureListener {
                // Uh-oh, an error occurred!
            }
            ref3.delete().addOnSuccessListener {
                // File deleted successfully
            }.addOnFailureListener {
                // Uh-oh, an error occurred!
            }

            val intent = Intent(this, AdminHomePage::class.java)
            intent.putExtra("loadfragment", "dashboard")
            startActivity(intent)
        }
    }
}