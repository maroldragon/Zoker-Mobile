package com.example.perpustakaandigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.R

class HomeNewAdapter(val bookList: ArrayList<Book>) : RecyclerView.Adapter<HomeNewAdapter.Holder>() {
    private lateinit var onItemClickCallback: HomeNewAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val book = bookList[position]

        Glide.with(holder.itemView.context)
                .load("https://koenig-media.raywenderlich.com/uploads/2019/05/Screenshot_1557010833-281x500.png")
                .centerCrop()
                .into(holder.imgPhoto)

        holder.bookName.text = book.name
        holder.bookAuthor.text = book.author

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(bookList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_new_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imgPhoto: ImageView = itemView!!.findViewById(R.id.imgv_home_new)
        var rating: TextView = itemView!!.findViewById(R.id.tv_rating_home_new)
        var bookName: TextView = itemView!!.findViewById(R.id.tv_home_new_name)
        var bookAuthor : TextView = itemView!!.findViewById(R.id.tv_home_new_author)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Book)
    }

}