package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class HistoryAppoinmentAdapter(var appoinmentList: List<CuocHen>, var doctorList: List<BacSi>) :
    RecyclerView.Adapter<HistoryAppoinmentAdapter.HistoryAppoimentViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null

    inner class HistoryAppoimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Khai báo firebase storage để lấy ảnh
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null

        //Khai báo biến hiển thị
        val image : ImageView = itemView.findViewById(R.id.image)
        val nameTV : TextView = itemView.findViewById(R.id.nameTV)
        val specialistTV: TextView = itemView.findViewById(R.id.specialistTV)

        val dateTV: TextView = itemView.findViewById(R.id.dateTV)
        val timeTV: TextView = itemView.findViewById(R.id.timeTV)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAppoimentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appoiment_list, parent , false)
        return HistoryAppoimentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAppoimentViewHolder, position: Int) {
        for (doctor in doctorList) {
            if (doctor.MaBacSi == appoinmentList[position].MaBacSi) {
//                holder.image.setImageResource(doctor.Image!!)
                holder.nameTV.text = doctor.HoTen
                holder.specialistTV.text = "Chuyên ngành: " + doctor.TenChuyenKhoa

                holder.storage = FirebaseStorage.getInstance();
                holder.storageReference = holder.storage.reference;
                var ref: StorageReference = holder.storageReference!!.child("BacSi/" + doctor.MaBacSi)

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
        holder.timeTV.text = "Giờ hẹn: " + appoinmentList[position].GioBatDau + " - " + appoinmentList[position].GioKetThuc
        holder.dateTV.text = "Ngày hẹn: " + appoinmentList[position].Ngay
    }

    override fun getItemCount(): Int {
        return appoinmentList.size
    }

}