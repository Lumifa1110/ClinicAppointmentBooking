package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.ApproveAppoinmentTab
import com.example.hyv_hpv_clinicbooking.Activity.DoctorPrescriptionPage
import com.example.hyv_hpv_clinicbooking.Activity.PrescriptionActivity
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AppoinmentManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppoinmentManagementFragment : Fragment() {
    var tabHost : TabHost? = null
    var recyclerView1: RecyclerView?= null
    var adapter1: DoctorAppoinmentList ?= null
    var quantityTV1: TextView ?= null
    var recyclerView2: RecyclerView?= null
    var adapter2: DoctorAppoinmentList ?= null
    var quantityTV2: TextView ?= null
    var recyclerView3: RecyclerView?= null
    var adapter3: DoctorAppoinmentList ?= null
    var quantityTV3: TextView ?= null

    private var appoinmentList = ArrayList<CuocHen>()
    private var patientList = ArrayList<BenhNhan>()

    private var unapprovedList = ArrayList<CuocHen>()
    private var approvedList = ArrayList<CuocHen>()
    private var historyAppoinmentList = ArrayList<CuocHen>()
    private lateinit var database : DatabaseReference

    private var emptyTV1: TextView ?= null
    private var emptyTV2: TextView ?= null
    private var emptyTV3: TextView ?= null

    var ctx: Context?= null

    private lateinit var auth  : FirebaseAuth
    var maTaiKhoan:String?= null

    val REQUEST_CODE= 1111

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appoinment_management, container, false)
    }

    override fun onStart() {
        super.onStart()
        database = Firebase.database.reference
        auth = FirebaseAuth.getInstance()
        getBenhNhanKey(auth.currentUser!!)
    }

    private fun displayView() {
        appoinmentList.clear()
        patientList.clear()
        readAppointmentFromRealtimeDB() {list1, list2->
            appoinmentList = list1.distinctBy { it.MaCuocHen } as ArrayList<CuocHen>
            patientList = list2

            val sortedAppointments = appoinmentList.sortedWith(compareBy(
                { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.Ngay) },
                { SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.GioBatDau) }
            ))
            appoinmentList = ArrayList(sortedAppointments)
