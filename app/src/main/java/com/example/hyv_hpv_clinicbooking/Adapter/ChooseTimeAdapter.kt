package com.example.hyv_hpv_clinicbooking.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.example.hyv_hpv_clinicbooking.Model.KhungGio
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R

class ChooseTimeAdapter (private var context: Context, private var items: ArrayList<KhungGio>, private var freeTimeList: ArrayList<ThoiGianRanh>) : BaseAdapter() {
    private var selectedItemPosition: Int = -1
    private class ViewHolder(row: View?) {
        var timeView: Button? = null

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


        val khungGio = items[position]
        viewHolder.timeView?.text = khungGio.gioBatDau

//        for(timeCheck in freeTimeList) {
//            if(khungGio.maKhungGio == timeCheck.maKhungGio) {
//                if(timeCheck.trangThai == 0) {
//                    viewHolder.timeView?.setBackgroundResource(R.drawable.time_busy)
//                    viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
//                    viewHolder.timeView?.isEnabled = false
//                }
//                else {
//                    viewHolder.timeView?.setBackgroundResource(R.drawable.box_date)
//                    viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
//                }
//                break;
//            }
//        }

        viewHolder.timeView?.setOnClickListener {
            if(viewHolder.timeView?.isEnabled == true) {
                selectedItemPosition = position
                notifyDataSetChanged()
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

    override fun getItem(i: Int): KhungGio {
        return items[i]
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return items.size
    }

}