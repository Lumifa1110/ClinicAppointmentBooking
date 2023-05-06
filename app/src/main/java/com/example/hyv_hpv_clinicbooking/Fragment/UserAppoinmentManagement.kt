package com.example.hyv_hpv_clinicbooking.Fragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorPrescriptionPage
import com.example.hyv_hpv_clinicbooking.Activity.PrescriptionActivity
import com.example.hyv_hpv_clinicbooking.Adapter.HistoryAppoinmentAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.*
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserAppoinmentManagement() : Fragment() {
    var tabHost : TabHost? = null
    var recyclerView1: RecyclerView?= null
    var adapter1: HistoryAppoinmentAdapter?= null
    var quantityTV1: TextView?= null
    var recyclerView2: RecyclerView?= null
    var adapter2: HistoryAppoinmentAdapter?= null
    var quantityTV2: TextView?= null
    var recyclerView3: RecyclerView?= null
    var adapter3: HistoryAppoinmentAdapter?= null
    var quantityTV3: TextView?= null
    private var appoinmentList = ArrayList<CuocHen>()

    private var timeList = ArrayList<ThoiGian>()
    private var doctorList = ArrayList<BacSi>()
    private var prescriptionList = ArrayList<KeDon>()

    private var unapprovedList = ArrayList<CuocHen>()
    private var approvedList = ArrayList<CuocHen>()
    private var historyAppoinmentList = ArrayList<CuocHen>()
    private lateinit var database : DatabaseReference

    private var emptyTV1: TextView ?= null
    private var emptyTV2: TextView ?= null
    private var emptyTV3: TextView ?= null
    private var context:Context ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_appoinment_management, container, false)
    }


    override fun onStart() {
        super.onStart()
        displayView()
    }

    private fun displayView() {
        database = Firebase.database.reference
        appoinmentList.clear()
        doctorList.clear()
        timeList.clear()
        prescriptionList.clear()
        readAppointmentFromRealtimeDB() {list1, list2 ->
            appoinmentList = list1
            doctorList = list2
            val sortedAppointments = appoinmentList.sortedWith(compareBy(
                { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.Ngay) },
                { SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.GioBatDau) }
            ))
            appoinmentList = ArrayList(sortedAppointments)
            displayRecyclerView()
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabHost = view.findViewById(R.id.tabHost)
        tabHost?.setup()
        var tabSpec : TabHost.TabSpec? = null
        tabSpec = tabHost?.newTabSpec("tab1")
        tabSpec?.setIndicator("Chờ duyệt", null)
        tabSpec?.setContent(R.id.unapprove_tab)
        tabHost?.addTab(tabSpec)
        tabSpec = tabHost?.newTabSpec("tab2")
        tabSpec?.setIndicator("Đã duyệt", null)
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
        if(unapprovedList.size > 0) {
            emptyTV1?.visibility = View.GONE
            recyclerView1?.visibility = View.VISIBLE
            quantityTV1?.setText(unapprovedList.size.toString())

            adapter1 = HistoryAppoinmentAdapter(unapprovedList, doctorList)
            recyclerView1?.adapter = adapter1
            recyclerView1?.layoutManager = LinearLayoutManager(context)

            adapter1?.onItemClick = { index ->
                showAlertDialog(index, 0)
            }
        }

        else {
            quantityTV1?.setText("0")
            emptyTV1?.visibility = View.VISIBLE
            recyclerView1?.visibility = View.GONE
        }
        // tab2
        approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<CuocHen>
        if(approvedList.size > 0) {
            emptyTV2?.visibility = View.GONE
            recyclerView2?.visibility = View.VISIBLE
            quantityTV2?.setText(approvedList.size.toString())

            adapter2 = HistoryAppoinmentAdapter(approvedList, doctorList)
            recyclerView2?.adapter = adapter2
            recyclerView2?.layoutManager = LinearLayoutManager(context)

            adapter2?.onItemClick = { index ->
                showAlertDialog(index, 1)
            }
        }
        else {
            quantityTV2?.setText("0")
            emptyTV2?.visibility = View.VISIBLE
            recyclerView2?.visibility = View.GONE
        }

