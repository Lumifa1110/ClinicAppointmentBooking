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
import com.example.hyv_hpv_clinicbooking.Model.KhungGio
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference

class TimeAdapter (private var context: Context, private var freeTimeList: ArrayList<ThoiGianRanh>, var database : DatabaseReference, var keyList: ArrayList<String>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var timeView: TextView? = null

        init {
            timeView = row?.findViewById(R.id.timeView)
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
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
                if(timeCheck.trangThai == 0) {
                    viewHolder.timeView?.setBackgroundResource(R.drawable.day_choose)
                    viewHolder.timeView?.setTextColor(Color.parseColor("#ffffff"))
                }
                else {
                    viewHolder.timeView?.setBackgroundResource(R.drawable.time_not_choose)
                    viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
                }
                break;
            }
        }

        viewHolder.timeView?.setOnClickListener {
            for(timeCheck in freeTimeList) {
                if(khungGio.gioBatDau == timeCheck.gioBatDau && khungGio.ngayThang == timeCheck.ngayThang) {
                    if(timeCheck.trangThai == 1) {
                        timeCheck.trangThai = 0
                        database.child(keyList[position]).child("trangThai").setValue(0)
                        viewHolder.timeView?.setBackgroundResource(R.drawable.day_choose)
                        viewHolder.timeView?.setTextColor(Color.parseColor("#ffffff"))
                    }
                    else {
                        database.child(keyList[position]).child("trangThai").setValue(1)
                        timeCheck.trangThai = 1
                        viewHolder.timeView?.setBackgroundResource(R.drawable.time_not_choose)
                        viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
                    }
                    break;
                }
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