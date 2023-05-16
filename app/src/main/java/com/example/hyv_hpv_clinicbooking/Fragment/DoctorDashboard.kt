package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.UpcomingAppointmentAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.ThongBao
import com.example.hyv_hpv_clinicbooking.Model.UpcomingAppointmentData
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class DoctorDashboard : Fragment() {

    private lateinit var notificationBTN : FrameLayout
    private lateinit var notificationCounter : TextView
    private lateinit var upcomingAppointmentHeader : LinearLayout
    private lateinit var upcomingAppointmentRV : RecyclerView
    private lateinit var upcomingAppointmentEmptyTV: TextView
    private lateinit var userGreeting2: TextView
    private lateinit var userAvatar: ImageView

    private lateinit var upcomingAppointmentAdapter: UpcomingAppointmentAdapter

    var ctx: Context?= null

    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth
    private lateinit var userDB : DatabaseReference
    private lateinit var cuochenDB : DatabaseReference
    private lateinit var thongBaoDB : DatabaseReference
    //Khai báo firebase storage để lấy ảnh
    lateinit var storage: FirebaseStorage

    var storageReference: StorageReference? = null
    var maTaiKhoan:String?= null
    var hoTenTaiKhoan: String ?= ""

    private var upcomingAppointmentList = ArrayList<UpcomingAppointmentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_dashboard, container, false)
    }

    override fun onStart() {
        super.onStart()
        upcomingAppointmentList.clear()
        val queryBacSiKey = userDB.child("BacSi")
            .orderByChild("email")
            .equalTo(auth.currentUser!!.email)
        queryBacSiKey.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    maTaiKhoan = it.key!!
                    val bacsi = it.getValue(BacSi::class.java)
                    hoTenTaiKhoan = bacsi?.HoTen ?: ""
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.reference;

                    var ref: StorageReference = storageReference!!.child("BacSi/" + maTaiKhoan)

                    ref.downloadUrl
                        .addOnSuccessListener { uri ->
                            Picasso.get().load(uri).into(userAvatar);
                            Log.d("Test", " Success!")
                        }
                        .addOnFailureListener {
                            Log.d("Test", " Failed!")
                            userAvatar?.setImageResource(R.drawable.default_avatar)
                        }
                    val queryThongBaoCount = thongBaoDB.child("BacSi")
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
                    val queryCuocHen = cuochenDB.orderByChild("maBacSi").equalTo(maTaiKhoan)
                    queryCuocHen.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                upcomingAppointmentEmptyTV.visibility = View.GONE
                                dataSnapshot.children.forEach { it ->
                                    val currentDate = Calendar.getInstance().apply {
                                        time = Date()
                                        set(Calendar.HOUR_OF_DAY, 0)
                                        set(Calendar.MINUTE, 0)
                                        set(Calendar.SECOND, 0)
                                        set(Calendar.MILLISECOND, 0)
                                    }.time
                                    val cuochen = it.getValue(CuocHen::class.java)
                                    if(cuochen!!.MaTrangThai == 1) {
                                        val myDate =
                                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(cuochen!!.Ngay)
                                        if (myDate!!.compareTo(currentDate) == 0) {
                                            val ngay = "Ngày: ${cuochen.Ngay}"
                                            val thoigian =
                                                "Thời gian: ${cuochen.GioBatDau} - ${cuochen.GioKetThuc}"
                                            val queryPatientInfo = userDB.child("BenhNhan")
                                                .orderByChild("maBenhNhan")
                                                .equalTo(cuochen.MaBenhNhan)
                                            queryPatientInfo.addListenerForSingleValueEvent(object :
                                                ValueEventListener {
                                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                    dataSnapshot.children.forEach { it ->
                                                        val benhnhan = it.getValue(BenhNhan::class.java)
                                                        val hotenbenhnhan =
                                                            "Bệnh nhân ${benhnhan!!.HoTen}"
                                                        val sdt = "Số điện thoại: ${benhnhan!!.SoDienThoai}"
                                                        val upcomingAppointmentData =
                                                            UpcomingAppointmentData(
                                                                MaTaiKhoan = benhnhan!!.MaBenhNhan,
                                                                HoTen = hotenbenhnhan,
                                                                SoDienThoai = sdt,
                                                                Ngay = ngay,
                                                                ThoiGian = thoigian
                                                            )
                                                        upcomingAppointmentList.add(
                                                            upcomingAppointmentData
                                                        )
                                                    }
                                                    if (upcomingAppointmentList.isEmpty()) {
                                                        upcomingAppointmentEmptyTV.visibility = View.VISIBLE
                                                    }
                                                    displayUpcomingAppointmentList()
                                                }

                                                override fun onCancelled(databaseError: DatabaseError) {}
                                            })
                                        }
                                        else {
                                            upcomingAppointmentEmptyTV.visibility = View.VISIBLE
                                            upcomingAppointmentRV.visibility = View.GONE
                                        }
                                    }
                                }
                            }
                            else {
                                upcomingAppointmentEmptyTV.visibility = View.VISIBLE
//                                upcomingAppointmentHeader.visibility = View.GONE
                                upcomingAppointmentRV.visibility = View.GONE
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidgets(view)
        initListeners()
        ctx = view.context
        database = Firebase.database.reference
        auth = FirebaseAuth.getInstance()
        userDB = Firebase.database.getReference("Users")
        cuochenDB = Firebase.database.getReference("CuocHen")
        thongBaoDB = Firebase.database.getReference("ThongBao")
    }

    private fun displayUpcomingAppointmentList() {
        upcomingAppointmentAdapter = UpcomingAppointmentAdapter(upcomingAppointmentList, 1)
        upcomingAppointmentRV.layoutManager = LinearLayoutManager(context)

        upcomingAppointmentRV.adapter = upcomingAppointmentAdapter
    }

    private fun initWidgets(view: View) {
        notificationBTN = view.findViewById(R.id.notificationBTN)
        notificationCounter = view.findViewById(R.id.notificationCounter)
        upcomingAppointmentHeader = view.findViewById(R.id.upcomingAppointmentHeader)
        upcomingAppointmentRV = view.findViewById(R.id.upcomingAppointmentRV)
        upcomingAppointmentEmptyTV = view.findViewById(R.id.upcomingAppointmentEmptyTV)
        userGreeting2 = view.findViewById(R.id.userGreeting2)
        userAvatar = view.findViewById(R.id.userAvatar)
    }

    private fun initListeners() {
        userGreeting2.setText("Bác sĩ " + hoTenTaiKhoan)
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

        thongBaoDB.child("BacSi").orderByChild("maTaiKhoan").equalTo(maTaiKhoan).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val key = snapshot.key
                    val thongBao = snapshot.getValue(ThongBao::class.java)
                    val myDate =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(thongBao!!.Ngay)
                    if (myDate.compareTo(currentDate) > 0) {
                        val thongBao = snapshot.getValue(ThongBao::class.java)
                        val text = "Bệnh nhân ${thongBao!!.TenTaiKhoan} ${thongBao.NoiDung} \nKhung giờ: ${thongBao.GioBatDau} - ${thongBao.GioKetThuc}, ${thongBao.Ngay}"
                        notificationTextList.add(text)
                        keyList.add(key!!)
                    } else if (myDate.compareTo(currentDate) == 0) {
                        val thongBao = snapshot.getValue(ThongBao::class.java)
                        val text = "Bệnh nhân ${thongBao!!.TenTaiKhoan} ${thongBao.NoiDung} \nKhung giờ: ${thongBao.GioBatDau} - ${thongBao.GioKetThuc}, ${thongBao.Ngay}"
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
                    empty.visibility = View.GONE
                    thongBaoList.visibility = View.VISIBLE
                    _clearBTN.visibility = View.VISIBLE
                    val arrayAdapter: ArrayAdapter<*>
                    arrayAdapter = ArrayAdapter(ctx!!!!, android.R.layout.simple_list_item_1, notificationTextList)
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

        //Xu li nut close
        _closeBTN.setOnClickListener {
            customDialog?.dismiss()
        }

        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }

    fun deleteNotificationFromRealtimeDB(key: String) {
        val databaseRef = Firebase.database.getReference("ThongBao").child("BacSi")
        // Assuming "key" is the key of the node you want to delete
        databaseRef.child(key).removeValue().addOnSuccessListener {
        }.addOnFailureListener {
            // There was an error deleting the node
        }
    }
}