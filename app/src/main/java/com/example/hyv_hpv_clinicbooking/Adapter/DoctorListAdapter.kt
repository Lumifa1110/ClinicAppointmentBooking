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

class DoctorListAdapter(var mList: List<BacSi>) : RecyclerView.Adapter<DoctorListAdapter.DoctorViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Khai báo firebase storage để lấy ảnh
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null

        //Khai báo biến hiển thị
        val image : ImageView = itemView.findViewById(R.id.image)
        val nameTV : TextView = itemView.findViewById(R.id.nameTV)
        val specialistTV: TextView = itemView.findViewById(R.id.specialistTV)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    fun setFilteredList(mList: List<BacSi>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_list , parent , false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
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
            }

        holder.nameTV.text = mList[position].HoTen
        holder.specialistTV.text = mList[position].TenChuyenKhoa
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}