package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class DoctorListAdapter_Admin(private var doctorList: List<BacSi>) :
    RecyclerView.Adapter<DoctorListAdapter_Admin.ViewHolder>() {
    var lock: Boolean = true
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var idTV: TextView? = null
        var nameTV: TextView? = null
        var phoneTV: TextView? = null
        var avatar: ImageView? = null
        var isLock: ImageButton? = null

        init {
            idTV = listItemView.findViewById(R.id.userID) as TextView
            nameTV = listItemView.findViewById(R.id.userName) as TextView
            phoneTV = listItemView.findViewById(R.id.userContact) as TextView
            avatar = listItemView.findViewById(R.id.userAvatar) as ImageView
            isLock = listItemView.findViewById(R.id.isLock) as ImageButton
        }
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int
    ): DoctorListAdapter_Admin.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.admin_doctor_list, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    override fun onBindViewHolder(
        holder: DoctorListAdapter_Admin.ViewHolder,
        position: Int
    ) {
        val doctor: BacSi = doctorList[position]
        holder.idTV?.text = (position + 1).toString()
        holder.nameTV?.text = doctor.HoTen
        holder.phoneTV?.text = doctor.SoDienThoai
        holder.avatar?.setImageResource(R.drawable.avatar)

        holder.isLock?.setOnClickListener {
            if (lock == true) {
                holder.isLock?.setImageResource(R.drawable.unlock_user)
                lock = false
            } else {
                holder.isLock?.setImageResource(R.drawable.block_user)
                lock = true
            }
        }
    }
    fun filterList(filterlist: ArrayList<BacSi>) {
        // below line is to add our filtered
        // list in our course array list.
        doctorList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}