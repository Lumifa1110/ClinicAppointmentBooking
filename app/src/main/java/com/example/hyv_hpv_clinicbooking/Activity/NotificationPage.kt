package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.NotificationPageAdapter
import com.example.hyv_hpv_clinicbooking.Model.ThongBao
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotificationPage : AppCompatActivity() {

    // Firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var userDB : DatabaseReference
    private lateinit var thongbaoDB : DatabaseReference

    // Widgets
    private lateinit var notificationRV : RecyclerView
    private lateinit var returnBTN : ImageView

    // Data
    private val roles = arrayOf<String>("BenhNhan", "BacSi")
    private var currentUserKey : String? = null
    private var currentUserRole : String? = null
    private var notifications = ArrayList<ThongBao>()

    private lateinit var notificationAdapter : NotificationPageAdapter

    private fun initWidgets() {
        notificationRV = findViewById(R.id.notificationRV)
        returnBTN = findViewById(R.id.returnBTN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_page)

        initWidgets()
        initListeners()
        displayRecyclerView()

        auth = FirebaseAuth.getInstance()
        userDB = Firebase.database.getReference("Users")
        thongbaoDB = Firebase.database.getReference("ThongBao")

        getCurrentUserInfo(auth.currentUser!!)
    }

    private fun initListeners() {
        returnBTN.setOnClickListener {
            finish()
        }
    }

    private fun displayRecyclerView() {
        notificationAdapter = NotificationPageAdapter(notifications)
        notificationRV.adapter = notificationAdapter
    }

    private fun getCurrentUserInfo(user: FirebaseUser) {
        for (role in roles) {
            val query = userDB.child(role)
                .orderByChild("email")
                .equalTo(user.email)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach { it ->
                        currentUserKey = it.key
                        currentUserRole = role
                        Log.d("currentUserData", currentUserKey!!)
                        Log.d("currentUserData", currentUserRole!!)
                        getNotificationListFromDB(currentUserKey!!, currentUserRole!!)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }

    private fun getNotificationListFromDB(key: String, role: String) {
        notifications.clear()
        val query = thongbaoDB.orderByChild("ma$currentUserRole").equalTo(currentUserKey)
        thongbaoDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    val notification = it.getValue(ThongBao::class.java)
                    notifications.add(notification!!)
                }
                Log.d("getNotification", notifications.toString())
                displayRecyclerView()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}