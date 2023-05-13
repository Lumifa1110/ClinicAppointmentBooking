package com.example.hyv_hpv_clinicbooking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class BestDoctorAdapter(var mList: List<BacSi>) : RecyclerView.Adapter<BestDoctorAdapter.BestDoctorViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class BestDoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val doctorNameTV : TextView = itemView.findViewById(R.id.doctorName)
        val doctorSpecialistTV : TextView = itemView.findViewById(R.id.doctorSpecialist)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    fun setFilteredList(mList: List<BacSi>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_doctor_item , parent , false)
        return BestDoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestDoctorViewHolder, position: Int) {
        holder.doctorNameTV.text = mList[position].HoTen
        holder.doctorSpecialistTV.text = mList[position].TenChuyenKhoa
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}