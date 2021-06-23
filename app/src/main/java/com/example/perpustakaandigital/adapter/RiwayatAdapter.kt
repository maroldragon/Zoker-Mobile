package com.example.perpustakaandigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.R

class RiwayatAdapter(val bookList: ArrayList<Book>) : RecyclerView.Adapter<RiwayatAdapter.Holder>() {
    private lateinit var onItemClickCallback: RiwayatAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val book = bookList[position]

        Glide.with(holder.itemView.context)
                .load("https://m.media-amazon.com/images/I/51AUQJVbXJL.jpg")
                .centerCrop()
                .into(holder.imgPhoto)

        holder.bookName.text = book.name
        holder.bookAuthor.text = book.author

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(bookList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.riwayat_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imgPhoto: ImageView = itemView!!.findViewById(R.id.imgv_riwayat)
        var bookName: TextView = itemView!!.findViewById(R.id.tv_riwayat_name)
        var bookAuthor : TextView = itemView!!.findViewById(R.id.tv_riwayat_author)
        var pinjam_lagi : Button = itemView!!.findViewById(R.id.btn_riwayat_pinjamLagi)
        var tanggal : TextView = itemView!!.findViewById(R.id.tv_riwayat_tanggal)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Book)
    }

}