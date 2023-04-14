package com.example.hyv_hpv_clinicbooking.Adapter

import BenhNhan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R

class DoctorAppoinmentList(var scheduleList: List<LichHenKham>, var timeList: List<ThoiGian>, var patientList: List<BenhNhan>, ) :
    RecyclerView.Adapter<DoctorAppoinmentList.DoctorAppoimentViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null

    inner class DoctorAppoimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.image)
        val nameTV : TextView = itemView.findViewById(R.id.nameTV)
        val phoneTV: TextView = itemView.findViewById(R.id.phoneTV)

        val dateTV: TextView = itemView.findViewById(R.id.dateTV)
        val timeTV: TextView = itemView.findViewById(R.id.timeTV)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAppoimentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appoinment_doctor_list, parent , false)
        return DoctorAppoimentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorAppoimentViewHolder, position: Int) {
        for (patient in patientList) {
            if (patient.MaBenhNhan == scheduleList[position].MaBenhNhan) {
                holder.image.setImageResource(patient.Image!!)
                holder.nameTV.text = patient.HoTen
                holder.phoneTV.text = "SĐT: " + patient.SoDienThoai
            }
        }
        for (time in timeList) {
            if (time.MaThoiGian == scheduleList[position].MaThoiGian) {
                holder.timeTV.text = "Giờ hẹn: " + time.GioBatDau + " - " + time.GioKetThuc
                holder.dateTV.text = "Ngày hẹn: " + time.Ngay
            }
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

}