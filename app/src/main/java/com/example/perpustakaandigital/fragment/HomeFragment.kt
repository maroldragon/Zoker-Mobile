package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.HomeNewAdapter
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.adapter.SliderAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.screen.DetailActivity
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    lateinit var sliderView : SliderView
    private var bookList: ArrayList<Book> = arrayListOf()
    private var bookList2: ArrayList<Book> = arrayListOf()
    lateinit var rvHomeNew : RecyclerView
    lateinit var progressHomeNew: ProgressBar
    lateinit var imvEmptyHomeNew : ImageView
    lateinit var rvHomeRecommend : RecyclerView
    lateinit var progressHomeRecommend: ProgressBar
    lateinit var imvEmptyHomeRecommend: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        sliderView = view.findViewById(R.id.imageSlider)
        rvHomeNew = view.findViewById(R.id.rv_home_new)
        progressHomeNew= view.findViewById(R.id.progress_home_new)
        imvEmptyHomeNew = view.findViewById(R.id.imgv_empty_home_new)
        rvHomeRecommend = view.findViewById(R.id.rv_home_recommend )
        progressHomeRecommend= view.findViewById(R.id.progress_home_recommend )
        imvEmptyHomeRecommend = view.findViewById(R.id.imgv_empty_home_recommend )

        val adapterSlider = SliderAdapter(this)
        adapterSlider.count = 4
        sliderView.sliderAdapter = adapterSlider
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 5
        sliderView.startAutoCycle()

        addData()
        addData2()
        return view
    }

    private fun addData() {
        val book = Book("123", "Milk And Honey",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList.add(book)
        bookList.add(book)
        bookList.add(book)
        bookList.add(book)
        showRecyclerHomeNew()
    }

    private fun addData2() {
        val book = Book("123", "Milk And Honey",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList2.add(book)
        bookList2.add(book)
        bookList2.add(book)
        bookList2.add(book)
        showRecyclerRecommend()
    }

    private fun showRecyclerHomeNew() {
        rvHomeNew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val homeNewAdapter = HomeNewAdapter(bookList)
        rvHomeNew.adapter = homeNewAdapter

        progressHomeNew.visibility = View.GONE
        if(bookList.size == 0){
            imvEmptyHomeNew.visibility = View.VISIBLE
        }else {
            imvEmptyHomeNew.visibility = View.GONE
        }


        homeNewAdapter.setOnItemClickCallback(object : HomeNewAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

    private fun showRecyclerRecommend() {
        rvHomeRecommend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recommendationAdapter = KategoriAdapter(bookList)
        rvHomeRecommend.adapter = recommendationAdapter

        progressHomeRecommend.visibility = View.GONE
        if(bookList.size == 0){
            imvEmptyHomeRecommend.visibility = View.VISIBLE
        }else {
            imvEmptyHomeRecommend.visibility = View.GONE
        }


        recommendationAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

}