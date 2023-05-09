package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.DoctorDetailPage
import com.example.hyv_hpv_clinicbooking.Activity.EditProfilePage
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class DoctorListAdapter_Admin(private var context: Context,
                              private var doctorList: ArrayList<BacSi>) :
    RecyclerView.Adapter<DoctorListAdapter_Admin.ViewHolder>() {
//    var lock: Boolean = true
    interface OnItemClickListener {
        fun onDeleteClick(doctor: BacSi) { }
        fun onBanClick(doctor: BacSi) { }
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var idTV: TextView? = null
        var nameTV: TextView? = null
        var phoneTV: TextView? = null
        var avatar: CircleImageView? = null
        var isLock: ImageButton? = null
        var delUser: ImageButton? = null
        var editUser: ImageButton? = null
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null

        init {
            idTV = listItemView.findViewById(R.id.userID) as TextView
            nameTV = listItemView.findViewById(R.id.userName) as TextView
            phoneTV = listItemView.findViewById(R.id.userContact) as TextView
            avatar = listItemView.findViewById(R.id.userAvatar)
            isLock = listItemView.findViewById(R.id.isLock) as ImageButton
            delUser = listItemView.findViewById(R.id.deleteUser)
            editUser = listItemView.findViewById(R.id.editUser)
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

        holder.storage = FirebaseStorage.getInstance();
        holder.storageReference = holder.storage.reference;
        var ref: StorageReference = holder.storageReference!!.child("BacSi/" + doctor.MaBacSi)

        ref.downloadUrl
            .addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(holder.avatar);
                Log.d("Test", " Success!")
            }
            .addOnFailureListener {
                Log.d("Test", " Failed!")
            }

//        holder.avatar?.setImageResource(doctor.Image)
//        lock = doctor.BiKhoa
        if (doctor.BiKhoa) {
            holder.isLock?.setImageResource(R.drawable.block_user)
        } else {
            holder.isLock?.setImageResource(R.drawable.unlock_user)
        }
        holder.isLock?.setOnClickListener {
            listener?.onBanClick(doctor)
        }
        holder.editUser!!.setOnClickListener{
            val intent = Intent(context, EditProfilePage::class.java)
            intent.putExtra("loaiTaiKhoan", "adminBacSi")
            intent.putExtra("taiKhoan", doctor)
            context.startActivity(intent)
        }

        holder.delUser!!.setOnClickListener {
            listener?.onDeleteClick(doctor)
            Toast.makeText(context, doctor.HoTen, Toast.LENGTH_SHORT).show()
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