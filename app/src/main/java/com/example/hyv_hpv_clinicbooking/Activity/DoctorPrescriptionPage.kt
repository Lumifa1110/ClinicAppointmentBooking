package com.example.hyv_hpv_clinicbooking.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Adapter.PrescriptionAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R

class DoctorPrescriptionPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DonThuoc>()
    private lateinit var adapter: PrescriptionAdapter
    private var backBtn: ImageButton ?= null
    private var addBtn: Button ?= null
    private var nameTV: TextView ?= null
    private var dateTV: TextView ?= null
    private var timeTV: TextView ?= null

    val REQUEST_CODE= 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_prescription_page)

        recyclerView = findViewById(R.id.recyclerView)
        backBtn = findViewById(R.id.back_button)
        addBtn = findViewById(R.id.addBtn)
        nameTV = findViewById(R.id.nameTV)
        dateTV = findViewById(R.id.dateTV)
        timeTV = findViewById(R.id.timeTV)

        val name = intent.getStringExtra("name")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")

        nameTV?.setText(name)
        dateTV?.setText(date)
        timeTV?.setText(time)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PrescriptionAdapter(mList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            val intent = Intent(this, DoctorPrescriptionInforPage::class.java)
            intent.putExtra("selection", "edit_selection")
            intent.putExtra("index", index)
            intent.putExtra("donThuoc", mList[index])
            startActivityForResult(intent, REQUEST_CODE)
        }

        addBtn?.setOnClickListener {
            val intent = Intent(this, DoctorPrescriptionInforPage::class.java)
            intent.putExtra("selection", "add_selection")
            startActivityForResult(intent, REQUEST_CODE)
        }
//        val prescriptions = appoinment.DonThuoc.split("\n").toTypedArray()
//        for(prescription in prescriptions) {
//            var item = prescription.split(";").toTypedArray()
//            var donthuoc = DonThuoc()
//            donthuoc.TenThuoc = item[0]
//            donthuoc.SoLuong = item[1].toInt()
//            donthuoc.DonVi = item[2]
//            donthuoc.CachDung = item[3]
//            mList.add(donthuoc)
//        }

        backBtn?.setOnClickListener {
            val intent = Intent(this, DoctorHomePage::class.java)
            intent.putExtra("fragment", "appoinment_management")
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val selection = data?.getStringExtra("selection") as String
                    if (selection == "save_selection") {
                        val donThuoc = data?.getParcelableExtra<DonThuoc>("new_donThuoc") as DonThuoc
                        mList.add(donThuoc)
                        adapter?.notifyDataSetChanged()
                    }
                    else if(selection == "edit_selection") {
                        val index = data?.getIntExtra("index", -1) as Int
                        val donThuoc = data?.getParcelableExtra<DonThuoc>("new_donThuoc") as DonThuoc
                        mList[index].TenThuoc = donThuoc.TenThuoc
                        mList[index].SoLuong = donThuoc.SoLuong
                        mList[index].DonVi = donThuoc.DonVi
                        mList[index].CachDung = donThuoc.CachDung

                        adapter?.notifyDataSetChanged()
                    }
                    else {
                        val index = data?.getIntExtra("index", -1) as Int
                        mList.removeAt(index)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}