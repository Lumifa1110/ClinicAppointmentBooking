package com.example.hyv_hpv_clinicbooking.Fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.ThongBao
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


class UserHomeFragment : Fragment() {

    private lateinit var notificationBTN : ImageView
    var ctx: Context?= null

    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth
    var maTaiKhoan:String?= null
    var hoTenTaiKhoan: String ?= ""

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

        val userDB = Firebase.database.getReference("Users")
        val query = userDB.child("BenhNhan")
            .orderByChild("email")
            .equalTo(auth.currentUser!!.email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    maTaiKhoan = it.key!!
                    val benhNhan = it.getValue(BacSi::class.java)
                    hoTenTaiKhoan = benhNhan?.HoTen ?: ""
                }
                initWidgets(view)
                initListeners()

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun initWidgets(view: View) {
        notificationBTN = view.findViewById(R.id.notificationBTN)
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
        var thongBaoList = view_dialog.findViewById<ListView>(R.id.thongBaoList)
        var _closeBTN: Button = view_dialog.findViewById(R.id.closeBTN)
        var _clearBTN: Button = view_dialog.findViewById(R.id.clearBTN)

        //Goi db de co list chuyenkhoa
        var notificationTextList = arrayListOf<String>()
        var keyList = arrayListOf<String>()
        var thongBaoDB = Firebase.database.getReference("ThongBao")

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
        val databaseRef = Firebase.database.getReference("ThongBao").child("BenhNhan")
        // Assuming "key" is the key of the node you want to delete
        databaseRef.child(key).removeValue().addOnSuccessListener {
        }.addOnFailureListener {
            // There was an error deleting the node
        }
    }

}