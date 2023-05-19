package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.PatientListAdapter
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PatientManagement : Fragment() {
    lateinit var searchView: SearchView
    private var recyclerView: RecyclerView? = null
    lateinit var adapter: PatientListAdapter
    private var patientList = ArrayList<BenhNhan>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        display()
    }
    private fun display() {
        readPatientFromRealtimeDB()
        displayRecyclerView()
    }
    private fun displayRecyclerView() {
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        adapter = PatientListAdapter(requireContext(), patientList)
        recyclerView!!.adapter = adapter
        adapter.setOnItemClickListener(object: PatientListAdapter.OnItemClickListener {
            override fun onBanClick(patient: BenhNhan) {
                super.onBanClick(patient)
                val builder = AlertDialog.Builder(requireContext())
                val message = if (patient.BiKhoa) "Có chắc muốn mở khoá tài khoản này?" else "Có chắc muốn khoá tài khoản này?"
                builder.setTitle("Cảnh báo")
                    .setMessage(message)
                    .setPositiveButton("Xác nhận") { dialog, which ->
                        var newValue: Boolean = !patient.BiKhoa
                        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("BenhNhan")
                        val query = databaseRef.orderByChild("maBenhNhan").equalTo(patient.MaBenhNhan)
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

            override fun onDeleteClick(patient: BenhNhan) {
                super.onDeleteClick(patient)
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Cảnh báo")
                    .setMessage("Bạn có chắc chắn muốn xoá ?")
                    .setPositiveButton("Xác nhận") { dialog, which ->
                        // Xoá medicine trong Firebase
                        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("BenhNhan")
                        val query = databaseRef.orderByChild("maBenhNhan").equalTo(patient.MaBenhNhan)
                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (data in snapshot.children) {
                                    data.ref.removeValue()
                                }
                                Toast.makeText(requireContext(), "Đã xoá " + patient.HoTen, Toast.LENGTH_SHORT).show()
                                adapter.notifyDataSetChanged()
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
        return inflater.inflate(R.layout.fragment_patient_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.patientRV)

        searchView = view.findViewById(R.id.searchPatient)
        searchView!!.queryHint = "Tìm kiếm bênh nhân"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text changes
                filter(newText)
                return true
            }
        })
    }
    private fun filter(text: String?) {
        val filteredlist: ArrayList<BenhNhan> = ArrayList()
        for (item in patientList) {
            if (item.HoTen.toLowerCase().contains(text!!.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }
    fun readPatientFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child("BenhNhan")
        databaseRef.addValueEventListener( object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                patientList.clear()
                for (snapshot in dataSnapshot.children) {
                    val benhnhan = snapshot.getValue(BenhNhan::class.java)
                    patientList.add(benhnhan!!)
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}