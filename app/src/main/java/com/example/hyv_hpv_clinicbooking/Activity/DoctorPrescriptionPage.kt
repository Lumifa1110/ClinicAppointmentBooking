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
    private var saveBtn: Button ?= null
    private var nameTV: TextView ?= null
    private var dateTV: TextView ?= null
    private var timeTV: TextView ?= null
    private var chuandoanTV: TextView ?= null
    private var loidanTV: TextView ?= null

    private var new_prescription: KeDon ?= null
    val REQUEST_CODE= 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_prescription_page)

        recyclerView = findViewById(R.id.recyclerView)
        backBtn = findViewById(R.id.back_button)
        addBtn = findViewById(R.id.addBtn)
        saveBtn = findViewById(R.id.saveBtn)
        nameTV = findViewById(R.id.nameTV)
        dateTV = findViewById(R.id.dateTV)
        timeTV = findViewById(R.id.timeTV)
        chuandoanTV = findViewById(R.id.chuandoan)
        loidanTV = findViewById(R.id.loidanTV)

        val key_prescription = intent.getStringExtra("key_prescription")
        new_prescription = intent.getParcelableExtra<KeDon>("prescription") as KeDon
        val name = intent.getStringExtra("name")


        nameTV?.setText(name)
        dateTV?.setText(new_prescription!!.Ngay)
        timeTV?.setText(new_prescription!!.Gio)

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

        saveBtn?.setOnClickListener {

            new_prescription!!.ChuanDoan = chuandoanTV!!.text.toString()
            new_prescription!!.LoiDan = loidanTV!!.text.toString()
            var donthuoc:String  = ""
            for(item in mList) {
                donthuoc +=  item.TenThuoc + ";" + item.SoLuong.toString() + ";" + item.DonVi + ";" + item.CachDung + "\n"
            }
            new_prescription!!.DonThuoc = donthuoc
            val replyIntent = Intent()
            replyIntent.putExtra("key_prescription", key_prescription)
            replyIntent.putExtra("prescription", new_prescription)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
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
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
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