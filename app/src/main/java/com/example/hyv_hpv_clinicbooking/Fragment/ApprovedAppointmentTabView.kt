package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorPrescriptionInforPage
import com.example.hyv_hpv_clinicbooking.Activity.DoctorPrescriptionPage
import com.example.hyv_hpv_clinicbooking.Activity.PrescriptionActivity
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
 * Use the [ApprovedAppointmentTabView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApprovedAppointmentTabView : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var appoinmentList = ArrayList<LichHenKham>()
    private lateinit var adapter: DoctorAppoinmentList
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var approvedList = ArrayList<LichHenKham>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approved_appointment_tab_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = Data()
        appoinmentList = data.generateScheduleData()
        approvedList = appoinmentList.filter { it.MaTrangThai == 1 } as ArrayList<LichHenKham>
        timeList = data.generateTimeData()
        patientList = data.generatePatientData()

        adapter = DoctorAppoinmentList(approvedList, timeList, patientList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            showAlertDialog(index)
        }
    }

    private fun showAlertDialog(index: Int) {
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