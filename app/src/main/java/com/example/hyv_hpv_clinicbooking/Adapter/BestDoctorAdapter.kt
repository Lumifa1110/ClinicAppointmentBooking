package com.example.hyv_hpv_clinicbooking.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class BestDoctorAdapter(var mList: List<BacSi>) : RecyclerView.Adapter<BestDoctorAdapter.BestDoctorViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class BestDoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Khai báo firebase storage để lấy ảnh
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null

        val image : ImageView = itemView.findViewById(R.id.doctorAvatar)
        val doctorNameTV : TextView = itemView.findViewById(R.id.doctorName)
        val doctorSpecialistTV : TextView = itemView.findViewById(R.id.doctorSpecialist)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_doctor_item , parent , false)
        return BestDoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestDoctorViewHolder, position: Int) {
        holder.doctorNameTV.text = mList[position].HoTen
        holder.doctorSpecialistTV.text = mList[position].TenChuyenKhoa

        holder.storage = FirebaseStorage.getInstance();
        holder.storageReference = holder.storage.reference;
        var ref: StorageReference = holder.storageReference!!.child("BacSi/" + mList[position].MaBacSi)

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

    override fun getItemCount(): Int {
        return mList.size
    }
}