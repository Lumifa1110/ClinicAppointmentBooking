package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailPage
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<BacSi>()
    private lateinit var adapter: DoctorListAdapter
    private var quantityDoctorTV: TextView ?= null
    private lateinit var database : DatabaseReference
    var size: Int = 0
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        mList.clear()
        readDoctorFromRealtimeDB()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        quantityDoctorTV = view.findViewById(R.id.quantityDoctorTV)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }


    private fun filterList(query: String?) {
        println(query)

        if (query != null) {
            val filteredList = ArrayList<BacSi>()
            for (i in mList) {
                if (i.HoTen!!.lowercase(Locale.ROOT)!!.contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun displayRecyclerView() {
        recyclerView.setHasFixedSize(true)
        quantityDoctorTV?.setText(mList.size.toString())

        adapter = DoctorListAdapter(mList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
        adapter?.onItemClick = { index ->
            val intent = Intent(requireContext(), DoctorDetailPage::class.java)
            intent.putExtra("doctor", mList[index])
            startActivity(intent)
        }
    }

    fun readDoctorFromRealtimeDB() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        databaseRef.child("BacSi").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val doctor = snapshot.getValue(BacSi::class.java)
                    mList.add(doctor!!)
                }
                // TODO: Do something with the lichHenKhamList
                displayRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }


}
