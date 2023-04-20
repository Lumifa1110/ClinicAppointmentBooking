package com.example.hyv_hpv_clinicbooking.Adapter

import BenhNhan
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.R
import de.hdodenhof.circleimageview.CircleImageView

class PatientListAdapter(private val patientList: List<BenhNhan>) :
    RecyclerView.Adapter<PatientListAdapter.ViewHolder>() {
    var lock: Boolean = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.admin_patient_list, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return patientList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val patient: BenhNhan = patientList[position]

        // Set item views based on your views and data model
        holder.idTV?.text = (position + 1).toString()
        holder.nameTV?.text = patient.HoTen
        holder.phoneTV?.text = patient.SoDienThoai
        holder.avatar?.setImageResource(R.drawable.anya)

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


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var idTV: TextView? = null
        var nameTV: TextView? = null
        var phoneTV: TextView? = null
        var avatar: CircleImageView? = null
        var isLock: ImageButton? = null

        init {
            idTV = listItemView.findViewById(R.id.idTV) as TextView
            nameTV = listItemView.findViewById(R.id.nameTV) as TextView
            phoneTV = listItemView.findViewById(R.id.phoneTV) as TextView
            avatar = listItemView.findViewById(R.id.avatar) as CircleImageView
            isLock = listItemView.findViewById(R.id.isLock) as ImageButton
        }
    }
}

