package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.EditProfilePage
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter_Admin
import com.example.hyv_hpv_clinicbooking.Adapter.PatientListAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DoctorManagement : Fragment() {
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DoctorListAdapter_Admin
    lateinit var doctorList: ArrayList<BacSi>

    override fun onStart() {
        super.onStart()
        display()
    }
    private fun display() {
        readDoctorFromRealtimeDB()
        displayRecyclerView()
    }
    private fun displayRecyclerView() {
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        adapter = DoctorListAdapter_Admin(requireContext(), doctorList)
        recyclerView!!.adapter = adapter
        adapter.setOnItemClickListener(object: DoctorListAdapter_Admin.OnItemClickListener {
            override fun onBanClick(doctor: BacSi) {
                super.onBanClick(doctor)
                val builder = AlertDialog.Builder(requireContext())
                val message = if (doctor.BiKhoa) "Có chắc muốn mở khoá tài khoản này?" else "Có chắc muốn khoá tài khoản này?"
                builder.setTitle("Cảnh báo")
                    .setMessage(message)
                    .setPositiveButton("Xác nhận") { dialog, which ->
                        var newValue: Boolean = !doctor.BiKhoa
                        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
                            .child("BacSi")
                        val query = databaseRef.orderByChild("maBacSi").equalTo(doctor.MaBacSi)
                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    var key: String? = null
                                    for (data in snapshot.children) {
                                        key = data.key.toString()
                                    }
                                    databaseRef.child(key!!).child("biKhoa").setValue(newValue)
                                    adapter.notifyDataSetChanged()
                                } else {
                                    Toast.makeText(requireContext(), "Không tìm thấy tài khoản. Lỗi hiển thị", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                    .setNegativeButton("Huỷ") { dialog, which ->
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                val alert: AlertDialog = builder.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
            }
            override fun onDeleteClick(doctor: BacSi) {
                super.onDeleteClick(doctor)
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Cảnh báo")
                    .setMessage("Bạn có chắc chắn muốn xoá ?")
                    .setPositiveButton("Xác nhận") { dialog, which ->
                        // Xoá medicine trong Firebase
                        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
                            .child("BacSi")
                        val query = databaseRef.orderByChild("maBacSi").equalTo(doctor.MaBacSi)
                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (data in snapshot.children) {
                                    data.ref.removeValue()
                                }
                                adapter.notifyDataSetChanged()
                                Toast.makeText(requireContext(), "Đã xoá " + doctor.HoTen, Toast.LENGTH_SHORT).show()
                            }
                            override fun onCancelled(error: DatabaseError) {
                                // Handle error
                            }
                        })
                    }
                    .setNegativeButton("Huỷ") { dialog, which ->
                        dialog.dismiss()
                    }
                val alert: AlertDialog = builder.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.DoctorView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = Data()
        doctorList = data.generateDoctorData()
        adapter = DoctorListAdapter_Admin(requireContext(), doctorList)
        recyclerView.adapter = adapter

        searchView = view.findViewById(R.id.searchDoctor)
        searchView!!.queryHint = "Tìm kiếm bác sĩ"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text changes
                filter(newText)
                return true
            }
        })
        adapter.setOnItemClickListener(object: DoctorListAdapter_Admin.OnItemClickListener {
            override fun onDeleteClick(doctor: BacSi) {
                doctorList.remove(doctor)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun filter(text: String?) {
        val filteredlist: ArrayList<BacSi> = ArrayList()
        for (item in doctorList) {
            if (item.HoTen.toLowerCase().contains(text!!.toLowerCase())) {
                // if the item is matched we are adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are displaying a toast message as no data found.
            Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }
    fun readDoctorFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("BacSi")
        databaseRef.addValueEventListener( object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doctorList.clear()
                for (snapshot in dataSnapshot.children) {
                    val benhnhan = snapshot.getValue(BacSi::class.java)
                    doctorList.add(benhnhan!!)
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}