//            appoinmentList.sortWith(compareBy({ it.Ngay }, { it.GioBatDau }))
            displayRecyclerView()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = view.context
        tabHost = view.findViewById(R.id.tabHost)
        tabHost?.setup()
        var tabSpec : TabHost.TabSpec? = null
        tabSpec = tabHost?.newTabSpec("tab1")
        tabSpec?.setIndicator("Chờ duyệt", null)
        tabSpec?.setContent(R.id.unapprove_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("tab2")
        tabSpec?.setIndicator("Kê đơn", null)
        tabSpec?.setContent(R.id.approve_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("tab3")
        tabSpec?.setIndicator("Lịch sử", null)
        tabSpec?.setContent(R.id.history_tab)
        tabHost?.addTab(tabSpec)

        quantityTV1 = view.findViewById(R.id.quantity1)
        recyclerView1 = view.findViewById(R.id.recyclerView1)
        emptyTV1 = view.findViewById(R.id.emptyTV1)

        quantityTV2 = view.findViewById(R.id.quantity2)
        recyclerView2 = view.findViewById(R.id.recyclerView2)
        emptyTV2 = view.findViewById(R.id.emptyTV2)

        quantityTV3 = view.findViewById(R.id.quantity3)
        recyclerView3 = view.findViewById(R.id.recyclerView3)
        emptyTV3 = view.findViewById(R.id.emptyTV3)
    }

    private fun displayRecyclerView() {
        unapprovedList.clear()
        approvedList.clear()
        historyAppoinmentList.clear()

        //tab1
        unapprovedList = appoinmentList.filter { it.MaTrangThai == 0 } as ArrayList<CuocHen>
        if (unapprovedList.size > 0) {
            emptyTV1?.visibility = View.GONE
            recyclerView1?.visibility = View.VISIBLE

            quantityTV1?.setText(unapprovedList.size.toString())

            adapter1 = DoctorAppoinmentList(unapprovedList, patientList)
            recyclerView1?.adapter = adapter1
            recyclerView1?.layoutManager = LinearLayoutManager(ctx)

            adapter1?.onItemClick = { index ->
                showUnapproveAlertDialog(index)
            }
        }
       else {
            quantityTV1?.setText("0")
            recyclerView1?.visibility = View.GONE
        }

        // tab2
        approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<CuocHen>
        if(approvedList.size > 0) {
            emptyTV2?.visibility = View.GONE
            recyclerView2?.visibility = View.VISIBLE

            quantityTV2?.setText(approvedList.size.toString())
            adapter2 = DoctorAppoinmentList(approvedList, patientList)
            recyclerView2?.adapter = adapter2
            recyclerView2?.layoutManager = LinearLayoutManager(ctx)

            adapter2?.onItemClick = { index ->
                showApproveAlertDialog(index)
            }
        }
        else {
            quantityTV2?.setText("0")
            recyclerView2?.visibility = View.GONE
        }

//         Tab3
        println(historyAppoinmentList.size.toString() + "historyAppoinmentList")

        historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<CuocHen>
        if(historyAppoinmentList.size > 0) {
            emptyTV3?.visibility = View.GONE
            recyclerView3?.visibility = View.VISIBLE

            quantityTV3?.setText(historyAppoinmentList.size.toString())

            adapter3 = DoctorAppoinmentList(historyAppoinmentList, patientList)
            recyclerView3?.adapter = adapter3
            recyclerView3?.layoutManager = LinearLayoutManager(ctx)

            adapter3?.onItemClick = { index ->
                val intent = Intent(ctx, PrescriptionActivity::class.java)
                intent.putExtra("people", "doctor")
                for(patient in patientList) {
                    if(historyAppoinmentList[index].MaBenhNhan == patient.MaBenhNhan) {
                        intent.putExtra("name", patientList[index].HoTen)
                    }
                }
                intent.putExtra("appoinment", historyAppoinmentList[index])
                startActivity(intent)
            }
        }
        else {
            quantityTV3?.setText("0")
            recyclerView3?.visibility = View.GONE
        }
    }
    private fun showUnapproveAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(ctx!!)
        alertDialog.setTitle("Duyệt cuộc hẹn")
        alertDialog.setMessage("Bạn có muốn duyệt cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            Toast.makeText(ctx, "Cuộc hẹn đã duyệt", Toast.LENGTH_LONG).show()
            appoinmentList.forEachIndexed { i, value ->
                if (value.MaCuocHen == unapprovedList[index].MaCuocHen) {
                    updateTrangThai(value.MaCuocHen, 1)
                    value.MaTrangThai = 1
                    displayView()
                }
            }
        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }

        alertDialog.setNeutralButton("Từ chối") { dialog, which ->
            appoinmentList.forEachIndexed { i, value ->
                if (value.MaCuocHen == unapprovedList[index].MaCuocHen) {
                    updateThoiGianRanhFromRealtimeDB(value.MaBacSi, value.Ngay, value.GioBatDau, value.GioKetThuc)
                    val databaseRef = FirebaseDatabase.getInstance().getReference("CuocHen")
                    // Assuming "key" is the key of the node you want to delete
                    databaseRef.child(value.MaCuocHen).removeValue().addOnSuccessListener {
                        // The node was successfully deleted
                        displayView()
                        Toast.makeText(ctx, "Cuộc hẹn đã xóa", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        // There was an error deleting the node
                    }
                }
            }
        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun showApproveAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(ctx!!)
        alertDialog.setTitle("Kê đơn thuốc")
        alertDialog.setMessage("Bạn có muốn kê đơn cho cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            val intent = Intent(ctx, DoctorPrescriptionPage::class.java)
            appoinmentList.forEachIndexed { i, value ->
                if (value.MaCuocHen == approvedList[index].MaCuocHen) {
                    Log.w(tag, "ABC" + approvedList[index].toString())
                    intent.putExtra("key_appoinment", value.MaCuocHen)
                }
            }

            patientList.forEach { item ->
                if (item.MaBenhNhan == approvedList[index].MaBenhNhan) {
                    intent.putExtra("patient_name", item.HoTen)
                }
            }
            intent.putExtra("appoinment", approvedList[index])
            startActivityForResult(intent, REQUEST_CODE)

        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun readAppointmentFromRealtimeDB(callback: (ArrayList<CuocHen>, ArrayList<BenhNhan>) -> Unit) {
        val appointmentList = ArrayList<CuocHen>()
        val patientList = ArrayList<BenhNhan>()
        val currentDate = Calendar.getInstance().apply {
            time = Date()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        database.child("CuocHen").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val appointment = snapshot.getValue(CuocHen::class.java)
                    if(appointment!!.MaBacSi.equals(maTaiKhoan)) {
                        if(appointment.Ngay == null || appointment.Ngay =="null" || appointment.GioBatDau == null || appointment.GioBatDau == "null" || appointment.GioKetThuc == null || appointment.GioKetThuc == "null") {
                            deleteAppoinmentFromRealtimeDB(appointment.MaCuocHen)
                        }
                        else {
                            if(appointment.MaTrangThai == 2) {
                                appointmentList.add(appointment!!)
                                readPatientFromRealtimeDB(appointment.MaBenhNhan) { patients ->
                                    patientList.addAll(patients)
                                    callback(appointmentList, patientList)
                                }
                            } else {
                                val myDate =
                                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(
                                        appointment!!.Ngay
                                    )
                                if (myDate.compareTo(currentDate) > 0) {
                                    // myDate lớn hơn ngày hiện tại
                                    appointmentList.add(appointment!!)
                                    readPatientFromRealtimeDB(appointment.MaBenhNhan) { patients ->
                                        patientList.addAll(patients)
                                        callback(appointmentList, patientList)
                                    }
                                } else if (myDate.compareTo(currentDate) == 0) {
                                    // myDate bằng ngày hiện tại
                                    appointmentList.add(appointment!!)
                                    readPatientFromRealtimeDB(appointment.MaBenhNhan) { patients ->
                                        patientList.addAll(patients)
                                        callback(appointmentList, patientList)
                                    }
                                } else {
                                    // myDate nhỏ hơn ngày hiện tại
                                    deleteAppoinmentFromRealtimeDB(appointment.MaCuocHen!!)
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun readPatientFromRealtimeDB(maBenhNhan: String, callback: (ArrayList<BenhNhan>) -> Unit) {
        val patientList = ArrayList<BenhNhan>()

        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        databaseRef.child("BenhNhan").orderByChild("maBenhNhan").equalTo(maBenhNhan)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val patient = snapshot.getValue(BenhNhan::class.java)
                        patientList.add(patient!!)
                    }
                    callback(patientList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
    }

    private fun updateTrangThai(key: String, maTrangThai: Int) {
        val childUpdates = HashMap<String, Any>()
        childUpdates["/CuocHen/$key/maTrangThai"] = maTrangThai
        database.updateChildren(childUpdates)
    }

    private fun updateKeDon(key: String, maTrangThai: Int, chuanDoan: String, donThuoc: String, loiDan: String) {
        val childUpdates = HashMap<String, Any>()
        childUpdates["/CuocHen/$key/maTrangThai"] = maTrangThai
        childUpdates["/CuocHen/$key/chuanDoan"] = chuanDoan
        childUpdates["/CuocHen/$key/donThuoc"] = donThuoc
        childUpdates["/CuocHen/$key/loiDan"] = loiDan

        database.updateChildren(childUpdates)
    }

    fun deleteAppoinmentFromRealtimeDB(key: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("CuocHen")
        // Assuming "key" is the key of the node you want to delete
        databaseRef.child(key).removeValue().addOnSuccessListener {
            // The node was successfully deleted
        }.addOnFailureListener {
            // There was an error deleting the node
        }
    }

    fun updateThoiGianRanhFromRealtimeDB(maBacSi: String, ngayThang: String, gioBatDau: String, gioKetThuc: String ) {
        val myRef = Firebase.database.getReference("ThoiGianRanh")

        myRef.orderByChild("maBacSi")
            .equalTo(maBacSi)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val key = snapshot.key
                        val thoiGianRanh = snapshot.getValue(ThoiGianRanh::class.java)
                        if (thoiGianRanh != null &&
                            thoiGianRanh.gioBatDau == gioBatDau &&
                            thoiGianRanh.gioKetThuc == gioKetThuc &&
                            thoiGianRanh.ngayThang == ngayThang
                        ) {
                            val childUpdates = java.util.HashMap<String, Any>()
                            childUpdates["/ThoiGianRanh/$key/trangThai"] = 1
                            database.updateChildren(childUpdates)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })

    }

    private fun getBenhNhanKey(user: FirebaseUser) {
        val userDB = Firebase.database.getReference("Users")

        val query = userDB.child("BenhNhan")
            .orderByChild("email")
            .equalTo(user.email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    maTaiKhoan = it.key!!
                }
                displayView()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val key_appoinment = data?.getStringExtra("key_appoinment")
                    val appoinment = data?.getParcelableExtra<CuocHen>("appoinment") as CuocHen
                    updateKeDon(key_appoinment!!, 2,  appoinment.ChuanDoan, appoinment.LoiDan, appoinment.DonThuoc)
                    displayView()
                    Toast.makeText(ctx, "Cuộc hẹn đã kê đơn", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}



