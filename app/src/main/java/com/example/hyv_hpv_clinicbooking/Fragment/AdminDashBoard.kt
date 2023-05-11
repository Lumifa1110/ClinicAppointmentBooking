package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailKiemDuyet
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailPage
import com.example.hyv_hpv_clinicbooking.Adapter.ApproveAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.MedicineAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.SpecializeAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.ChuyenKhoa
import com.example.hyv_hpv_clinicbooking.Model.Thuoc
import com.example.hyv_hpv_clinicbooking.R
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
    private var approvalList = ArrayList<BacSi>()

    lateinit var medicineAdapter: MedicineAdapter
    lateinit var specializeAdapter: SpecializeAdapter
    lateinit var approvalAdapter: ApproveAdapter


    lateinit var searchMedicine: SearchView
    lateinit var searchSpecialize: SearchView

    private lateinit var database : DatabaseReference

//    private lateinit var auth  : FirebaseAuth
    private var tabHost: TabHost? = null
    private var addMedicine: ImageButton? = null
    private var addSpecialize: ImageButton? = null
    private var medicineRV: RecyclerView? = null
    private var specializeRV: RecyclerView? = null
    private var approvalRV: RecyclerView? = null

    private var countMedicine: TextView? = null
    private var countSpecialize: TextView? = null
    private var countAll: TextView? = null
    private var countApproval: TextView? = null
    private var bacsiCanDuyet: TextView? = null
    private var countDoctor: TextView? = null
    private var countPatient: TextView? = null



    override fun onStart() {
        super.onStart()
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

        countMedicine = view.findViewById(R.id.countMedicine)
        countSpecialize = view.findViewById(R.id.countSpec)
        countAll = view.findViewById(R.id.countAll)
        countApproval = view.findViewById(R.id.countWaitDoc)
        countDoctor = view.findViewById(R.id.countDoc)
        countPatient = view.findViewById(R.id.countPat)
        bacsiCanDuyet = view.findViewById(R.id.number)

        medicineRV = view.findViewById(R.id.medicineRV)
        specializeRV = view.findViewById(R.id.specializeRV)
        approvalRV = view.findViewById(R.id.approvalRV)

        searchMedicine = view.findViewById(R.id.searchMed)
        searchMedicine.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterMedicine(newText)
                return true
            }
        })

        searchSpecialize = view.findViewById(R.id.searchSpec)
        searchSpecialize.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterSpecialize(newText)
                return true
            }
        })

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
        readApprovalFromRealtimeDB()
        getAmountDoctorAndPatient()
        displayRecyclerView()
    }
    private fun displayRecyclerView() {
        medicineRV!!.layoutManager = LinearLayoutManager(context)
        specializeRV!!.layoutManager = LinearLayoutManager(context)
        approvalRV!!.layoutManager = LinearLayoutManager(context)

        medicineAdapter = MedicineAdapter(requireContext(), medicineList)
        specializeAdapter = SpecializeAdapter(requireContext(), specializeList)
        approvalAdapter = ApproveAdapter(requireContext(), approvalList)

        medicineRV!!.adapter = medicineAdapter
        specializeRV!!.adapter = specializeAdapter
        approvalRV!!.adapter = approvalAdapter

        //Kiểm duyệt
        approvalAdapter.onItemClick = { bacSi, index ->
            val intent = Intent(context, DoctorDetailKiemDuyet::class.java)
            intent.putExtra("doctor", bacSi)
            startActivity(intent)
        }

        // Edit và Delete với 1 thuốc
        medicineAdapter.setOnItemClickListener(object : MedicineAdapter.OnItemClickListener{
            // Khi xoá
            override fun onDeleteClick(medicine: Thuoc) {
                super.onDeleteClick(medicine)
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Cảnh báo")
                    .setMessage("Bạn có chắc chắn muốn xoá ?")
                    .setPositiveButton("Xác nhận") { dialog, which ->
                        // Xoá medicine trong Firebase
                        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach").child("Thuoc")
                        val query = databaseRef.orderByChild("tenThuoc").equalTo(medicine.tenThuoc)
                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (data in snapshot.children) {
                                    data.ref.removeValue()
                                }
                                Toast.makeText(requireContext(), "Đã xoá " + medicine.tenThuoc, Toast.LENGTH_SHORT).show()
                                medicineAdapter.notifyDataSetChanged()
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
            // Khi chỉnh sửa
            override fun onEditClick(medicine: Thuoc) {
                super.onEditClick(medicine)
                // Tạo 1 dialog hiện tên thuốc trên EditText
                var builder = AlertDialog.Builder(requireContext())
                var dialogLayout = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
                var nameET = dialogLayout.findViewById<EditText>(R.id.nameMedicine)
                nameET.setText(medicine.tenThuoc)

                builder.setView(dialogLayout)
                    .setPositiveButton("Thay đổi") { dialog, which ->
                        val databaseRef = FirebaseDatabase.getInstance()
                            .getReference("DanhSach")
                            .child("Thuoc")
                        // Tìm thuốc với tên thuốc cũ/ chưa cập nhật
                        val query = databaseRef.orderByChild("tenThuoc").equalTo(medicine.tenThuoc)
                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()){
                                    val newType = nameET.text.toString().trim()
                                    val updateMedicine = Thuoc(newType)
                                    var key: String? = null
                                    if (newType.isNotEmpty()){
                                        // Kiểm tra xem tên thuốc mới (được chỉnh sửa đã tồn tại hay chưa
                                        val queryCheck = databaseRef.orderByChild("tenThuoc")
                                            .equalTo(updateMedicine.tenThuoc)
                                        queryCheck.addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(subSnapshot: DataSnapshot) {
                                                if (subSnapshot.exists()){
                                                    Toast.makeText(requireContext(), "Thuốc đã tồn tại", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    // Cập nhật khi tên của thuốc mới cập nhật không có tồn tại trong database
                                                    for (data in snapshot.children) {
                                                        key = data.key.toString()
                                                    }
                                                    databaseRef.child(key!!).child("tenThuoc").setValue(newType)
                                                }
                                            }
                                            override fun onCancelled(subError: DatabaseError) {
                                                TODO("Not yet implemented")
                                            }
                                        })
                                        re_create()
                                        Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(requireContext(), "Thuốc không để trống", Toast.LENGTH_SHORT).show()
                                    }

                                } else {
                                    Toast.makeText(requireContext(), "Thuốc không tồn tại", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
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

        // Edit và Delete với 1 chuyên khoa
        specializeAdapter.setOnItemClickListener(object : SpecializeAdapter.OnItemClickListener{
            override fun onDeleteClick(specialize: ChuyenKhoa) {
                super.onDeleteClick(specialize)
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Cảnh báo")
                    .setMessage("Bạn có chắc chắn muốn xoá ?")
                    .setPositiveButton("Xác nhận") { dialog, which ->
                        // Xoá medicine trong Firebase
                        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach").child("ChuyenKhoa")
                        val query = databaseRef.orderByChild("tenChuyenKhoa")
                            .equalTo(specialize.tenChuyenKhoa)
                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (data in snapshot.children) {
                                    data.ref.removeValue()
                                }
                                Toast.makeText(requireContext(), "Đã xoá " + specialize.tenChuyenKhoa, Toast.LENGTH_SHORT).show()
                                specializeAdapter.notifyDataSetChanged()
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
            override fun onEditClick(specialize: ChuyenKhoa) {
                super.onEditClick(specialize)
                // Tạo 1 dialog hiện tên chuyên khoa trên EditText
                val builder = AlertDialog.Builder(requireContext())
                val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_specialize, null)
                val nameET = dialogLayout.findViewById<EditText>(R.id.nameSpecialize)
                nameET.setText(specialize.tenChuyenKhoa)

                builder.setView(dialogLayout)
                    .setPositiveButton("Thay đổi") { dialog, which ->
                        val databaseRef = FirebaseDatabase.getInstance()
                            .getReference("DanhSach")
                            .child("ChuyenKhoa")
                        // Tìm chuyên khoa bằng tên chuyên khoa cũ/ chưa cập nhật
                        val query = databaseRef.orderByChild("tenChuyenKhoa")
                            .equalTo(specialize.tenChuyenKhoa)
                        query.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val newType = nameET.text.toString().trim()
                                    val updateSpecialize = ChuyenKhoa(newType)
                                    var key: String? = null
                                    if (newType.isNotEmpty()){
                                        val queryCheck = databaseRef.orderByChild("tenChuyenKhoa")
                                            .equalTo(updateSpecialize.tenChuyenKhoa)
                                        queryCheck.addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(subSnapshot: DataSnapshot) {
                                                if (subSnapshot.exists()){
                                                    Toast.makeText(requireContext(), "Chuyên khoa đã tồn tại", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    // Cập nhật khi tên của chuyên khoa mới cập nhật không có tồn tại trong database
                                                    for (data in snapshot.children) {
                                                        key = data.key.toString()
                                                    }
                                                    databaseRef.child(key!!).child("tenChuyenKhoa").setValue(newType)
                                                }
                                            }
                                            override fun onCancelled(subError: DatabaseError) {
                                                TODO("Not yet implemented")
                                            }
                                        })
                                        re_create()
                                        Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(requireContext(), "Chuyên khoa không để trống", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "Chuyên khoa không tồn tại", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
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
                val query = database.orderByChild("tenThuoc").equalTo(newMedicine.tenThuoc)
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
        val nameET = dialogLayout.findViewById<EditText>(R.id.nameSpecialize)
        database = Firebase.database.getReference("DanhSach").child("ChuyenKhoa")
        builder.setView(dialogLayout)
        builder.setPositiveButton("Thêm chuyên khoa") { dialog, which ->
            val newType = nameET.text.toString().trim()
            val newSpecialize = ChuyenKhoa(newType)
            if (newType.isNotEmpty()) {
                // Perform the action of adding the new type and description here
                val query = database.orderByChild("tenChuyenKhoa").equalTo(newSpecialize.tenChuyenKhoa)
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
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                medicineList.clear()
                for (snapshot in snapshot.children) {
                    val thuoc = snapshot.getValue(Thuoc::class.java)
                    medicineList.add(thuoc!!)
                }
                countMedicine!!.setText(medicineList.size.toString())
                medicineAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun readSpecializeFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach").child("ChuyenKhoa")
        databaseRef.addValueEventListener( object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                specializeList.clear()
                for (snapshot in dataSnapshot.children) {
                    val chuyenkhoa = snapshot.getValue(ChuyenKhoa::class.java)
                    specializeList.add(chuyenkhoa!!)
                }
                countSpecialize!!.setText(specializeList.size.toString())
                specializeAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun readApprovalFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("BacSiChoDuyet")
        databaseRef.addValueEventListener( object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                approvalList.clear()
                for (snapshot in dataSnapshot.children) {
                    val bacSi = snapshot.getValue(BacSi::class.java)
                    approvalList.add(bacSi!!)
                }
                bacsiCanDuyet!!.setText(approvalList.size.toString())
                approvalAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getAmountDoctorAndPatient() {
        var bacsiDuyet: Long = 0
        var bacsiChuaDuyet: Long = 0
        var benhnhanCount: Long = 0
        var totalCount: Long = 0
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bacsiDuyet = snapshot.child("BacSi").children
                    .filter { it.child("daDuyet").getValue(Boolean::class.java) == true }.count().toLong()
                bacsiChuaDuyet = snapshot.child("BacSi").children
                    .filter { it.child("daDuyet").getValue(Boolean::class.java) == false }.count().toLong()
                benhnhanCount = snapshot.child("BenhNhan").childrenCount
                totalCount = bacsiDuyet + benhnhanCount
                countAll!!.setText(totalCount.toString())
                countApproval!!.setText(bacsiChuaDuyet.toString())
                countDoctor!!.setText(bacsiDuyet.toString())
                countPatient!!.setText(benhnhanCount.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun filterMedicine(text: String?) {
        val filteredlist: ArrayList<Thuoc> = ArrayList()
        for (item in medicineList) {
            if (item.tenThuoc.toLowerCase().contains(text!!.toLowerCase())) {
                // if the item is matched we are adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are displaying a toast message as no data found.
            Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered list to our adapter class.
            medicineAdapter.filter(filteredlist)
        }
    }
    private fun filterSpecialize(text: String?) {
        val filteredlist: ArrayList<ChuyenKhoa> = ArrayList()
        for (item in specializeList) {
            if (item.tenChuyenKhoa!!.toLowerCase().contains(text!!.toLowerCase())) {
                // if the item is matched we are adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are displaying a toast message as no data found.
            Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered list to our adapter class.
            specializeAdapter.filter(filteredlist)
        }
    }
}