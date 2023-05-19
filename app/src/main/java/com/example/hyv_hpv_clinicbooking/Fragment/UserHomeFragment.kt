package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailPage
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UserHomeFragment : Fragment() {

    private lateinit var userGreetingTV : TextView
    private lateinit var notificationBTN : FrameLayout
    private lateinit var notificationCounter : TextView
    private lateinit var upcomingAppointmentHeader : LinearLayout
    private lateinit var upcomingAppointmentRV : RecyclerView
    private lateinit var upcomingAppointmentEmptyTV: TextView
    private lateinit var bestDoctorRV : RecyclerView
    private lateinit var bestDoctorEmptyTV: TextView
    private lateinit var userGreeting2: TextView
    private lateinit var userAvatar: ImageView

    private lateinit var upcomingAppointmentAdapter: UpcomingAppointmentAdapter
    private lateinit var bestDoctorAdapter: BestDoctorAdapter

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
    private var bestDoctorList = ArrayList<BacSi>()
    var notificationTextList = arrayListOf<String>()
    var keyList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        upcomingAppointmentList.clear()
        bestDoctorList.clear()
        notificationTextList.clear()
        keyList.clear()
        val queryBenhNhanKey = userDB.child("BenhNhan")
            .orderByChild("email")
            .equalTo(auth.currentUser!!.email)
        queryBenhNhanKey.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    maTaiKhoan = it.key!!
                    val benhNhan = it.getValue(BenhNhan::class.java)
                    hoTenTaiKhoan = benhNhan?.HoTen ?: ""
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.reference;

                    userGreetingTV.text = "Chào ${hoTenTaiKhoan}, bạn cảm thấy thế nào?"

                    var ref: StorageReference = storageReference!!.child("BenhNhan/" + maTaiKhoan)

                    ref.downloadUrl
                        .addOnSuccessListener { uri ->
                            Picasso.get().load(uri).into(userAvatar);
                            Log.d("Test", " Success!")
                        }
                        .addOnFailureListener {
                            Log.d("Test", " Failed!")
                            userAvatar?.setImageResource(R.drawable.default_avatar)
                        }

                    //Goi db de co list chuyenkhoa

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
                                notificationCounter.visibility = View.GONE
                            }
                            else {
                                notificationCounter.visibility = View.VISIBLE
                                notificationCounter.text = notificationTextList.size.toString()
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle errors here
                        }
                    })

                    val queryCuocHen = cuochenDB.orderByChild("maBenhNhan").equalTo(maTaiKhoan)
                    queryCuocHen.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            upcomingAppointmentList.clear()
                            if (dataSnapshot.exists()) {
//                                upcomingAppointmentHeader.visibility = View.VISIBLE
                                upcomingAppointmentEmptyTV.visibility = View.GONE
                                upcomingAppointmentRV.visibility = View.VISIBLE
                                dataSnapshot.children.forEach { it ->
                                    val cuochen = it.getValue(CuocHen::class.java)
                                    val myDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(cuochen!!.Ngay)

                                    if (myDate.compareTo(currentDate) >= 0) {
                                        if (cuochen!!.MaTrangThai == 1) {
                                            val ngay = "Ngày: ${cuochen!!.Ngay}"
                                            val thoigian =
                                                "Thời gian: ${cuochen.GioBatDau} - ${cuochen.GioKetThuc}"
                                            val queryDoctorInfo = userDB.child("BacSi")
                                                .orderByChild("maBacSi")
                                                .equalTo(cuochen.MaBacSi)
                                            queryDoctorInfo.addListenerForSingleValueEvent(object :
                                                ValueEventListener {
                                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                    dataSnapshot.children.forEach { it ->
                                                        val bacsi = it.getValue(BacSi::class.java)
                                                        val hotenbacsi = "Bác sĩ ${bacsi!!.HoTen}"
                                                        val tenchuyenkhoa =
                                                            "Chuyên khoa: ${bacsi.TenChuyenKhoa}"
                                                        val upcomingAppointmentData =
                                                            UpcomingAppointmentData(
                                                                MaTaiKhoan = bacsi!!.MaBacSi,
                                                                HoTen = hotenbacsi,
                                                                TenChuyenKhoa = tenchuyenkhoa,
                                                                Ngay = ngay,
                                                                ThoiGian = thoigian
                                                            )
                                                        upcomingAppointmentList.add(
                                                            upcomingAppointmentData
                                                        )
                                                    }
                                                    displayUpcomingAppointmentList()
                                                }

                                                override fun onCancelled(databaseError: DatabaseError) {}
                                            })
                                        }
                                    }
                                    else {
                                        upcomingAppointmentEmptyTV.visibility = View.VISIBLE
                                        upcomingAppointmentRV.visibility = View.GONE
                                    }
                                }
                            }
                            else {
//                                upcomingAppointmentHeader.visibility = View.GONE
                                upcomingAppointmentEmptyTV.visibility = View.VISIBLE
                                upcomingAppointmentRV.visibility = View.GONE
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }

                initListeners()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        val queryBestDoctor = userDB.child("BacSi")
        queryBestDoctor.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    bestDoctorEmptyTV.visibility = View.GONE
                    dataSnapshot.children.forEach { it ->
                        val bacsi = it.getValue(BacSi::class.java)
                        bestDoctorList.add(bacsi!!)
                    }
                    displayBestDoctorList()
                }
                else {
                    bestDoctorEmptyTV.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
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
    }

    private fun displayUpcomingAppointmentList() {
        upcomingAppointmentAdapter = UpcomingAppointmentAdapter(upcomingAppointmentList, 0)
        upcomingAppointmentRV.layoutManager = LinearLayoutManager(context)

        upcomingAppointmentRV.adapter = upcomingAppointmentAdapter
    }

    private fun displayBestDoctorList() {
        bestDoctorList  = ArrayList<BacSi>(bestDoctorList.sortedByDescending { it.SLBenhNhan })
        bestDoctorAdapter = BestDoctorAdapter(bestDoctorList.subList(0, 5))
        bestDoctorRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        bestDoctorRV.adapter = bestDoctorAdapter
        bestDoctorAdapter?.onItemClick = { index ->
            val intent = Intent(ctx, DoctorDetailPage::class.java)
            intent.putExtra("doctor", bestDoctorList[index])
            startActivity(intent)
        }
    }

    private fun initWidgets(view: View) {
        userGreetingTV = view.findViewById(R.id.userGreeting2)
        notificationBTN = view.findViewById(R.id.notificationBTN)
        notificationCounter = view.findViewById(R.id.notificationCounter)
        upcomingAppointmentHeader = view.findViewById(R.id.upcomingAppointmentHeader)
        upcomingAppointmentRV = view.findViewById(R.id.upcomingAppointmentRV)
        upcomingAppointmentEmptyTV = view.findViewById(R.id.upcomingAppointmentEmptyTV)
        bestDoctorRV = view.findViewById(R.id.bestDoctorRV)
        bestDoctorEmptyTV = view.findViewById(R.id.bestDoctorEmptyTV)
        userGreeting2 = view.findViewById(R.id.userGreeting2)
        userAvatar = view.findViewById(R.id.userAvatar)
    }

    private fun initListeners() {
        userGreeting2.setText("Chào "+ hoTenTaiKhoan+ ", bạn thế nào?")
        notificationBTN.setOnClickListener {
            showDialogChuyenKhoa()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = Date()
        return dateFormat.format(currentDate)
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

        if(notificationTextList.size == 0) {
            empty.visibility = View.VISIBLE
            thongBaoList.visibility = View.GONE
            _clearBTN.visibility = View.GONE
        }
        else {
            empty.visibility = View.GONE
            thongBaoList.visibility = View.VISIBLE
            _clearBTN.visibility = View.VISIBLE


            // Xử lý cuộc hẹn sắp tới
            val arrayAdapter: ArrayAdapter<*>
            arrayAdapter = ArrayAdapter(requireContext()!!, android.R.layout.simple_list_item_1, notificationTextList)
            thongBaoList.adapter = arrayAdapter

            thongBaoList.setOnItemClickListener { parent, view, position, id ->
                deleteNotificationFromRealtimeDB(keyList[position])
                notificationTextList.removeAt(position)
                keyList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                if(notificationTextList.size == 0) {
                    notificationCounter.visibility = View.GONE
                    thongBaoList.visibility = View.GONE
                    empty.visibility = View.VISIBLE
                    _clearBTN.visibility = View.GONE
                } else {
                    notificationCounter.text = notificationTextList.size.toString()
                }
            }

            _clearBTN.setOnClickListener {
                for (key in keyList) {
                    deleteNotificationFromRealtimeDB(key)
                }
                notificationTextList.clear()
                notificationCounter.visibility = View.GONE
                thongBaoList.visibility = View.GONE
                empty.visibility = View.VISIBLE
                keyList.clear()
                arrayAdapter.notifyDataSetChanged()
                customDialog?.dismiss()
            }

        }

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