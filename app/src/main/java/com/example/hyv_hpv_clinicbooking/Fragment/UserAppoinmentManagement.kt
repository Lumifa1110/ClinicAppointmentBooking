package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.ContentValues
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
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    private var appoinmentList = ArrayList<CuocHen>()

    private var timeList = ArrayList<ThoiGian>()
    private var doctorList = ArrayList<BacSi>()
    private var prescriptionList = ArrayList<KeDon>()
    private  var keyAppoinmentList =  ArrayList<String>()

    private var unapprovedList = ArrayList<CuocHen>()
    private var approvedList = ArrayList<CuocHen>()
    private var historyAppoinmentList = ArrayList<CuocHen>()
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
        database = Firebase.database.reference

        appoinmentList.clear()
        doctorList.clear()
        timeList.clear()
        prescriptionList.clear()

        display()
    }

    private fun display() {
        readAppointmentFromRealtimeDB() {list1, list2, list3 ->
            keyAppoinmentList = list1
            appoinmentList = list2
            doctorList = list3
            displayRecyclerView()
        }

        val sortedAppointments = appoinmentList.sortedWith(compareBy(
            { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.Ngay) },
            { SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.GioBatDau) }
        ))
        appoinmentList = ArrayList(sortedAppointments)
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
        unapprovedList.clear()
        approvedList.clear()
        historyAppoinmentList.clear()

        //tab1
        unapprovedList = appoinmentList.filter { it.MaTrangThai == 0 } as ArrayList<CuocHen>
        quantityTV1?.setText(unapprovedList.size.toString())

        adapter1 = HistoryAppoinmentAdapter(unapprovedList, doctorList)
        recyclerView1?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView1?.adapter = adapter1


        adapter1?.onItemClick = { index ->
            showAlertDialog(index, 0)
        }
        adapter1?.notifyDataSetChanged()

        // tab2
        approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<CuocHen>
        quantityTV2?.setText(approvedList.size.toString())

        recyclerView2?.layoutManager = LinearLayoutManager(requireContext())

        adapter2 = HistoryAppoinmentAdapter(approvedList, doctorList)
        recyclerView2?.adapter = adapter2

        adapter2?.onItemClick = { index ->
            showAlertDialog(index, 1)
        }

//        Tab3
        historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<CuocHen>
        quantityTV3?.setText(historyAppoinmentList.size.toString())

        recyclerView3?.layoutManager = LinearLayoutManager(requireContext())

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
                        deleteAppoinmentFromRealtimeDB(keyAppoinmentList[i])
                    }
                }
            } else {
                appoinmentList.forEachIndexed { i, value ->
                    if (value.MaCuocHen == approvedList[index].MaCuocHen) {
                        deleteAppoinmentFromRealtimeDB(keyAppoinmentList[i])
                    }
                }
            }
            display()
        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun readAppointmentFromRealtimeDB(callback: (ArrayList<String>, ArrayList<CuocHen>, ArrayList<BacSi>) -> Unit) {
        val appointmentList = ArrayList<CuocHen>()
        val doctorList = ArrayList<BacSi>()
        val keyAppoinmentList = ArrayList<String>()

        database.child("CuocHen").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val scheduleId = snapshot.key
                    val appointment = snapshot.getValue(CuocHen::class.java)
                    if(appointment!!.MaBacSi.equals("-NUGq3NRrFTwUKz84O6P")) {
                        keyAppoinmentList.add(scheduleId!!)
                        appointmentList.add(appointment!!)
                        readDoctorFromRealtimeDB(appointment.MaBacSi) { doctors ->
                            doctorList.addAll(doctors)
                            // Invoke the callback function with the retrieved data
                            callback(keyAppoinmentList, appointmentList, doctorList)
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
        databaseRef.child("BacSi").orderByChild("maBacSi").equalTo(maBacSi.toDouble())
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
            // The node was successfully deleted
            Toast.makeText(requireContext(), "Cuộc hẹn đã xóa", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            // There was an error deleting the node
        }
    }
}