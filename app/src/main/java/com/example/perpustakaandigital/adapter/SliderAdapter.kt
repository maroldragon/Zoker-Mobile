package com.example.perpustakaandigital.adapter

import android.widget.TextView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.perpustakaandigital.fragment.HomeFragment
import com.example.perpustakaandigital.R

class SliderAdapter(private val context: HomeFragment) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var mCount: Int = 0

    fun setCount(count: Int) {
        this.mCount = count
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {

        viewHolder.itemView.setOnClickListener { }

        when (position) {
            0 -> {
                viewHolder.textViewTitle.text = "Love And Live Saemhdasfsdsdfsdfsdfs dfsdfsdfsd sdfsd"
                viewHolder.textViewAuthor.text = "Evan Owen Pasaribu"
//                viewHolder.textViewDescription.textSize = 16f
//                viewHolder.textViewDescription.setTextColor(Color.WHITE)
                viewHolder.shadowContainer.visibility = View.VISIBLE
                viewHolder.imageGifContainer.visibility = View.GONE
                Glide.with(viewHolder.itemView)
                    .load(R.drawable.image_book_sampel)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground)
            }
            1 -> {
                viewHolder.textViewTitle.text = "Love And Live"
                viewHolder.textViewAuthor.text = "Evan Owen"
//                viewHolder.textViewDescription.textSize = 16f
//                viewHolder.textViewDescription.setTextColor(Color.WHITE)
                viewHolder.shadowContainer.visibility = View.VISIBLE
                viewHolder.imageGifContainer.visibility = View.GONE
                Glide.with(viewHolder.itemView)
                    .load(R.drawable.image_book_sampel)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground)
            }
            2 -> {
                viewHolder.textViewTitle.text = "Love And Live"
                viewHolder.textViewAuthor.text = "Evan Owen"
//                viewHolder.textViewDescription.textSize = 16f
//                viewHolder.textViewDescription.setTextColor(Color.WHITE)
                viewHolder.shadowContainer.visibility = View.VISIBLE
                viewHolder.imageGifContainer.visibility = View.GONE
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.image_book_sampel)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground)
            }
            3 -> {
                viewHolder.textViewTitle.text = "Love And Live"
                viewHolder.textViewAuthor.text = "Evan Owen"
//                viewHolder.textViewDescription.textSize = 16f
//                viewHolder.textViewDescription.setTextColor(Color.WHITE)
                viewHolder.imageGifContainer.visibility = View.GONE
                viewHolder.shadowContainer.visibility = View.VISIBLE
                Glide.with(viewHolder.itemView)
                    .load(R.drawable.image_book_sampel)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground)
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
}