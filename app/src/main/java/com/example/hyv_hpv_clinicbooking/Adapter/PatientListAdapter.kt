package com.example.hyv_hpv_clinicbooking.Adapter

import BenhNhan
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PatientListAdapter(private var context: Context,
                         private var patientList: ArrayList<BenhNhan>) :
    RecyclerView.Adapter<PatientListAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onDeleteClick(patient: BenhNhan) { }
        fun onBanClick(patient: BenhNhan) { }
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
        // Set item views based on your views and data model
        holder.idTV?.text = (position + 1).toString()
        holder.nameTV?.text = patient.HoTen
        holder.phoneTV?.text = patient.SoDienThoai

        holder.storage = FirebaseStorage.getInstance();
        holder.storageReference = holder.storage.reference;
        var ref: StorageReference = holder.storageReference!!.child("BenhNhan/" + patient.MaBenhNhan)
        ref.downloadUrl
            .addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(holder.avatar);
                Log.d("Test", " Success!")
            }
            .addOnFailureListener {
                Log.d("Test", " Failed!")
            }
//        holder.avatar?.setImageResource(patient.Image)

        if (patient.BiKhoa) {
            holder.isLock?.setImageResource(R.drawable.block_user)
        } else {
            holder.isLock?.setImageResource(R.drawable.unlock_user)
        }

        holder.isLock?.setOnClickListener {
            listener!!.onBanClick(patient)
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
        lateinit var storage: FirebaseStorage
        var storageReference: StorageReference? = null
        private var filePath: Uri? = null
        private val PICK_IMAGE_REQUEST = 71

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

