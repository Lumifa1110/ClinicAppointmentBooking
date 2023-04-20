package com.example.hyv_hpv_clinicbooking.Fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter_Admin
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R


class DoctorManagement : Fragment() {

    var menuItem: MenuItem? = null
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DoctorListAdapter_Admin
    lateinit var doctorList: ArrayList<BacSi>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_doctor_management, container, false)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.DoctorView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = Data()
        doctorList = data.generateDoctorData()
        adapter = DoctorListAdapter_Admin(doctorList)
        recyclerView.adapter = adapter

        searchView = view.findViewById(R.id.searchDoctor)
        searchView!!.queryHint = "Search doctors"
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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.admin_search, menu)
        menuItem = menu.findItem(R.id.searchDoctor)
        searchView = menuItem!!.actionView as SearchView
        searchView!!.queryHint = "Search doctors"
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
        searchView!!.setOnCloseListener {
            searchView!!.clearFocus()
            false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun filter(text: String?) {
        val filteredlist: ArrayList<BacSi> = ArrayList()
        for (item in doctorList) {
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