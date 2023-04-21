package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter_Admin
import com.example.hyv_hpv_clinicbooking.Adapter.PatientListAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R


class PatientManagement : Fragment() {
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PatientListAdapter
    lateinit var patientList: ArrayList<BenhNhan>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        var data = Data()
        patientList = data.generatePatientData()

        adapter = PatientListAdapter(requireContext(), patientList)
        recyclerView.adapter = adapter

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
        adapter.setOnItemClickListener(object: PatientListAdapter.OnItemClickListener {
            override fun onDeleteClick(patient: BenhNhan) {
                patientList.remove(patient)
                adapter.notifyDataSetChanged()
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
}