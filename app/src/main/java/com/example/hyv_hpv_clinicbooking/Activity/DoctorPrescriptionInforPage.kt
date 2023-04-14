package com.example.hyv_hpv_clinicbooking.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import com.example.hyv_hpv_clinicbooking.R

class DoctorPrescriptionInforPage : AppCompatActivity() {
    var name: EditText ?= null
    var quantity: EditText ?= null
    var donvi: EditText ?= null
    var using: EditText ?= null
    var deleteBtn: Button ?= null
    var saveBtn: Button ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_prescription_infor_page)

        name = findViewById(R.id.nameET)
        quantity = findViewById(R.id.quantityET)
        donvi = findViewById(R.id.donviET)
        deleteBtn = findViewById(R.id.deleteBtn)
        saveBtn = findViewById(R.id.saveBtn)
        using = findViewById(R.id.using)

        val selection = intent.getStringExtra("selection")
        val index = intent.getIntExtra("index", -1)
        if(selection.equals("edit_selection")) {
            val donThuoc = intent.getParcelableExtra<DonThuoc>("donThuoc")
            name?.setText(donThuoc?.TenThuoc)
            quantity?.setText(donThuoc?.SoLuong!!.toString())
            donvi?.setText(donThuoc?.DonVi)
            using?.setText(donThuoc?.CachDung)
        }
        else {
            deleteBtn?.isVisible = false
        }

        deleteBtn?.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("selection", "delete_selection")
            replyIntent.putExtra("index", index)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()

        }

        saveBtn?.setOnClickListener {

            val new_donThuoc = DonThuoc()
            new_donThuoc.TenThuoc = name?.text.toString()
            new_donThuoc.SoLuong = quantity?.text.toString().toInt()
            new_donThuoc.DonVi = donvi?.text.toString()
            new_donThuoc.CachDung = using?.text.toString()

            val replyIntent = Intent()
            if(selection.equals("edit_selection")) {
                replyIntent.putExtra("selection", "edit_selection")
                replyIntent.putExtra("index", index)
            }
            else {
                replyIntent.putExtra("selection", "save_selection")
            }
            replyIntent.putExtra("new_donThuoc", new_donThuoc)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

    }
}