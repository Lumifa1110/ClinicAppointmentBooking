package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.Thuoc
import com.example.hyv_hpv_clinicbooking.R

class MedicineAdapter(private var context: Context,
                      private var medicineList: ArrayList<Thuoc>
                      ): RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onDeleteClick(doctor: BacSi) { }
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        var nameTV: TextView? = null
        init {
            nameTV = listItemView.findViewById(R.id.itemNameTV)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.added_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicine: Thuoc = medicineList[position]
        holder.nameTV!!.text = medicine.TenThuoc
    }
    override fun getItemCount(): Int {
        return medicineList.size
    }
    fun filter(filterlist: ArrayList<Thuoc>) {
        this.medicineList = filterlist
        notifyDataSetChanged()
    }

}