package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.DonVi
import com.example.hyv_hpv_clinicbooking.Model.Thuoc
import com.example.hyv_hpv_clinicbooking.R

class UnitAdapter(private var context: Context,
                      private var unitList: ArrayList<DonVi>
): RecyclerView.Adapter<UnitAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onDeleteClick(unit: DonVi) { }
        fun onEditClick(unit: DonVi) { }
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        var nameTV: TextView? = null
        var editItem: ImageButton? = null
        var delItem: ImageButton? = null
        init {
            nameTV = listItemView.findViewById(R.id.itemNameTV)
            editItem = listItemView.findViewById(R.id.editMedorSpec)
            delItem = listItemView.findViewById(R.id.deleteMedorSpec)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.added_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val unit: DonVi = unitList[position]
        holder.nameTV!!.text = unit.tenDonVi
        holder.editItem!!.setOnClickListener {
            Toast.makeText(context, "Edit"+ unit.tenDonVi, Toast.LENGTH_SHORT).show()
            listener?.onEditClick(unit)
        }
        holder.delItem!!.setOnClickListener {
            Toast.makeText(context, "Delete"+unit.tenDonVi, Toast.LENGTH_SHORT).show()
            listener?.onDeleteClick(unit)
        }
    }
    override fun getItemCount(): Int {
        return unitList.size
    }
    fun filter(filterlist: ArrayList<DonVi>) {
        this.unitList = filterlist
        notifyDataSetChanged()
    }
}