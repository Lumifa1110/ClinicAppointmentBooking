package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class DoctorWaitListAdapter (private var context: Context,
                             private var doctorList: ArrayList<BacSi>) :
    RecyclerView.Adapter<DoctorWaitListAdapter.ViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var idTV: TextView? = null
        var nameTV: TextView? = null
        var phoneTV: TextView? = null
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
            idTV = listItemView.findViewById(R.id.userID) as TextView
            nameTV = listItemView.findViewById(R.id.userName) as TextView
            phoneTV = listItemView.findViewById(R.id.userContact) as TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorWaitListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.admin_wait_list, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor: BacSi = doctorList[position]
        holder.idTV?.text = (position + 1).toString()
        holder.nameTV?.text = doctor.HoTen
        holder.phoneTV?.text = doctor.SoDienThoai
    }
    override fun getItemCount(): Int {
        return doctorList.size
    }
    fun filter(filterlist: ArrayList<BacSi>) {
        // below line is to add our filtered
        // list in our course array list.
        doctorList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}