package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DonThuoc
import com.example.hyv_hpv_clinicbooking.R

class PrescriptionAdapter(var mList: List<DonThuoc>) :
    RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {
    inner class PrescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV : TextView = itemView.findViewById(R.id.nameTV)
        val amountTV : TextView = itemView.findViewById(R.id.amountTV)
        val usingTV: TextView = itemView.findViewById(R.id.usingTV)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prescription_list , parent , false)
        return PrescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        holder.nameTV.text = mList[position].TenThuoc
        holder.amountTV.text = mList[position].SoLuong.toString() + " " + mList[position].DonVi
        holder.usingTV.text = mList[position].CachDung
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
