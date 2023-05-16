package com.example.hyv_hpv_clinicbooking.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.UpcomingAppointmentData
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class UpcomingAppointmentAdapter(var mList: List<UpcomingAppointmentData>, var type: Int) : RecyclerView.Adapter<UpcomingAppointmentAdapter.PatientUpcomingAppointmentViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class PatientUpcomingAppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Khai báo firebase storage để lấy ảnh
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null

        val image : ImageView = itemView.findViewById(R.id.doctorAvatar)
        val doctorNameTV : TextView = itemView.findViewById(R.id.upcomingAppointmentDoctor)
        val doctorSpecializeTV : TextView = itemView.findViewById(R.id.upcomingAppointmentSpecialize)
        val appointmentDateTV : TextView = itemView.findViewById(R.id.upcomingAppointmentDate)
        val appointmentTimeTV : TextView = itemView.findViewById(R.id.upcomingAppointmentTime)
        val icon: ImageView = itemView.findViewById(R.id.icon)
        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientUpcomingAppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_appointment_item , parent , false)
        return PatientUpcomingAppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientUpcomingAppointmentViewHolder, position: Int) {
        holder.storage = FirebaseStorage.getInstance();
        holder.storageReference = holder.storage.reference;
        if(type == 0) {
            holder.doctorSpecializeTV.text = mList[position].TenChuyenKhoa
            var ref: StorageReference = holder.storageReference!!.child("BacSi/" + mList[position].MaTaiKhoan)
            ref.downloadUrl
                .addOnSuccessListener { uri ->
                    Picasso.get().load(uri).into(holder.image);
                    Log.d("Test", " Success!")
                }
                .addOnFailureListener {
                    Log.d("Test", " Failed!")
                    holder.image.setImageResource(R.drawable.avatar_doctor_default)
                }
        } else {
            holder.icon.setImageResource(R.drawable.phone_call)
            holder.doctorSpecializeTV.text = mList[position].SoDienThoai
            var ref: StorageReference = holder.storageReference!!.child("BenhNhan/" + mList[position].MaTaiKhoan)
            ref.downloadUrl
                .addOnSuccessListener { uri ->
                    Picasso.get().load(uri).into(holder.image);
                    Log.d("Test", " Success!")
                }
                .addOnFailureListener {
                    Log.d("Test", " Failed!")
                    holder.image.setImageResource(R.drawable.default_avatar)
                }
        }


        holder.doctorNameTV.text = mList[position].HoTen
        holder.appointmentDateTV.text = mList[position].Ngay
        holder.appointmentTimeTV.text = mList[position].ThoiGian
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}