package com.example.hyv_hpv_clinicbooking.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.example.hyv_hpv_clinicbooking.R

class TimeAdapter (private var context: Context, private var items: ArrayList<String>) : BaseAdapter() {
    private var selectedItemPosition: Int = 0

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

        viewHolder.timeView?.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()
        }

        if(selectedItemPosition == position) {
            viewHolder.timeView?.setBackgroundResource(R.drawable.day_choose)
            viewHolder.timeView?.setTextColor(Color.parseColor("#ffffff"))
        } else {
            viewHolder.timeView?.setBackgroundResource(R.drawable.box_date)
            viewHolder.timeView?.setTextColor(Color.parseColor("#000000"))
        }
        val time = items[position]
        viewHolder.timeView?.text = time
        return view
    }

    override fun getItem(i: Int): String {
        return items[i]
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return items.size
    }

}