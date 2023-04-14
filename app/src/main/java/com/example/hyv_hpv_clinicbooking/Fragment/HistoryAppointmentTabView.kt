package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorPrescriptionPage
import com.example.hyv_hpv_clinicbooking.Activity.PrescriptionActivity
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorAppoinmentList
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryAppointmentTabView.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryAppointmentTabView : Fragment() {
    private var appoinmentList = ArrayList<LichHenKham>()
    private var timeList = ArrayList<ThoiGian>()
    private var prescriptionList = ArrayList<KeDon>()
    private var patientList = ArrayList<BenhNhan>()
    private var historyAppoinmentList = ArrayList<LichHenKham>()

    private lateinit var adapter: DoctorAppoinmentList
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_appointment_tab_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = Data()
        appoinmentList = data.generateScheduleData()
        historyAppoinmentList = appoinmentList.filter { it.MaTrangThai == 2 } as ArrayList<LichHenKham>
        timeList = data.generateTimeData()
        patientList = data.generatePatientData()
        prescriptionList = data.generateKeDonData()

        adapter = DoctorAppoinmentList(historyAppoinmentList, timeList, patientList)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            val intent = Intent(requireContext(), PrescriptionActivity::class.java)
            intent.putExtra("people", "doctor")
            for(patient in patientList) {
                if(historyAppoinmentList[index].MaBenhNhan == patient.MaBenhNhan) {
                    intent.putExtra("name", patientList[index].HoTen)
                }
            }
            for(prescription in prescriptionList) {
                if(historyAppoinmentList[index].MaBenhNhan == prescription.MaBenhNhan) {
                    println(prescription.MaBacSi.toString() + " và " + prescription.MaBenhNhan.toString())
                    intent.putExtra("prescription", prescription)

                }
            }
            startActivity(intent)
        }
    }

}

