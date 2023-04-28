package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.app.Activity
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
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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

    private var appoinmentList = ArrayList<LichHenKham>()
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var prescriptionList = ArrayList<KeDon>()

    private var unapprovedList = ArrayList<LichHenKham>()
    private var approvedList = ArrayList<LichHenKham>()
    private var historyAppoinmentList = ArrayList<LichHenKham>()
    private lateinit var database : DatabaseReference
    private  var keyAppoinmentList =  ArrayList<String>()
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
        appoinmentList.clear()
        patientList.clear()
        timeList.clear()
        prescriptionList.clear()
        display()
    }

    private fun display() {
        readAppointmentFromRealtimeDB() {list0, list1, list2, list3, list4 ->
            keyAppoinmentList = list0
            appoinmentList = list1
            patientList = list2
            timeList = list3
            prescriptionList = list4
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
         tabSpec?.setIndicator("Kê đơn", null)
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
        unapprovedList = appoinmentList.filter { it.MaTrangThai == 0 } as ArrayList<LichHenKham>
        quantityTV1?.setText(unapprovedList.size.toString())

        recyclerView1?.layoutManager = LinearLayoutManager(requireContext())
        adapter1 = DoctorAppoinmentList(unapprovedList, timeList, patientList)
        recyclerView1?.adapter = adapter1

        adapter1?.onItemClick = { index ->
            showUnapproveAlertDialog(index)
        }

        // tab2
        approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<LichHenKham>
        quantityTV2?.setText(approvedList.size.toString())


        recyclerView2?.layoutManager = LinearLayoutManager(requireContext())

        adapter2 = DoctorAppoinmentList(approvedList, timeList, patientList)
        recyclerView2?.adapter = adapter2

        adapter2?.onItemClick = { index ->
            showApproveAlertDialog(index)
        }

//         Tab3
        historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<LichHenKham>
        quantityTV3?.setText(historyAppoinmentList.size.toString())

        recyclerView3?.layoutManager = LinearLayoutManager(requireContext())

        adapter3 = DoctorAppoinmentList(historyAppoinmentList, timeList, patientList)
        recyclerView3?.adapter = adapter3

        adapter3?.onItemClick = { index ->
            val intent = Intent(requireContext(), PrescriptionActivity::class.java)
            intent.putExtra("people", "doctor")
            for(patient in patientList) {
                if(historyAppoinmentList[index].MaBenhNhan == patient.MaBenhNhan) {
                    intent.putExtra("name", patientList[index].HoTen)
                }
            }
            for(prescription in prescriptionList) {
                if(historyAppoinmentList[index].MaBenhNhan == prescription.MaBenhNhan && historyAppoinmentList[index].MaBacSi == prescription.MaBacSi) {
                    intent.putExtra("prescription", prescription)
                }
            }
            startActivity(intent)
        }
    }
    private fun showUnapproveAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Duyệt cuộc hẹn")
        alertDialog.setMessage("Bạn có muốn duyệt cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            Toast.makeText(requireContext(), "Cuộc hẹn đã duyệt", Toast.LENGTH_LONG).show()
            appoinmentList.forEachIndexed { i, value ->
                if (value.MaLichHen == unapprovedList[index].MaLichHen) {
                    updateScheduleTrangThai(keyAppoinmentList[i], 1)
                    display()
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

    private fun showApproveAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Kê đơn thuốc")
        alertDialog.setMessage("Bạn có muốn kê đơn cho cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            val intent = Intent(requireContext(), DoctorPrescriptionPage::class.java)
            var new_prescription: KeDon = KeDon()
            new_prescription.MaDon = prescriptionList.size + 1
            appoinmentList.forEachIndexed { i, value ->
                if (value.MaLichHen == approvedList[index].MaLichHen) {
                    intent.putExtra("key_prescription", keyAppoinmentList[i])
                    new_prescription.MaBacSi = value.MaBacSi
                    new_prescription.MaBenhNhan = value.MaBenhNhan
                }
            }

            timeList.forEach { item ->
                if (item.MaThoiGian == approvedList[index].MaThoiGian) {
                    new_prescription.Ngay = item.Ngay
                    new_prescription.Gio = item.GioBatDau
                }
            }

            patientList.forEach { item ->
                if (item.MaBenhNhan == approvedList[index].MaBenhNhan) {
                    intent.putExtra("patient_name", item.HoTen)
                }
            }
            intent.putExtra("prescription", new_prescription)
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

    fun readAppointmentFromRealtimeDB(callback: (ArrayList<String>, ArrayList<LichHenKham>, ArrayList<BenhNhan>, ArrayList<ThoiGian>, ArrayList<KeDon>) -> Unit) {
        val appointmentList = ArrayList<LichHenKham>()
        val patientList = ArrayList<BenhNhan>()
        val timeList = ArrayList<ThoiGian>()
        val prescriptionList = ArrayList<KeDon>()

        database.child("LichHenKham").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val scheduleId = snapshot.key
                    keyAppoinmentList.add(scheduleId!!)
                    val appointment = snapshot.getValue(LichHenKham::class.java)

                    appointmentList.add(appointment!!)
                    readPatientFromRealtimeDB(appointment.MaBenhNhan) { patients ->
                        patientList.addAll(patients)
                        readTimeFromRealtimeDB(appointment.MaThoiGian) { times ->
                            timeList.addAll(times)
                            readPrescriptionFromRealtimeDB(appointment.MaBacSi) { prescriptions ->
                                prescriptionList.addAll(prescriptions)
                                // Invoke the callback function with the retrieved data
                                callback(keyAppoinmentList, appointmentList, patientList, timeList, prescriptionList)
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

    fun readPatientFromRealtimeDB(maBenhNhan: Int, callback: (ArrayList<BenhNhan>) -> Unit) {
        val patientList = ArrayList<BenhNhan>()

        database.child("BenhNhan").orderByChild("maBenhNhan").equalTo(maBenhNhan.toDouble())
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

    fun readTimeFromRealtimeDB(maThoiGian: Int, callback: (ArrayList<ThoiGian>) -> Unit) {
        val timeList = ArrayList<ThoiGian>()
        database.child("ThoiGian").orderByChild("maThoiGian").equalTo(maThoiGian.toDouble())
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val time = snapshot.getValue(ThoiGian::class.java)
                    timeList.add(time!!)
                }
                callback(timeList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun readPrescriptionFromRealtimeDB(maBacSi: Int, callback: (ArrayList<KeDon>) -> Unit) {
        val prescriptionList = ArrayList<KeDon>()
        database.child("KeDon").orderByChild("maBacSi").equalTo(maBacSi.toDouble())
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val prescription = snapshot.getValue(KeDon::class.java)
                    prescriptionList.add(prescription!!)
                }
                callback(prescriptionList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }


    private fun updateScheduleTrangThai(key: String, maTrangThai: Int) {
        Log.w("abcd", key)
        val childUpdates = HashMap<String, Any>()
        childUpdates["/LichHenKham/$key/maTrangThai"] = maTrangThai
        database.updateChildren(childUpdates)
    }

    private fun writeNewPrescription(prescriptionId: String, kedon: KeDon) {
        val kedon1 = KeDon(
            Ngay = kedon.Ngay,
            Gio = kedon.Gio,
            MaBacSi = kedon.MaBacSi,
            MaBenhNhan = kedon.MaBenhNhan,
            ChuanDoan = kedon.ChuanDoan ,
            DonThuoc = kedon.DonThuoc ,
            LoiDan = kedon.LoiDan ,
        )

        database.child("KeDon").child(prescriptionId).setValue(kedon1)
        Toast.makeText(requireContext()
            , "Add Schedule successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val prescription = data?.getParcelableExtra<KeDon>("prescription") as KeDon
                    val key_prescription = data?.getStringExtra("key_prescription")
                    updateScheduleTrangThai(key_prescription!!, 2)
                    val prescriptionId = database.push().key
                    writeNewPrescription(prescriptionId!!, prescription)
                    display()
                    Toast.makeText(requireContext(), "Cuộc hẹn đã kê đơn", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}



