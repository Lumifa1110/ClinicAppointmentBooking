package com.example.hyv_hpv_clinicbooking.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailPage
import com.example.hyv_hpv_clinicbooking.Adapter.DayAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DoctorListAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.TimeAdapter
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.R
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorChooseFreeTimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorChooseFreeTimeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var customListView: RecyclerView? = null
    var dayList = arrayListOf<String>()
    var morningList = arrayListOf<String>()
    var afternoonList = arrayListOf<String>()

    var adapter: DayAdapter? = null
    var timeAdapter: TimeAdapter? = null
    var afternoonAdapter: TimeAdapter? = null

    var morningTimeView: GridView? = null
    var afternoonTimeView: GridView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_choose_free_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customListView = view.findViewById(R.id.dayRV)
        morningTimeView = view.findViewById<GridView>(R.id.morningGV)
        afternoonTimeView = view.findViewById<GridView>(R.id.afternoonGV)

        morningList = arrayListOf<String>()
        afternoonList = arrayListOf<String>()

        dayList.add("Thứ 2\n03/04")
        dayList.add("Thứ 3\n04/04")
        dayList.add("Thứ 4\n05/04")
        dayList.add("Thứ 5\n06/04")
        dayList.add("Thứ 6\n07/04")
        dayList.add("Thứ 7\n08/04")
        dayList.add("Chủ Nhật\n09/04")

        morningList.add("8:00 AM")
        morningList.add("8:30 AM")
        morningList.add("9:00 AM")
        morningList.add("9:30 AM")
        morningList.add("10:00 AM")
        morningList.add("10:30 AM")
        morningList.add("11:00 AM")

        afternoonList.add("13:00 PM")
        afternoonList.add("13:30 PM")
        afternoonList.add("14:00 PM")
        afternoonList.add("14:30 PM")
        afternoonList.add("15:00 PM")
        afternoonList.add("15:30 PM")
        afternoonList.add("16:00 PM")

        adapter = DayAdapter(dayList)

        customListView!!.adapter = adapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        customListView!!.layoutManager = layoutManager

        adapter?.onItemClick = { day, index ->
            try {

            } catch (e: IOException) {
                println(e.message)
            }
        }

        timeAdapter = morningList?.let { TimeAdapter(requireContext(), it) }
        morningTimeView?.adapter = timeAdapter
        morningTimeView?.setOnItemClickListener { adapterView, view, i, l ->
        }

        afternoonAdapter = afternoonList?.let { TimeAdapter(requireContext(), it) }
        afternoonTimeView?.adapter = afternoonAdapter
        afternoonTimeView?.setOnItemClickListener { adapterView, view, i, l ->
        }
    }
}