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
import android.view.ViewTreeObserver
import android.widget.*
import androidx.core.view.isVisible
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*

class DoctorPrescriptionInforPage : AppCompatActivity() {
    var name: AutoCompleteTextView? = null
    private lateinit var scrollView: ScrollView
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

        name = findViewById(R.id.nameET)
        quantity = findViewById(R.id.quantityET)
        donvi = findViewById(R.id.donviET)
        deleteBtn = findViewById(R.id.deleteBtn)
        saveBtn = findViewById(R.id.saveBtn)
        using = findViewById(R.id.using)
        scrollView = findViewById(R.id.scrollView)
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
                scrollToView(name!!)
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
                scrollToView(donvi!!)
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
                        val donVi = donVi.child("tenDonVi").getValue(String::class.java)
                        donviList.add(donVi!!)
                    }
                }
                if (dataSnapshot.hasChild("Thuoc")) {
                    val thuocSnapshot = dataSnapshot.child("Thuoc")
                    for (thuoc in thuocSnapshot.children) {
                        val key = thuoc.key
                        val donThuoc = thuoc.child("tenThuoc").getValue(String::class.java)
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

    private fun scrollToView(view: View) {
        // Add an OnGlobalLayoutListener to the ScrollView
        scrollView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Scroll to the desired view
                scrollView.scrollTo(0, view.bottom)
                // Remove the listener to avoid multiple scrolls
                scrollView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

}