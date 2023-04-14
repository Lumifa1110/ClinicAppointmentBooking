package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Activity.Infor
import com.example.hyv_hpv_clinicbooking.R


class DoctorDetailAdapter(private var context: Context, private var items: ArrayList<Infor>) : BaseAdapter(){
    private class ViewHolder(row: View?) {
        var titleTV: TextView?= null
        var amountTV: TextView?= null
        var iconIV: ImageView?= null
        init {
            titleTV = row?.findViewById(R.id.titleTV)
            amountTV = row?.findViewById(R.id.nameTV)
            iconIV = row?.findViewById(R.id.iconIV)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null) {
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.infor_doctor_list, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var infor = items[position]
        viewHolder.titleTV?.text = infor.title
        viewHolder.amountTV?.text = infor.amount
        viewHolder.iconIV?.setImageResource(infor.icon!!)

        return view as View
    }


    override fun getItem(i: Int): Any {
        return items[i]
    }

    override  fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}