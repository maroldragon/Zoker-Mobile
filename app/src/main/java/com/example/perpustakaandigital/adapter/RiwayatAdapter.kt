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
import com.example.perpustakaandigital.model.Pinjam

class RiwayatAdapter(val pinjamList: ArrayList<Pinjam>) : RecyclerView.Adapter<RiwayatAdapter.Holder>() {
    private lateinit var onItemClickCallback: RiwayatAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pinjam = pinjamList[position]
        val book = getBookById(pinjam.bookId)

        Glide.with(holder.itemView.context)
                .load(book.gambar)
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(holder.imgPhoto)

        holder.bookName.text = book.judul
        holder.bookAuthor.text = book.penulis
        holder.tanggal.text = pinjam.tanggal

        holder.pinjam_lagi.setOnClickListener { onItemClickCallback.onItemClicked(pinjamList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.riwayat_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return pinjamList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imgPhoto: ImageView = itemView!!.findViewById(R.id.imgv_riwayat)
        var bookName: TextView = itemView!!.findViewById(R.id.tv_riwayat_name)
        var bookAuthor : TextView = itemView!!.findViewById(R.id.tv_riwayat_author)
        var pinjam_lagi : Button = itemView!!.findViewById(R.id.btn_riwayat_pinjamLagi)
        var tanggal : TextView = itemView!!.findViewById(R.id.tv_riwayat_tanggal)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Pinjam)
    }

    fun getBookById(bookId : String?): Book {
        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        return book;
    }

}