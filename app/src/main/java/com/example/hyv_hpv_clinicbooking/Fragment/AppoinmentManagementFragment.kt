package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.hyv_hpv_clinicbooking.Activity.DoctorPrescriptionPage
import com.example.hyv_hpv_clinicbooking.Activity.PrescriptionActivity
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Adapter.FragmentAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.HistoryAppoinmentAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AppoinmentManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppoinmentManagementFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tablayout: TabLayout

    var tabHost : TabHost? = null
    var recyclerView1: RecyclerView?= null
    var adapter1: DoctorAppoinmentList ?= null
    var quantityTV1: TextView ?= null
    var recyclerView2: RecyclerView?= null
    var adapter2: DoctorAppoinmentList ?= null
    var quantityTV2: TextView ?= null
    var recyclerView3: RecyclerView?= null
    var adapter3: DoctorAppoinmentList ?= null
    var quantityTV3: TextView ?= null

    private var appoinmentList = ArrayList<LichHenKham>()
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var prescriptionList = ArrayList<KeDon>()

    private var unapprovedList = ArrayList<LichHenKham>()
    private var approvedList = ArrayList<LichHenKham>()
    private var historyAppoinmentList = ArrayList<LichHenKham>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appoinment_management, container, false)
    }

/*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        tablayout = view.findViewById(R.id.tablayout)

        val fragmentAdapter = FragmentAdapter(childFragmentManager)
        fragmentAdapter.addFragment(UnapprovedAppointmentTabView(),"Chưa duyệt")
        fragmentAdapter.addFragment(ApprovedAppointmentTabView(),"Đã duyệt")
        fragmentAdapter.addFragment(HistoryAppointmentTabView(),"Lịch sử")

        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
    }*/

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         tabHost = view.findViewById(R.id.tabHost)
         tabHost?.setup()
         var tabSpec : TabHost.TabSpec? = null
         tabSpec = tabHost?.newTabSpec("tab1")
         tabSpec?.setIndicator("Chờ duyệt", null)
         tabSpec?.setContent(R.id.unapprove_tab)
         tabHost?.addTab(tabSpec)
         tabSpec = tabHost?.newTabSpec("tab2")
         tabSpec?.setIndicator("Kê đơn", null)
         tabSpec?.setContent(R.id.approve_tab)
         tabHost?.addTab(tabSpec)
         tabSpec = tabHost?.newTabSpec("tab3")
         tabSpec?.setIndicator("Lịch sử", null)
         tabSpec?.setContent(R.id.history_tab)
         tabHost?.addTab(tabSpec)

         var data = Data()
         appoinmentList = data.generateScheduleData()
         timeList = data.generateTimeData()
         patientList = data.generatePatientData()
         prescriptionList = data.generateKeDonData()

        //tab1
         unapprovedList = appoinmentList.filter { it.MaTrangThai == 0 } as ArrayList<LichHenKham>
         quantityTV1 = view.findViewById(R.id.quantity1)
         quantityTV1?.setText(unapprovedList.size.toString())

         recyclerView1 = view.findViewById(R.id.recyclerView1)
         recyclerView1?.layoutManager = LinearLayoutManager(requireContext())
         adapter1 = DoctorAppoinmentList(unapprovedList, timeList, patientList)
         recyclerView1?.adapter = adapter1

         adapter1?.onItemClick = { index ->
             showUnapproveAlertDialog(index)
         }

        // tab2
         approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<LichHenKham>
         quantityTV2 = view.findViewById(R.id.quantity2)
         quantityTV2?.setText(approvedList.size.toString())

         recyclerView2 = view.findViewById(R.id.recyclerView2)

         recyclerView2?.layoutManager = LinearLayoutManager(requireContext())
         patientList = data.generatePatientData()

         adapter2 = DoctorAppoinmentList(approvedList, timeList, patientList)
         recyclerView2?.adapter = adapter2

         adapter2?.onItemClick = { index ->
             showApproveAlertDialog(index)
         }

//         Tab3
         historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<LichHenKham>
         quantityTV3 = view.findViewById(R.id.quantity3)
         quantityTV3?.setText(historyAppoinmentList.size.toString())

         recyclerView3 = view.findViewById(R.id.recyclerView3)
         recyclerView3?.layoutManager = LinearLayoutManager(requireContext())

         adapter3 = DoctorAppoinmentList(historyAppoinmentList, timeList, patientList)
         recyclerView3?.adapter = adapter3

         adapter3?.onItemClick = { index ->
             val intent = Intent(requireContext(), PrescriptionActivity::class.java)
             intent.putExtra("people", "doctor")
             for(patient in patientList) {
                 if(historyAppoinmentList[index].MaBenhNhan == patient.MaBenhNhan) {
                     intent.putExtra("name", patientList[index].HoTen)
                 }
             }
             for(prescription in prescriptionList) {
                 if(historyAppoinmentList[index].MaBenhNhan == prescription.MaBenhNhan && historyAppoinmentList[index].MaBacSi == prescription.MaBacSi) {
                     intent.putExtra("prescription", prescription)
                 }
             }
             startActivity(intent)
         }
    }

    private fun showUnapproveAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Duyệt cuộc hẹn")
        alertDialog.setMessage("Bạn có muốn duyệt cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            Toast.makeText(requireContext(), "Cuộc hẹn đã duyệt", Toast.LENGTH_LONG).show()
            appoinmentList.forEach { item ->
                if (item.MaLichHen == unapprovedList[index].MaLichHen) {
                    item.MaTrangThai = 1
                }
            }
            unapprovedList.removeAt(index)
            adapter1?.notifyDataSetChanged()

        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun showApproveAlertDialog(index: Int) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Kê đơn thuốc")
        alertDialog.setMessage("Bạn có muốn kê đơn cho cuộc hẹn này không?")
        alertDialog.setPositiveButton(
            "Có"
        ) { _, _ ->
            Toast.makeText(requireContext(), "Cuộc hẹn đã kê đơn", Toast.LENGTH_LONG).show()
            val intent = Intent(requireContext(), DoctorPrescriptionPage::class.java)

            timeList.forEach { item ->
                if (item.MaThoiGian == approvedList[index].MaThoiGian) {
                    intent.putExtra("date", item.Ngay)
                    intent.putExtra("time", item.GioBatDau)
                }
            }

            patientList.forEach { item ->
                if (item.MaBenhNhan == approvedList[index].MaBenhNhan) {
                    intent.putExtra("patient_name", item.HoTen)
                }
            }
            startActivity(intent)

            appoinmentList.forEach { item ->
                if (item.MaLichHen == approvedList[index].MaLichHen) {
                    item.MaTrangThai = 3
                }
            }
            approvedList.removeAt(index)
            adapter2?.notifyDataSetChanged()
        }
        alertDialog.setNegativeButton(
            "Không"
        ) { _, _ ->

        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}