//        Tab3
        historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<CuocHen>
        if(historyAppoinmentList.size > 0) {
            emptyTV3?.visibility = View.GONE
            recyclerView3?.visibility = View.VISIBLE
            quantityTV3?.setText(historyAppoinmentList.size.toString())

            recyclerView3?.layoutManager = LinearLayoutManager(context)
            adapter3 = HistoryAppoinmentAdapter(historyAppoinmentList, doctorList)
            recyclerView3?.adapter = adapter3

            adapter3?.onItemClick = { index ->
                val intent = Intent(requireContext(), PrescriptionActivity::class.java)
                intent.putExtra("people", "patient")
                for(doctor in doctorList) {
                    if(historyAppoinmentList[index].MaBacSi == doctor.MaBacSi) {
                        intent.putExtra("name", doctor.HoTen)
                    }
                }
                intent.putExtra("appoinment", historyAppoinmentList[index])
                startActivity(intent)
            }
        }
        else {
            quantityTV3?.setText("0")
            emptyTV3?.visibility = View.VISIBLE
            recyclerView3?.visibility = View.GONE
        }
    }

    private fun showAlertDialog(index: Int, type: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Hủy cuộc hẹn")
        alertDialog.setMessage("Bạn có muốn hủy cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            Toast.makeText(requireContext(), "Cuộc hẹn đã hủy", Toast.LENGTH_LONG).show()
            if(type == 0) {
                appoinmentList.forEachIndexed { i, value ->
                    if (value.MaCuocHen == unapprovedList[index].MaCuocHen) {
                        unapprovedList.removeAt(index)
                        adapter1?.notifyDataSetChanged()
                        quantityTV1?.setText(unapprovedList.size.toString())
                        updateThoiGianRanhFromRealtimeDB(value.MaCuocHen, value.MaBacSi, value.Ngay, value.GioBatDau, value.GioKetThuc)

                    }
                }
            } else {
                appoinmentList.forEachIndexed { i, value ->
                    if (value.MaCuocHen == approvedList[index].MaCuocHen) {
                        approvedList.removeAt(index)
                        adapter2?.notifyDataSetChanged()
                        quantityTV2?.setText(approvedList.size.toString())
                        updateThoiGianRanhFromRealtimeDB(value.MaCuocHen, value.MaBacSi, value.Ngay, value.GioBatDau, value.GioKetThuc)
                    }
                }
            }
        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun readAppointmentFromRealtimeDB(callback: (ArrayList<CuocHen>, ArrayList<BacSi>) -> Unit) {
        val appointmentList = ArrayList<CuocHen>()
        val doctorList = ArrayList<BacSi>()

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
                    if(appointment!!.MaBenhNhan.equals("-NUGq3NRrFTwUKz84O6P")) {
                        if(appointment.Ngay == null || appointment.Ngay =="null" || appointment.GioBatDau == null || appointment.GioBatDau == "null" || appointment.GioKetThuc == null || appointment.GioKetThuc == "null") {
                            deleteAppoinmentFromRealtimeDB(appointment.MaCuocHen)
                        }
                        else {
                            val myDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(appointment!!.Ngay)
                            if (myDate.compareTo(currentDate) > 0) {
                                // myDate lớn hơn ngày hiện tại
                                appointmentList.add(appointment!!)
                                readDoctorFromRealtimeDB(appointment.MaBacSi) { doctors ->
                                    doctorList.addAll(doctors)
                                    // Invoke the callback function with the retrieved data
                                    callback(appointmentList, doctorList)
                                }
                            } else if (myDate.compareTo(currentDate) == 0) {
                                // myDate bằng ngày hiện tại
                                appointmentList.add(appointment!!)
                                readDoctorFromRealtimeDB(appointment.MaBacSi) { doctors ->
                                    doctorList.addAll(doctors)
                                    // Invoke the callback function with the retrieved data
                                    callback(appointmentList, doctorList)
                                }
                            } else {
                                // myDate nhỏ hơn ngày hiện tại
                                deleteAppoinmentFromRealtimeDB(appointment.MaCuocHen)
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

    fun readDoctorFromRealtimeDB(maBacSi: String, callback: (ArrayList<BacSi>) -> Unit) {
        val doctorList = ArrayList<BacSi>()

        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        databaseRef.child("BacSi").orderByChild("maBacSi").equalTo(maBacSi)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val doctor = snapshot.getValue(BacSi::class.java)
                        doctorList.add(doctor!!)
                    }
                    callback(doctorList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
    }

    fun deleteAppoinmentFromRealtimeDB(key: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("CuocHen")
        // Assuming "key" is the key of the node you want to delete
        databaseRef.child(key).removeValue().addOnSuccessListener {
            Toast.makeText(requireContext(), "Cuộc hẹn đã xóa", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            // There was an error deleting the node
        }
    }

    fun updateThoiGianRanhFromRealtimeDB(maCuocHen:String, maBacSi: String, ngayThang: String, gioBatDau: String, gioKetThuc: String ) {
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
                            val childUpdates = HashMap<String, Any>()
                            childUpdates["/ThoiGianRanh/$key/duocDat"] = 0
                            database.updateChildren(childUpdates)
                        }
                    }
                    deleteAppoinmentFromRealtimeDB(maCuocHen)
                    displayView()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
    }
}