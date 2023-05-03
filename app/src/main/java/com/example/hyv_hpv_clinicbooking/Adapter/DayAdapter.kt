package com.example.hyv_hpv_clinicbooking.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.R

class DayAdapter(private val dayList: List<String>) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    var selectedItemPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.ngay_trong_tuan, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    var onItemClick: ((String, Int) -> Unit)? = null
    var onButtonClick: ((String, Int) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var ngayTrongTuan: TextView? = null
        init {
            ngayTrongTuan = listItemView.findViewById(R.id.ngayTrongTuan) as TextView
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val day: String = dayList[position]

        // Set item views based on your views and data model
        holder.ngayTrongTuan?.text = day;
        holder.ngayTrongTuan?.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()

            onButtonClick?.invoke(dayList[position], position)
            onItemClick?.invoke(dayList[position], position)
        }

        if(selectedItemPosition == position) {
            holder.ngayTrongTuan?.setBackgroundResource(R.drawable.day_choose)
            holder.ngayTrongTuan?.setTextColor(Color.parseColor("#ffffff"))
        } else {
            holder.ngayTrongTuan?.setBackgroundResource(R.drawable.day_not_choose)
            holder.ngayTrongTuan?.setTextColor(Color.parseColor("#000000"))

        }
    }


}