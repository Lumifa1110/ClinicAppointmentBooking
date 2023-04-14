package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UnapprovedAppointmentTabView.newInstance] factory method to
 * create an instance of this fragment.
 */
class UnapprovedAppointmentTabView : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var appoinmentList = ArrayList<LichHenKham>()
    private lateinit var adapter: DoctorAppoinmentList
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var unapprovedList = ArrayList<LichHenKham>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unapproved_appointment_tab_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = Data()
        appoinmentList = data.generateScheduleData()
        unapprovedList = appoinmentList.filter { it.MaTrangThai == 0 } as ArrayList<LichHenKham>
        timeList = data.generateTimeData()
        patientList = data.generatePatientData()
        println("MA bac si day la :")

        adapter = DoctorAppoinmentList(unapprovedList, timeList, patientList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            showAlertDialog(index)
        }
    }

    private fun showAlertDialog(index: Int) {
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
            adapter?.notifyDataSetChanged()

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