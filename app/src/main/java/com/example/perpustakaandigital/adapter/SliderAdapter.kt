package com.example.perpustakaandigital.adapter

import android.content.Context
import android.content.Intent
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.fragment.HomeFragment
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Notif

class SliderAdapter(val context: Context?, val bookList: ArrayList<Book>) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var mCount: Int = 0

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setCount(count: Int) {
        this.mCount = count
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {

        when (position) {
            0 -> {
                viewHolder.textViewTitle.text = bookList[position].judul
                viewHolder.textViewAuthor.text = bookList[position].penulis
                viewHolder.shadowContainer.visibility = View.VISIBLE
                viewHolder.imageGifContainer.visibility = View.GONE
                Glide.with(viewHolder.itemView)
                    .load(bookList[position].gambar)
                    .fitCenter()
                    .placeholder(R.drawable.no_image)
                    .into(viewHolder.imageViewBackground)

                viewHolder.btnPratinjau.setOnClickListener { onItemClickCallback.onItemClicked(bookList[position]) }
            }
            1 -> {
                viewHolder.textViewTitle.text = bookList[position].judul
                viewHolder.textViewAuthor.text = bookList[position].penulis
                viewHolder.shadowContainer.visibility = View.VISIBLE
                viewHolder.imageGifContainer.visibility = View.GONE
                Glide.with(viewHolder.itemView)
                        .load(bookList[position].gambar)
                        .fitCenter()
                        .placeholder(R.drawable.no_image)
                        .into(viewHolder.imageViewBackground)
                viewHolder.btnPratinjau.setOnClickListener { onItemClickCallback.onItemClicked(bookList[position]) }
            }
            2 -> {
                viewHolder.textViewTitle.text = bookList[position].judul
                viewHolder.textViewAuthor.text = bookList[position].penulis
//                viewHolder.textViewDescription.textSize = 16f
//                viewHolder.textViewDescription.setTextColor(Color.WHITE)
                viewHolder.shadowContainer.visibility = View.VISIBLE
                viewHolder.imageGifContainer.visibility = View.GONE
                Glide.with(viewHolder.itemView)
                        .load(bookList[position].gambar)
                        .fitCenter()
                        .placeholder(R.drawable.no_image)
                        .into(viewHolder.imageViewBackground)
                viewHolder.btnPratinjau.setOnClickListener { onItemClickCallback.onItemClicked(bookList[position]) }
            }
            3 -> {
                viewHolder.textViewTitle.text = bookList[position].judul
                viewHolder.textViewAuthor.text = bookList[position].penulis
                viewHolder.btnPratinjau.setOnClickListener {
                    //startActivity(Intent(context, MainActivity::class.java))
                }
                viewHolder.imageGifContainer.visibility = View.GONE
                viewHolder.shadowContainer.visibility = View.VISIBLE
                Glide.with(viewHolder.itemView)
                        .load(bookList[position].gambar)
                        .fitCenter()
                        .placeholder(R.drawable.no_image)
                        .into(viewHolder.imageViewBackground)
                viewHolder.btnPratinjau.setOnClickListener { onItemClickCallback.onItemClicked(bookList[position]) }
            }
/*            else -> {
                viewHolder.textViewDescription.textSize = 29f
                viewHolder.textViewDescription.setTextColor(Color.WHITE)
                viewHolder.textViewDescription.text = ""
                viewHolder.imageGifContainer.visibility = View.VISIBLE
                Glide.with(viewHolder.itemView)
                    .load(R.drawable.puma_offer)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground)
                Glide.with(viewHolder.itemView)
                    .asGif()
                    .load(R.drawable.oh_look_at_this)
                    .into(viewHolder.imageGifContainer)
            }*/
        }

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mCount
    }

    inner class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.imgv_auto_image_slider)
        var imageGifContainer: ImageView = itemView.findViewById(R.id.imgv_gif_container)
        var textViewTitle: TextView = itemView.findViewById(R.id.tv_title_slider)
        var textViewAuthor: TextView = itemView.findViewById(R.id.tv_author_slider)
        var btnPratinjau: Button = itemView.findViewById(R.id.btn_pratinjau_slider)
        var shadowContainer : FrameLayout = itemView.findViewById(R.id.fl_shadow_container)

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Book)
    }
}