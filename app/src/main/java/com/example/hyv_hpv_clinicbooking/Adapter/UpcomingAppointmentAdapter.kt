package com.example.hyv_hpv_clinicbooking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.UpcomingAppointmentData
import com.example.hyv_hpv_clinicbooking.R

class UpcomingAppointmentAdapter(var mList: List<UpcomingAppointmentData>) : RecyclerView.Adapter<UpcomingAppointmentAdapter.PatientUpcomingAppointmentViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class PatientUpcomingAppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val doctorNameTV : TextView = itemView.findViewById(R.id.upcomingAppointmentDoctor)
        val doctorSpecializeTV : TextView = itemView.findViewById(R.id.upcomingAppointmentSpecialize)
        val appointmentDateTV : TextView = itemView.findViewById(R.id.upcomingAppointmentDate)
        val appointmentTimeTV : TextView = itemView.findViewById(R.id.upcomingAppointmentTime)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    fun setFilteredList(mList: List<UpcomingAppointmentData>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientUpcomingAppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_appointment_item , parent , false)
        return PatientUpcomingAppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientUpcomingAppointmentViewHolder, position: Int) {
        holder.doctorNameTV.text = mList[position].HoTen
        holder.doctorSpecializeTV.text = mList[position].TenChuyenKhoa
        holder.appointmentDateTV.text = mList[position].Ngay
        holder.appointmentTimeTV.text = mList[position].ThoiGian
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}