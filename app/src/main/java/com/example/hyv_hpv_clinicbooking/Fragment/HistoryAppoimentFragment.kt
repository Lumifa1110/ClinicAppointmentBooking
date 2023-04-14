package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailPage
import com.example.hyv_hpv_clinicbooking.Activity.PrescriptionActivity
import com.example.hyv_hpv_clinicbooking.Adapter.HistoryAppoinmentAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryAppoimentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryAppoimentFragment : Fragment() {
    private var doctorList = ArrayList<BacSi>()
    private var appoinments = ArrayList<KeDon>()


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAppoinmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_appoiment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = Data()
        doctorList = data.generateDoctorData()
        appoinments = data.generateKeDonData()
        adapter = HistoryAppoinmentAdapter(doctorList, appoinments)
        recyclerView.adapter = adapter

        adapter?.onItemClick = { index ->
            val intent = Intent(requireContext(), PrescriptionActivity::class.java)
            intent.putExtra("name", doctorList[index].HoTen)
            for(appoinment in appoinments) {
                if(doctorList[index].MaBacSi == appoinment.MaBacSi) {
                    intent.putExtra("appoinment", appoinment)
                }
            }
            startActivity(intent)
        }
    }
}