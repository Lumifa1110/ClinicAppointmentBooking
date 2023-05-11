package com.example.hyv_hpv_clinicbooking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

class ApproveAdapter(private var context: Context,
                     private var approveList: ArrayList<BacSi>
): RecyclerView.Adapter<ApproveAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        var nameTV: TextView? = null
        var phoneTV:TextView? = null
        var emailTV:TextView? = null
        var chuyenKhoaTV: TextView?= null
        init {
            nameTV = listItemView.findViewById(R.id.nameTV)
            phoneTV = listItemView.findViewById(R.id.phoneTV)
            emailTV = listItemView.findViewById(R.id.emailTV)
            chuyenKhoaTV = listItemView.findViewById(R.id.chuyenKhoaTV)
            listItemView.setOnClickListener {
                onItemClick?.invoke(approveList[adapterPosition], adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproveAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(R.layout.approve_ele, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bacSi: BacSi = approveList[position]
        holder.nameTV!!.text = bacSi.HoTen
        holder.phoneTV!!.text = bacSi.SoDienThoai
        holder.emailTV!!.text = bacSi.Email
        holder.chuyenKhoaTV!!.text = bacSi.TenChuyenKhoa
    }

    var onItemClick: ((BacSi, Int) -> Unit)? = null

    override fun getItemCount(): Int {
        return approveList.size
    }
    fun filter(filterlist: ArrayList<BacSi>) {
        this.approveList = filterlist
        notifyDataSetChanged()
    }
}