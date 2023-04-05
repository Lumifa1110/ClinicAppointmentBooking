package com.example.hyv_hpv_clinicbooking.Fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Model.Doctor
import com.example.hyv_hpv_clinicbooking.R


class DoctorManagement : Fragment() {

    var doctor: Doctor?= null
    var menuItem: MenuItem? = null
    var searchView: SearchView? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DoctorListAdapter
    lateinit var doctorList: ArrayList<Doctor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_management, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.DoctocView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        doctorList = ArrayList()
        doctor = Doctor()
        doctor?.HoTen = "Yogesh Batra"
        doctor?.Image = R.drawable.doctor1
        doctor?.TenChuyenKhoa = "Nha sĩ"
        doctor?.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor?.SoDienThoai = "0123456789"
        doctor?.SLBenhNhan = 1234
        doctor?.SoNamTrongNghe = 4
        doctor?.SoCuocHen = 100
        doctorList.add(doctor!!)
        doctorList.add(doctor!!)
        doctorList.add(doctor!!)
        doctorList.add(doctor!!)
        adapter = DoctorListAdapter(doctorList)
        recyclerView.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.admin_search, menu)
        menuItem = menu.findItem(R.id.adminSearch)
        searchView = menuItem!!.actionView as SearchView
        searchView?.queryHint = "Search doctors"
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text changes
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)

    }
}