package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.AdminHomePage
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter_Admin
import com.example.hyv_hpv_clinicbooking.Adapter.MedicineAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.ChuyenKhoa
import com.example.hyv_hpv_clinicbooking.Model.Thuoc
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminDashBoard : Fragment() {
    private var medicineList = ArrayList<Thuoc>()
    private var specializeList = ArrayList<ChuyenKhoa>()

    lateinit var medicineAdapter: MedicineAdapter

    private lateinit var database : DatabaseReference

//    private lateinit var auth  : FirebaseAuth
    var tabHost: TabHost? = null
    var addMedicine: ImageButton? = null
    var addSpecialize: ImageButton? = null
    var medicineRV: RecyclerView? = null
    var specializeRV: RecyclerView? = null
    override fun onStart() {
        super.onStart()
        medicineList.clear()
        re_create()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_dash_board, container, false)
        tabHost = view.findViewById(R.id.tabAdmin)
        tabHost!!.setup()
        var tabSpec: TabHost.TabSpec? = null

        tabSpec = tabHost!!.newTabSpec("statistic")
        tabSpec.setContent(R.id.fragment_admin_statistic)
        tabSpec.setIndicator("Thống kê", null)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec("approval")
        tabSpec.setContent(R.id.fragment_approval_list)
        tabSpec.setIndicator("Danh sách chờ", null)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec("medicine")
        tabSpec.setContent(R.id.fragment_admin_medicine)
        tabSpec.setIndicator("Nhà thuốc", null)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec("specialize")
        tabSpec.setContent(R.id.fragment_admin_specialize)
        tabSpec.setIndicator("Chuyên khoa", null)
        tabHost!!.addTab(tabSpec)

        medicineRV = view.findViewById(R.id.medicineRV)
        specializeRV = view.findViewById(R.id.specializeRV)

        addMedicine = view.findViewById(R.id.addMedicine)
        addMedicine!!.setOnClickListener {
            Toast.makeText(requireContext(), "Add Medicine", Toast.LENGTH_SHORT).show()
            showAddMedicineDialog()
            re_create()
        }

        addSpecialize = view.findViewById(R.id.addSpecialize)
        addSpecialize!!.setOnClickListener {
            showAddSpecializeDialog()
            re_create()
        }
        return view
    }
    private fun re_create() {
        readMedicineFromRealtimeDB()
        readSpecializeFromRealtimeDB()
        displayRecyclerView()
    }
    private fun displayRecyclerView() {
        medicineRV!!.layoutManager = LinearLayoutManager(requireContext())
        medicineAdapter = MedicineAdapter(requireContext(), medicineList)
        medicineRV!!.adapter = medicineAdapter
    }
    private fun showAddMedicineDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
        val nameET = dialogLayout.findViewById<EditText>(R.id.nameMedicine)
        database = Firebase.database.getReference("DanhSach").child("Thuoc")

        builder.setView(dialogLayout)
        builder.setPositiveButton("Thêm thuốc") { dialog, which ->
            val newType = nameET.text.toString().trim()
            val newMedicine = Thuoc(newType)

            if (newType.isNotEmpty()) {
                val query = database.orderByChild("TenThuoc").equalTo(newMedicine.TenThuoc)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(requireContext(), "Thuốc đã tồn tại", Toast.LENGTH_SHORT).show()
                        } else {
                            database.push().setValue(newMedicine)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                Toast.makeText(requireContext(), "Loại thuốc mới: $newType", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Loại thuốc và mô tả không thể để trống", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Huỷ bỏ") { dialog, which ->
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun showAddSpecializeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_specialize, null)
        val nameET = dialogLayout.findViewById<EditText>(R.id.nameMedicine)
        database = Firebase.database.getReference("DanhSach").child("ChuyenKhoa")

        builder.setView(dialogLayout)
        builder.setPositiveButton("Thêm chuyên khoa") { dialog, which ->
            val newType = nameET.text.toString().trim()
            val newSpecialize = ChuyenKhoa(newType)
            if (newType.isNotEmpty()) {
                // Perform the action of adding the new type and description here
                val query = database.orderByChild("TenChuyenKhoa").equalTo(newSpecialize.TenChuyenKhoa)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(requireContext(), "Chuyên khoa đã tồn tại", Toast.LENGTH_SHORT).show()
                        } else {
                            database.push().setValue(newSpecialize)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                Toast.makeText(requireContext(), "Chuyên khoa mới: $newType", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tên chuyên khoa để trống", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Huỷ bỏ") { dialog, which ->
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
    fun readMedicineFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach").child("Thuoc")
        databaseRef.addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicineList.clear()
                for (snapshot in snapshot.children) {
                    val thuoc = snapshot.getValue(Thuoc::class.java)
                    medicineList.add(thuoc!!)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun readSpecializeFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach").child("ChuyeKhoa")
        databaseRef.addValueEventListener( object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                specializeList.clear()
                for (snapshot in dataSnapshot.children) {
                    val chuyenkhoa = snapshot.getValue(ChuyenKhoa::class.java)
                    specializeList.add(chuyenkhoa!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}