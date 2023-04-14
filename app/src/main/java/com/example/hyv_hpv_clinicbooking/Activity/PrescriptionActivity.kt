package com.example.hyv_hpv_clinicbooking.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.PrescriptionAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

class PrescriptionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DonThuoc>()
    private lateinit var adapter: PrescriptionAdapter
    private var backBtn: ImageButton ?= null
    private var nameTV: TextView?= null
    private var dateTV: TextView?= null
    private var timeTV: TextView?= null
    private var chuandoanTV: TextView?= null
    private var loidanTV: TextView?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        recyclerView = findViewById(R.id.recyclerView)
        backBtn = findViewById(R.id.back_button)

        nameTV = findViewById(R.id.nameTV)
        dateTV = findViewById(R.id.dateTV)
        timeTV = findViewById(R.id.timeTV)
        chuandoanTV = findViewById(R.id.chuandoanTV)
        loidanTV = findViewById(R.id.loidanTV)


        val name = intent.getStringExtra("doctor")
        val appoinment = intent.getParcelableExtra<KeDon>("appoinment") as KeDon


        nameTV?.setText(name)
        dateTV?.setText(appoinment.Ngay)
        timeTV?.setText(appoinment.Gio)
        chuandoanTV?.setText(appoinment.ChuanDoan)
        loidanTV?.setText(appoinment.LoiDan)

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



@Serializable
data class DonThuoc (
    var TenThuoc: String ?= "",
    var SoLuong: Int ?= 0,
    var DonVi: String ?= "",
    var CachDung: String ?= "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(TenThuoc)
        parcel.writeInt(SoLuong!!)
        parcel.writeString(DonVi)
        parcel.writeString(CachDung)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KeDon> {
        override fun createFromParcel(parcel: Parcel): KeDon {
            return KeDon(parcel)
        }

        override fun newArray(size: Int): Array<KeDon?> {
            return arrayOfNulls(size)
        }
    }
}