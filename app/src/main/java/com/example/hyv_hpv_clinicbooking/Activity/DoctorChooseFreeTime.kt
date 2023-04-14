package com.example.hyv_hpv_clinicbooking.Activity

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.DayAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.TimeAdapter
import com.example.hyv_hpv_clinicbooking.R
import java.io.IOException

class DoctorChooseFreeTime : AppCompatActivity() {
    var customListView: RecyclerView? = null
    var dayList = arrayListOf<String>()
    var morningList = arrayListOf<String>()
    var afternoonList = arrayListOf<String>()

    var adapter: DayAdapter? = null
    var timeAdapter: TimeAdapter? = null
    var afternoonAdapter: TimeAdapter? = null

    var morningTimeView:GridView ?= null
    var afternoonTimeView:GridView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_choose_free_time)

        customListView = findViewById(R.id.dayRV)
        morningTimeView = findViewById<GridView>(R.id.morningGV)
        afternoonTimeView = findViewById<GridView>(R.id.afternoonGV)

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
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        customListView!!.layoutManager = layoutManager

        adapter?.onItemClick = { day, index ->
            try {

            } catch(e: IOException) {
                println(e.message)
            }
        }

        timeAdapter = morningList?.let { TimeAdapter(this, it) }
        morningTimeView?.adapter = timeAdapter
        morningTimeView?.setOnItemClickListener { adapterView, view, i, l ->
        }

        afternoonAdapter = afternoonList?.let { TimeAdapter(this, it) }
        afternoonTimeView?.adapter = afternoonAdapter
        afternoonTimeView?.setOnItemClickListener { adapterView, view, i, l ->

        }
    }
}