package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.ChuyenKhoa
import com.example.hyv_hpv_clinicbooking.R

class SpecializeAdapter(private var context: Context,
                        private var specializeList: ArrayList<ChuyenKhoa>
                      ): RecyclerView.Adapter<SpecializeAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onDeleteClick(specialize: ChuyenKhoa) { }
        fun onEditClick(specialize: ChuyenKhoa) { }
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializeAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.added_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val specialize: ChuyenKhoa = specializeList[position]
        holder.nameTV!!.text = specialize.tenChuyenKhoa
        holder.editItem!!.setOnClickListener {
            Toast.makeText(context, specialize.tenChuyenKhoa, Toast.LENGTH_SHORT).show()
            listener?.onEditClick(specialize)
        }
        holder.delItem!!.setOnClickListener {
            Toast.makeText(context, specialize.tenChuyenKhoa, Toast.LENGTH_SHORT).show()
            listener?.onDeleteClick(specialize)
        }
    }
    override fun getItemCount(): Int {
        return specializeList.size
    }
    fun filter(filterlist: ArrayList<ChuyenKhoa>) {
        this.specializeList = filterlist
        notifyDataSetChanged()
    }

}