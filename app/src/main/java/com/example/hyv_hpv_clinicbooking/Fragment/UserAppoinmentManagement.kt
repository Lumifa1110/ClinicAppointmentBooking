package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.ContentValues.TAG
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
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserAppoinmentManagement.newInstance] factory method to
 * create an instance of this fragment.
 */
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
    private var appoinmentList = ArrayList<LichHenKham>()

    private var timeList = ArrayList<ThoiGian>()
    private var doctorList = ArrayList<BacSi>()
    private var prescriptionList = ArrayList<KeDon>()

    private var unapprovedList = ArrayList<LichHenKham>()
    private var approvedList = ArrayList<LichHenKham>()
    private var historyAppoinmentList = ArrayList<LichHenKham>()
    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_appoinment_management, container, false)
    }


    override fun onStart() {
        super.onStart()
        appoinmentList.clear()
        doctorList.clear()
        timeList.clear()
        prescriptionList.clear()

        readAppoinmentFromRealtimeDB(1)
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

        quantityTV2 = view.findViewById(R.id.quantity2)
        recyclerView2 = view.findViewById(R.id.recyclerView2)

        quantityTV3 = view.findViewById(R.id.quantity3)
        recyclerView3 = view.findViewById(R.id.recyclerView3)
    }

    private fun displayRecyclerView() {
        //tab1
        unapprovedList = appoinmentList.filter { it.MaTrangThai == 0 } as ArrayList<LichHenKham>
        quantityTV1?.setText(unapprovedList.size.toString())
        for (i in timeList) {
            Log.w("fsfsdf", i.GioBatDau + ", " + i.GioKetThuc)

        }
        adapter1 = HistoryAppoinmentAdapter(unapprovedList, timeList, doctorList)
        recyclerView1?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView1?.adapter = adapter1


        adapter1?.onItemClick = { index ->
            showAlertDialog(index, 0)
        }
        adapter1?.notifyDataSetChanged()

        // tab2
        approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<LichHenKham>
        quantityTV2?.setText(approvedList.size.toString())

        recyclerView2?.layoutManager = LinearLayoutManager(requireContext())

        adapter2 = HistoryAppoinmentAdapter(approvedList, timeList, doctorList)
        recyclerView2?.adapter = adapter2

        adapter2?.onItemClick = { index ->
            showAlertDialog(index, 1)
        }

//        Tab3
        historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<LichHenKham>
        quantityTV3?.setText(historyAppoinmentList.size.toString())

        recyclerView3?.layoutManager = LinearLayoutManager(requireContext())

        adapter3 = HistoryAppoinmentAdapter(historyAppoinmentList, timeList, doctorList)
        recyclerView3?.adapter = adapter3

        adapter3?.onItemClick = { index ->
            val intent = Intent(requireContext(), PrescriptionActivity::class.java)
            intent.putExtra("people", "patient")
            for(doctor in doctorList) {
                if(historyAppoinmentList[index].MaBacSi == doctor.MaBacSi) {
                    intent.putExtra("name", doctor.HoTen)
                }
            }
            for(prescription in prescriptionList) {
                if(historyAppoinmentList[index].MaBacSi == prescription.MaBacSi) {
                    intent.putExtra("prescription", prescription)
                }
            }
            startActivity(intent)
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
            appoinmentList.removeAt(index)
           if(type == 0) {
               unapprovedList.removeAt(index)
               adapter1?.notifyDataSetChanged()
           } else {
               approvedList.removeAt(index)
               adapter2?.notifyDataSetChanged()
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

    fun readAppoinmentFromRealtimeDB(userId: Int){
        database = Firebase.database.getReference("LichHenKham")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val lichHenKham = snapshot.getValue(LichHenKham::class.java)
                    if(lichHenKham!!.MaBenhNhan == userId) {
                        appoinmentList.add(lichHenKham!!)
                    }
                }

                // TODO: Do something with the lichHenKhamList
                readDoctorFromRealtimeDB()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
    fun readDoctorFromRealtimeDB() {
        database = Firebase.database.getReference("BacSi")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val doctor = snapshot.getValue(BacSi::class.java)
                    doctorList.add(doctor!!)
                }
                readTimeFromRealtimeDB()
                // TODO: Do something with the lichHenKhamList
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun readTimeFromRealtimeDB() {
        database = Firebase.database.getReference("ThoiGian")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val time = snapshot.getValue(ThoiGian::class.java)
                    timeList.add(time!!)

                }
                readPrecriptionFromRealtimeDB()
                // TODO: Do something with the lichHenKhamList

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun readPrecriptionFromRealtimeDB() {
        database = Firebase.database.getReference("KeDon")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val prescription = snapshot.getValue(KeDon::class.java)
                    prescriptionList.add(prescription!!)
                }
                displayRecyclerView()

                // TODO: Do something with the lichHenKhamList
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}