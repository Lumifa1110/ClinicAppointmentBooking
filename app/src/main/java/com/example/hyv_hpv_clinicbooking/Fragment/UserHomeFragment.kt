package com.example.hyv_hpv_clinicbooking.Fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.BestDoctorAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.UpcomingAppointmentAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.ThongBao
import com.example.hyv_hpv_clinicbooking.Model.UpcomingAppointmentData
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UserHomeFragment : Fragment() {

    private lateinit var notificationBTN : FrameLayout
    private lateinit var notificationCounter : TextView
    private lateinit var upcomingAppointmentHeader : LinearLayout
    private lateinit var upcomingAppointmentRV : RecyclerView
    private lateinit var bestDoctorRV : RecyclerView

    private lateinit var upcomingAppointmentAdapter: UpcomingAppointmentAdapter
    private lateinit var bestDoctorAdapter: BestDoctorAdapter

    var ctx: Context?= null

    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth
    private lateinit var userDB : DatabaseReference
    private lateinit var cuochenDB : DatabaseReference
    private lateinit var thongBaoDB : DatabaseReference

    var maTaiKhoan:String?= null
    var hoTenTaiKhoan: String ?= ""

    private var upcomingAppointmentList = ArrayList<UpcomingAppointmentData>()
    private var bestDoctorList = ArrayList<BacSi>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctx = view.context
        database = Firebase.database.reference
        auth = FirebaseAuth.getInstance()
        userDB = Firebase.database.getReference("Users")
        cuochenDB = Firebase.database.getReference("CuocHen")
        thongBaoDB = Firebase.database.getReference("ThongBao")

        initWidgets(view)

        upcomingAppointmentList.clear()
        bestDoctorList.clear()

        val queryBenhNhanKey = userDB.child("BenhNhan")
            .orderByChild("email")
            .equalTo(auth.currentUser!!.email)
        queryBenhNhanKey.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    maTaiKhoan = it.key!!
                    val benhNhan = it.getValue(BacSi::class.java)
                    hoTenTaiKhoan = benhNhan?.HoTen ?: ""
                    val queryThongBaoCount = thongBaoDB.child("BenhNhan")
                        .orderByChild("maTaiKhoan")
                        .equalTo(maTaiKhoan)
                    queryThongBaoCount.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val thongBaoCount = snapshot.childrenCount
                            if (thongBaoCount == 0.toLong()) {
                                notificationCounter.visibility = View.GONE
                            }
                            else {
                                notificationCounter.visibility = View.VISIBLE
                                notificationCounter.text = thongBaoCount.toString()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                    val queryCuocHen = cuochenDB.orderByChild("maBenhNhan").equalTo(maTaiKhoan)
                    queryCuocHen.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                upcomingAppointmentHeader.visibility = View.VISIBLE
                                upcomingAppointmentRV.visibility = View.VISIBLE
                                dataSnapshot.children.forEach { it ->
                                    val cuochen = it.getValue(CuocHen::class.java)
                                    val ngay = "Ngày: ${cuochen!!.Ngay}"
                                    val thoigian = "Thời gian: ${cuochen.GioBatDau} - ${cuochen.GioKetThuc}"
                                    val queryDoctorInfo = userDB.child("BacSi")
                                        .orderByChild("maBacSi")
                                        .equalTo(cuochen.MaBacSi)
                                    queryDoctorInfo.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            dataSnapshot.children.forEach { it ->
                                                val bacsi = it.getValue(BacSi::class.java)
                                                val hotenbacsi = "Bác sĩ ${bacsi!!.HoTen}"
                                                val tenchuyenkhoa = "Chuyên khoa: ${bacsi.TenChuyenKhoa}"
                                                val upcomingAppointmentData = UpcomingAppointmentData(
                                                    HoTen = hotenbacsi,
                                                    TenChuyenKhoa = tenchuyenkhoa,
                                                    Ngay = ngay,
                                                    ThoiGian = thoigian
                                                )
                                                upcomingAppointmentList.add(upcomingAppointmentData)
                                            }
                                            displayUpcomingAppointmentList()
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {}
                                    })
                                }
                            }
                            else {
                                upcomingAppointmentHeader.visibility = View.GONE
                                upcomingAppointmentRV.visibility = View.GONE
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }
                initWidgets(view)
                initListeners()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        val queryBestDoctor = userDB.child("BacSi")
        queryBestDoctor.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    val bacsi = it.getValue(BacSi::class.java)
                    bestDoctorList.add(bacsi!!)
                }
                displayBestDoctorList()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun displayUpcomingAppointmentList() {
        upcomingAppointmentAdapter = UpcomingAppointmentAdapter(upcomingAppointmentList)
        upcomingAppointmentRV.layoutManager = LinearLayoutManager(context)

        upcomingAppointmentRV.adapter = upcomingAppointmentAdapter
    }

    private fun displayBestDoctorList() {
        bestDoctorAdapter = BestDoctorAdapter(bestDoctorList)
        bestDoctorRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        bestDoctorRV.adapter = bestDoctorAdapter
    }

    private fun initWidgets(view: View) {
        notificationBTN = view.findViewById(R.id.notificationBTN)
        notificationCounter = view.findViewById(R.id.notificationCounter)
        upcomingAppointmentHeader = view.findViewById(R.id.upcomingAppointmentHeader)
        upcomingAppointmentRV = view.findViewById(R.id.upcomingAppointmentRV)
        bestDoctorRV = view.findViewById(R.id.bestDoctorRV)
    }

    private fun initListeners() {
        notificationBTN.setOnClickListener {
            showDialogChuyenKhoa()
        }
    }


    fun showDialogChuyenKhoa() {
        var customDialog: AlertDialog?=null
        val builder = AlertDialog.Builder(requireContext())
        //Hiển thị dialog để chọn time
        var view_dialog: View =
            this.layoutInflater.inflate(R.layout.dialog_notification, null)

        //khai bao bien
        var empty:TextView = view_dialog.findViewById(R.id.empty)
        var thongBaoList = view_dialog.findViewById<ListView>(R.id.thongBaoList)
        var _closeBTN: Button = view_dialog.findViewById(R.id.closeBTN)
        var _clearBTN: Button = view_dialog.findViewById(R.id.clearBTN)

        //Goi db de co list chuyenkhoa
        var notificationTextList = arrayListOf<String>()
        var keyList = arrayListOf<String>()

        val currentDate = Calendar.getInstance().apply {
            time = Date()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        thongBaoDB.child("BenhNhan").orderByChild("maTaiKhoan").equalTo(maTaiKhoan).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val key = snapshot.key
                    val thongBao = snapshot.getValue(ThongBao::class.java)
                    val myDate =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(thongBao!!.Ngay)
                    if (myDate.compareTo(currentDate) > 0) {
                        val text = "Bác sĩ ${thongBao!!.TenTaiKhoan} ${thongBao.NoiDung} \nKhung giờ: ${thongBao.GioBatDau} - ${thongBao.GioKetThuc}, ${thongBao.Ngay}"
                        notificationTextList.add(text)
                        keyList.add(key!!)
                    } else if (myDate.compareTo(currentDate) == 0) {
                        val text = "Bác sĩ ${thongBao!!.TenTaiKhoan} ${thongBao.NoiDung} \nKhung giờ: ${thongBao.GioBatDau} - ${thongBao.GioKetThuc}, ${thongBao.Ngay}"
                        notificationTextList.add(text)
                        keyList.add(key!!)
                    } else {
                        deleteNotificationFromRealtimeDB(key!!)
                    }
                }

                if(notificationTextList.size == 0) {
                    empty.visibility = View.VISIBLE
                    thongBaoList.visibility = View.GONE
                    _clearBTN.visibility = View.GONE
                }
                else {
                    val arrayAdapter: ArrayAdapter<*>
                    arrayAdapter = ArrayAdapter(requireContext()!!, android.R.layout.simple_list_item_1, notificationTextList)
                    thongBaoList.adapter = arrayAdapter

                    thongBaoList.setOnItemClickListener { parent, view, position, id ->
                        deleteNotificationFromRealtimeDB(keyList[position])
                        notificationTextList.removeAt(position)
                        keyList.removeAt(position)
                        arrayAdapter.notifyDataSetChanged()
//                    customDialog?.dismiss()
                    }

                    _clearBTN.setOnClickListener {
                        for (key in keyList) {
                            deleteNotificationFromRealtimeDB(key)
                        }
                        notificationTextList.clear()
                        keyList.clear()
                        arrayAdapter.notifyDataSetChanged()
                        customDialog?.dismiss()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        // Xử lý cuộc hẹn sắp tới


        //Xu li nut close
        _closeBTN.setOnClickListener {
            customDialog?.dismiss()
        }

        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }

    fun deleteNotificationFromRealtimeDB(key: String) {
        val databaseRef = Firebase.database.getReference("ThongBao").child("BenhNhan")
        // Assuming "key" is the key of the node you want to delete
        databaseRef.child(key).removeValue().addOnSuccessListener {
        }.addOnFailureListener {
            // There was an error deleting the node
        }
    }

}