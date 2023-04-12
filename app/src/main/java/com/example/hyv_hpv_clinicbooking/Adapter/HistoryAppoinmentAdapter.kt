package com.example.hyv_hpv_clinicbooking.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.R

@RequiresApi(Build.VERSION_CODES.O)
class HistoryAppoinmentAdapter(var doctorList: List<BacSi>, var appoinmentList: List<KeDon>) :
    RecyclerView.Adapter<HistoryAppoinmentAdapter.HistoryAppoimentViewHolder>() {

    inner class HistoryAppoimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.image)
        val nameTV : TextView = itemView.findViewById(R.id.name)
        val specialistTV: TextView = itemView.findViewById(R.id.specialist)

        val dateTV: TextView = itemView.findViewById(R.id.date)
        val timeTV: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAppoimentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_list , parent , false)
        return HistoryAppoimentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAppoimentViewHolder, position: Int) {
//        holder.dateTV.text = appoinmentList[position].NgayGio?.toLocalDate().toString()
//        holder.timeTV.text =  appoinmentList[position].NgayGio?.hour.toString() + " giờ "  +appoinmentList[position].NgayGio?.minute.toString() + " phút"

        holder.dateTV.text = appoinmentList[position].Ngay
        holder.timeTV.text =  appoinmentList[position].Gio
        for(doctor in doctorList) {
            if(doctor.MaBacSi == appoinmentList[position].MaBacSi) {
                holder.image.setImageResource(doctor.Image!!)
                holder.nameTV.text = doctor.HoTen
                holder.specialistTV.text = doctor.TenChuyenKhoa
            }
        }

    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

}


