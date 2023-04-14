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
    private var mList = ArrayList<LichHenKham>()
    private lateinit var adapter: DoctorAppoinmentList
    private var timeList = ArrayList<ThoiGian>()
    private var patientList = ArrayList<BenhNhan>()
    private var newList = ArrayList<LichHenKham>()

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
        mList = data.generateScheduleData()
        newList = mList.filter { it.MaTrangThai == 1 } as ArrayList<LichHenKham>
        timeList = data.generateTimeData()
        patientList = data.generatePatientData()
        println("MA bac si day la :")

        adapter = DoctorAppoinmentList(newList, timeList, patientList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            val intent = Intent(requireContext(), PrescriptionActivity::class.java)

            timeList.forEach { item ->
                if (item.MaThoiGian == newList[index].MaThoiGian) {
                    intent.putExtra("date", item.Ngay)
                    intent.putExtra("time", item.GioBatDau)
                }
            }

            patientList.forEach { item ->
                if (item.MaBenhNhan == newList[index].MaBenhNhan) {
                    intent.putExtra("patient_name", item.HoTen)
                }
            }
            startActivity(intent)
        }
    }
}