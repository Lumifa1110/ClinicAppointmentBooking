package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.ChuyenKhoa
import com.example.hyv_hpv_clinicbooking.R

class SpecializeAdapter(private var context: Context,
                        private var specializeList: ArrayList<ChuyenKhoa>
                      ): RecyclerView.Adapter<SpecializeAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onDeleteClick(chuyenkhoa: ChuyenKhoa) { }
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
        holder.nameTV!!.text = specialize.TenChuyenKhoa
    }
    override fun getItemCount(): Int {
        return specializeList.size
    }
    fun filter(filterlist: ArrayList<ChuyenKhoa>) {
        this.specializeList = filterlist
        notifyDataSetChanged()
    }

}