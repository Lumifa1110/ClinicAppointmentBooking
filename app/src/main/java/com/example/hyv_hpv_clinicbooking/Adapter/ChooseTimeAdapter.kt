package com.example.hyv_hpv_clinicbooking.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.KhungGio
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference

class ChooseTimeAdapter (private var context: Context, private var freeTimeList: ArrayList<ThoiGianRanh>, var database : DatabaseReference, var keyList: ArrayList<String>, var selectedItemPosition: Int, var selectedDate:String, var cuocHenList: ArrayList<CuocHen>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var timeView: TextView? = null

        init {
            timeView = row?.findViewById(R.id.timeView)
        }
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            view = (inflater as LayoutInflater).inflate(R.layout.morning_time, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }


        val khungGio = freeTimeList[position]
        viewHolder.timeView?.text = khungGio.gioBatDau + " - " + khungGio.gioKetThuc

        for(timeCheck in freeTimeList) {
            if(khungGio.gioBatDau == timeCheck.gioBatDau && khungGio.ngayThang == timeCheck.ngayThang) {
                if(timeCheck.trangThai == 1) {
                    viewHolder.timeView?.setBackgroundResource(R.drawable.time_busy)
                    viewHolder.timeView?.setTextColor(Color.parseColor("#747474"))
                    viewHolder.timeView?.isEnabled = false
                }
                else {
                    viewHolder.timeView?.setBackgroundResource(R.drawable.box_date)
                    viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
                }
                break;
            }
        }


        for(cuocHen in cuocHenList) {
            if(cuocHen.Ngay == selectedDate) {
                if (khungGio.gioBatDau == cuocHen.GioBatDau && khungGio.gioKetThuc == cuocHen.GioKetThuc) {
                    if (cuocHen.MaTrangThai == 0 || cuocHen.MaTrangThai == 1) {
                        viewHolder.timeView?.setBackgroundResource(R.drawable.time_busy)
                        viewHolder.timeView?.setTextColor(Color.parseColor("#747474"))
                        viewHolder.timeView?.isEnabled = false
                    }
                }
            }
        }

        if(viewHolder.timeView?.isEnabled == true) {
            if (selectedItemPosition == position) {
                viewHolder.timeView?.setBackgroundResource(R.drawable.day_choose)
                viewHolder.timeView?.setTextColor(Color.parseColor("#ffffff"))
            } else {
                viewHolder.timeView?.setBackgroundResource(R.drawable.box_date)
                viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
            }
        }

        return view
    }

    override fun getItem(i: Int): ThoiGianRanh {
        return freeTimeList[i]
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return freeTimeList.size
    }

}