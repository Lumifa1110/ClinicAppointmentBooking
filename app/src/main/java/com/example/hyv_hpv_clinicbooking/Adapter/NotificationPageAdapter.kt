package com.example.hyv_hpv_clinicbooking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.ThongBao
import com.example.hyv_hpv_clinicbooking.R

class NotificationPageAdapter(private val notificationList: List<ThongBao>) :
    RecyclerView.Adapter<NotificationPageAdapter.ViewHolder>() {
    private var selectedItemPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationPageAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.notification_list, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var noiDungTV: TextView? = null
        init {
            noiDungTV = listItemView.findViewById(R.id.notificationContent) as TextView
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val thongbao: ThongBao = notificationList[position]
        val noiDung = thongbao.NoiDung
        holder.noiDungTV!!.text = noiDung
    }


}