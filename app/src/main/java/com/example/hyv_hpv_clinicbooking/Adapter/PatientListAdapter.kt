package com.example.hyv_hpv_clinicbooking.Adapter

import BenhNhan
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import de.hdodenhof.circleimageview.CircleImageView

class PatientListAdapter(private var context: Context,
                         private var patientList: ArrayList<BenhNhan>) :
    RecyclerView.Adapter<PatientListAdapter.ViewHolder>() {
    var lock: Boolean = true
    interface OnItemClickListener {
        fun onDeleteClick(patient: BenhNhan) { }
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
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
        lock = patient.BiKhoa
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
        holder.delUser!!.setOnClickListener {
            listener?.onDeleteClick(patient)
            Toast.makeText(context, patient.HoTen, Toast.LENGTH_SHORT).show()
        }
    }


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var idTV: TextView? = null
        var nameTV: TextView? = null
        var phoneTV: TextView? = null
        var avatar: CircleImageView? = null
        var isLock: ImageButton? = null
        var delUser: ImageButton? = null

        init {
            idTV = listItemView.findViewById(R.id.idTV) as TextView
            nameTV = listItemView.findViewById(R.id.nameTV) as TextView
            phoneTV = listItemView.findViewById(R.id.phoneTV) as TextView
            avatar = listItemView.findViewById(R.id.avatar) as CircleImageView
            isLock = listItemView.findViewById(R.id.isLock) as ImageButton
            delUser = listItemView.findViewById(R.id.deleteUser)
        }
    }
    fun filterList(filterlist: ArrayList<BenhNhan>) {
        // below line is to add our filtered
        // list in our course array list.
        patientList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}

