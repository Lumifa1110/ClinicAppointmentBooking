package com.example.hyv_hpv_clinicbooking.Activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*

class DoctorPrescriptionInforPage : AppCompatActivity() {
    var name: AutoCompleteTextView? = null
    var quantity: EditText? = null
    var donvi: Spinner? = null
    var using: EditText? = null
    var deleteBtn: Button? = null
    var saveBtn: Button? = null
    var adapterSearch: ArrayAdapter<String>? = null
    val donThuocList = ArrayList<String>()
    val donviList = ArrayList<String>()

    override fun onStart() {
        super.onStart()
        donThuocList.clear()
        donviList.clear()
        readDonViFromRealtimeDB()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_prescription_infor_page)

        val donviList = listOf("Viên", "Ống", "Tuýp", "lọ", "lon")
        val items = listOf(
            "Paracetamol 500mg - thuốc giảm đau và hạ s",
            "Amoxicillin 500mg - kháng sinh điều trị nhiễm trùng",
            "Omeprazole 20mg - thuốc chống loét dạ dày",
            "Metformin 500mg - thuốc điều trị tiểu đường",
            "Simvastatin 20mg - thuốc giảm cholesterol",
            "Sertraline 50mg - thuốc trị trầm cảm",
            "Albuterol 2mg - thuốc giãn phế quản điều trị hen suyễn",
            "Warfarin 5mg - thuốc chống đông máu",
            "Furosemide 40mg - thuốc giảm đau và hạ sốt",
            "Ibuprofen 400mg - thuốc giảm đau và hạ sốt"
        )

        name = findViewById(R.id.nameET)
        quantity = findViewById(R.id.quantityET)
        donvi = findViewById(R.id.donviET)
        deleteBtn = findViewById(R.id.deleteBtn)
        saveBtn = findViewById(R.id.saveBtn)
        using = findViewById(R.id.using)
    }

    private fun main() {

        val selection = intent.getStringExtra("selection")
        var donViItem = donviList[0]
        val index = intent.getIntExtra("index", -1)
        if (selection.equals("edit_selection")) {
            val donThuoc = intent.getParcelableExtra<DonThuoc>("donThuoc") as DonThuoc
            name?.setText(donThuoc?.TenThuoc)
            quantity?.setText(donThuoc?.SoLuong!!.toString())
            donViItem = donThuoc?.DonVi.toString()
            using?.setText(donThuoc?.CachDung)
        } else {
            deleteBtn?.isVisible = false
        }

        ArrayAdapter(this, android.R.layout.simple_spinner_item, donviList)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                donvi?.adapter = adapter
                donvi?.setSelection(donviList.indexOf(donViItem))
            }

        adapterSearch = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_single_choice,
            donThuocList
        )
        name!!.setAdapter(adapterSearch)
        name!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

//        name!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            // Retrieve the clicked item from the adapter
//            val selectedItem = adapterSearch!!.getItem(position)
//
//            // Find the index of the selected item in the items array
//            val selectedIndex = items.indexOf(selectedItem)
//
//
//        }

        donvi?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                donViItem = donviList[p2]
            }
        }

        deleteBtn?.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("selection", "delete_selection")
            replyIntent.putExtra("index", index)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()

        }

        saveBtn?.setOnClickListener {

            val new_donThuoc = DonThuoc()
            new_donThuoc.TenThuoc = name?.text.toString()
            if (quantity?.text.toString().length > 0) {
                new_donThuoc.SoLuong = quantity?.text.toString().toInt()
            } else {
                new_donThuoc.SoLuong = 0
            }
            new_donThuoc.DonVi = donViItem
            new_donThuoc.CachDung = using?.text.toString()

            val replyIntent = Intent()
            if (selection.equals("edit_selection")) {
                replyIntent.putExtra("selection", "edit_selection")
                replyIntent.putExtra("index", index)
            } else {
                replyIntent.putExtra("selection", "save_selection")
            }
            replyIntent.putExtra("new_donThuoc", new_donThuoc)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    fun readDonViFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().reference.child("DanhSach")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if "DonVi" and "Thuoc" nodes exist
                if (dataSnapshot.hasChild("DonVi")) {
                    val donViSnapshot = dataSnapshot.child("DonVi")
                    for (donVi in donViSnapshot.children) {
                        val key = donVi.key
                        val donVi = donVi.child("TenDonVi").getValue(String::class.java)
                        donviList.add(donVi!!)
                    }
                }
                if (dataSnapshot.hasChild("Thuoc")) {
                    val thuocSnapshot = dataSnapshot.child("Thuoc")
                    for (thuoc in thuocSnapshot.children) {
                        val key = thuoc.key
                        val donThuoc = thuoc.child("TenThuoc").getValue(String::class.java)
                        donThuocList.add(donThuoc!!)
                    }
                }
                // Do something with the "DonVi" and "Thuoc" lists
                main()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}