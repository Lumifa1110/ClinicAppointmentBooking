package com.example.hyv_hpv_clinicbooking.Adapter

import BenhNhan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class DoctorAppoinmentList(var scheduleList: List<CuocHen>, var patientList: List<BenhNhan>) :
    RecyclerView.Adapter<DoctorAppoinmentList.DoctorAppoimentViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null

    inner class DoctorAppoimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Khai báo firebase storage để lấy ảnh
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null

        //Khai báo biến hiển thị
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
//                holder.image.setImageResource(patient.Image!!)
                holder.nameTV.text = patient.HoTen
                holder.phoneTV.text = "SĐT: " + patient.SoDienThoai

                holder.storage = FirebaseStorage.getInstance();
                holder.storageReference = holder.storage.reference;
                var ref: StorageReference = holder.storageReference!!.child("BenhNhan/" + patient.MaBenhNhan)

                ref.downloadUrl
                    .addOnSuccessListener { uri ->
                        Picasso.get().load(uri).into(holder.image);
                        Log.d("Test", " Success!")
                    }
                    .addOnFailureListener {
                        Log.d("Test", " Failed!")
                    }
            }
        }
        holder.timeTV.text = "Giờ hẹn: " + scheduleList[position].GioBatDau + " - " + scheduleList[position].GioKetThuc
        holder.dateTV.text = "Ngày hẹn: " + scheduleList[position].Ngay
        if(scheduleList[position].MaTrangThai == 1) {
            Log.w("hung", scheduleList[position].GioBatDau + " - " + scheduleList[position].GioKetThuc +", " + scheduleList[position].Ngay)
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

}