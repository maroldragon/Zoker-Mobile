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

class KategoriAdapter(val bookList: ArrayList<Book>) : RecyclerView.Adapter<KategoriAdapter.Holder>() {
    private lateinit var onItemClickCallback: KategoriAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val book = bookList[position]

        Glide.with(holder.itemView.context)
                .load(book.gambar)
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(holder.imgPhoto)

        holder.bookTitle.text = book.judul
        holder.bookAuthor.text = book.penulis

        holder.button_pratinjau.setOnClickListener { onItemClickCallback.onItemClicked(bookList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kategory_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imgPhoto: ImageView = itemView!!.findViewById(R.id.imgv_category)
        var bookTitle: TextView = itemView!!.findViewById(R.id.tv_category_name)
        var bookAuthor : TextView = itemView!!.findViewById(R.id.tv_category_author)
        var button_pratinjau : Button = itemView!!.findViewById(R.id.btn_kategori_pratinjau)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Book)
    }

}