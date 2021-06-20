package com.example.perpustakaandigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.perpustakaandigital.model.Notif
import com.example.perpustakaandigital.R

class NotifikasiAdapter(val notifList: ArrayList<Notif>) : RecyclerView.Adapter<NotifikasiAdapter.Holder>() {
    private lateinit var onItemClickCallback: NotifikasiAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val notif = notifList[position]

        holder.judul.text = notif.title
        holder.status.text = notif.status

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(notifList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notifikasi_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return notifList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var judul: TextView = itemView!!.findViewById(R.id.tv_judul_notifikasi)
        var status : TextView = itemView!!.findViewById(R.id.tv_status_notifikasi)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Notif)
    }

}