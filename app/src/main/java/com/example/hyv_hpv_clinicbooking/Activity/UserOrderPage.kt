package com.example.hyv_hpv_clinicbooking.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.ChooseTimeAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DayAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.TimeAdapter
import com.example.hyv_hpv_clinicbooking.Model.KhungGio
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import java.util.*

class UserOrderPage : AppCompatActivity() {
    var customListView: RecyclerView? = null
    var morningList = arrayListOf<KhungGio>()
    var afternoonList = arrayListOf<KhungGio>()
    var lichRanh =  arrayListOf<ThoiGianRanh>()
    //
    var adapter: DayAdapter? = null
    var timeAdapter: ChooseTimeAdapter? = null
    var afternoonAdapter: ChooseTimeAdapter? = null
    //
    var morningTimeView: GridView? = null
    var afternoonTimeView: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order_page)

        customListView = findViewById(R.id.dayRV)
        morningTimeView = findViewById<GridView>(R.id.morningGV)
        afternoonTimeView = findViewById<GridView>(R.id.afternoonGV)

        morningList = arrayListOf<KhungGio>()
        afternoonList = arrayListOf<KhungGio>()
        lichRanh =  arrayListOf<ThoiGianRanh>()

        //thu 2
        lichRanh.add(ThoiGianRanh(1, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Thứ 2\n03/04", 1, 1));
        lichRanh.add(ThoiGianRanh(3, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Thứ 2\n03/04", 1, 1));
        lichRanh.add(ThoiGianRanh(6, "Thứ 2\n03/04", 1, 1));
        lichRanh.add(ThoiGianRanh(7, "Thứ 2\n03/04", 1, 1));
        lichRanh.add(ThoiGianRanh(8, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Thứ 2\n03/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Thứ 2\n03/04", 1, 0));

        //thu 3
        lichRanh.add(ThoiGianRanh(1, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(3, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(6, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(7, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(8, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Thứ 3\n04/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Thứ 3\n04/04", 1, 0));

        //thu 4
        lichRanh.add(ThoiGianRanh(1, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(3, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(6, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(7, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(8, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Thứ 4\n05/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Thứ 4\n05/04", 1, 0));

        //thu 5
        lichRanh.add(ThoiGianRanh(1, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(3, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(6, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(7, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(8, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Thứ 5\n06/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Thứ 5\n06/04", 1, 0));

        //thu 6
        lichRanh.add(ThoiGianRanh(1, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(3, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(6, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(7, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(8, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Thứ 6\n07/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Thứ 6\n07/04", 1, 0));

        //thu 7
        lichRanh.add(ThoiGianRanh(1, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(3, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(6, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(7, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(8, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Thứ 7\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Thứ 7\n08/04", 1, 0));

        //chu nhat
        lichRanh.add(ThoiGianRanh(1, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(2, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(3, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(4, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(5, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(6, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(7, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(8, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(9, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(10, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(11, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(12, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(13, "Chủ Nhật\n08/04", 1, 0));
        lichRanh.add(ThoiGianRanh(14, "Chủ Nhật\n08/04", 1, 0));

        //xu ly thu trong tuan
        var dayList =  ArrayList<String>()
        for(index in 0..6) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_MONTH, index)
            val day = cal.get(Calendar.DAY_OF_WEEK)
            if(day == 1) {
                dayList.add("Chủ Nhật" + "\n" + cal.time.date.toString() + "/" + cal.time.month.toString())

            }
            else {
                dayList.add("Thứ " + day.toString() + "\n" + cal.time.date.toString() + "/" + cal.time.month.toString())
            }
        }

        adapter = DayAdapter(dayList)
        customListView!!.adapter = adapter
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        customListView!!.layoutManager = layoutManager

        var CalendarOfDay = arrayListOf<ThoiGianRanh>()
        for(index in 0..13) {
            CalendarOfDay.add(lichRanh[index])
        }

        adapter?.onItemClick = {student, vitri ->
            println(student + " - " + vitri.toString())
        }
        adapter?.notifyDataSetChanged()

        //Khung gio co dinh
        morningList.add(KhungGio(1, "8:00 AM", "8:30 AM"))
        morningList.add(KhungGio(2, "8:30 AM", "9:00 AM"))
        morningList.add(KhungGio(3, "9:00 AM", "9:30 AM"))
        morningList.add(KhungGio(4, "9:30 AM", "10:00 AM"))
        morningList.add(KhungGio(5, "10:00 AM", "10:30 AM"))
        morningList.add(KhungGio(6, "10:30 AM", "11:00 AM"))
        morningList.add(KhungGio(7, "11:00 AM", "11:30 AM"))


        afternoonList.add(KhungGio(7, "13:00 PM", "13:30 PM"))
        afternoonList.add(KhungGio(8, "13:30 PM", "14:00 PM"))
        afternoonList.add(KhungGio(10, "14:00 PM", "14:30 PM"))
        afternoonList.add(KhungGio(11, "14:30 PM", "15:00 PM"))
        afternoonList.add(KhungGio(12, "15:00 PM", "15:30 PM"))
        afternoonList.add(KhungGio(13, "15:30 PM", "16:00 PM"))
        afternoonList.add(KhungGio(14, "16:00 PM", "16:30 PM"))


        timeAdapter = ChooseTimeAdapter(this, morningList, CalendarOfDay)
        morningTimeView?.adapter = timeAdapter


        afternoonAdapter = ChooseTimeAdapter(this, afternoonList, CalendarOfDay)
        afternoonTimeView?.adapter = afternoonAdapter
    }
}