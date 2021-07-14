package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.HomeNewAdapter
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.adapter.SliderAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.screen.DetailActivity
import com.example.perpustakaandigital.screen.SearchResultActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    lateinit var sliderView : SliderView
    private var bookList: ArrayList<Book> = arrayListOf()
    private var bookListRecomd: ArrayList<Book> = arrayListOf()
    private var bookListSlider: ArrayList<Book> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
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

        llFilter = view.findViewById(R.id.ll_home_filter)
        etSearch = view.findViewById(R.id.et_home_search_item)
        imgv_filter = view.findViewById(R.id.imgv_home_search_filter)
        etAuthorSearch = view.findViewById(R.id.et_home_search_author)
        etIsbnSearch = view.findViewById(R.id.et_home_search_isbn)
        etPenerbitSearch = view.findViewById(R.id.et_home_search_penerbit)
        btnSearch = view.findViewById(R.id.btn_home_search)
        sliderView = view.findViewById(R.id.imageSlider)
        rvHomeNew = view.findViewById(R.id.rv_home_new)
        progressHomeNew= view.findViewById(R.id.progress_home_new)
        imvEmptyHomeNew = view.findViewById(R.id.imgv_empty_home_new)
        rvHomeRecommend = view.findViewById(R.id.rv_home_recommend )
        progressHomeRecommend= view.findViewById(R.id.progress_home_recommend )
        imvEmptyHomeRecommend = view.findViewById(R.id.imgv_empty_home_recommend )

        imgv_filter.setOnClickListener {
            if(llFilter.visibility == View.VISIBLE){
                llFilter.visibility = View.GONE
            }else {
                llFilter.visibility = View.VISIBLE
            }
        }

        btnSearch.setOnClickListener {
            if(etSearch.text.toString() != "" || etAuthorSearch.text.toString() != "" || etIsbnSearch.text.toString() != ""
                    || etPenerbitSearch.text.toString() != ""){

                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("searching", "on")
                intent.putExtra("searchTitle", etSearch.text)
                intent.putExtra("searchAuthor", etAuthorSearch.text)
                intent.putExtra("searchIsbn", etIsbnSearch.text)
                intent.putExtra("searchPenerbit", etPenerbitSearch.text)
                startActivity(intent)
            }else {
                Toast.makeText(context, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
            }
        }

        etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(etSearch.text.toString() != "") {
                    val intent = Intent(context, SearchResultActivity::class.java)
                    intent.putExtra("searchTitle", etSearch.text.toString())
                    startActivity(intent)
                }else {
                    Toast.makeText(context, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
                }
                true
            } else false
        })

        progressHomeRecommend.visibility = View.VISIBLE
        imvEmptyHomeRecommend.visibility = View.GONE
        rvHomeRecommend.setHasFixedSize(true)
        progressHomeNew.visibility = View.VISIBLE
        imvEmptyHomeNew.visibility = View.GONE
        rvHomeNew.setHasFixedSize(true)
        loadDataSlider()
        loadDataBukuNew()
        loadDataBuku()
        return view
    }

//    private fun addData() {
//        bookList.clear()
//        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
//                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
//        bookList.add(book)
//        bookList.add(book)
//        bookList.add(book)
//        bookList.add(book)
//        showRecyclerHomeNew()
//    }

//    private fun addData2() {
//        bookListRecomd.clear()
//        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
//                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
//        bookListRecomd.add(book)
//        bookListRecomd.add(book)
//        bookListRecomd.add(book)
//        bookListRecomd.add(book)
//        showRecyclerRecommend()
//    }

//    private fun addDataSlider() {
//        bookListSlider.clear()
//        val book = Book("123", "Milk And Honey","132423423423.pdf", "1923", "Erlangga","4.5",
//                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
//        bookListSlider.add(book)
//        bookListSlider.add(book)
//        bookListSlider.add(book)
//        bookListSlider.add(book)
//        showSlider()
//    }

    private fun showSlider() {
        val adapterSlider = SliderAdapter(context, bookListSlider)
        adapterSlider.count = 4
        sliderView.sliderAdapter = adapterSlider
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 5

        adapterSlider.setOnItemClickCallback(object : SliderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })

        sliderView.startAutoCycle()
    }

    private fun showRecyclerHomeNew() {
        rvHomeNew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val homeNewAdapter = HomeNewAdapter(bookList)
        rvHomeNew.adapter = homeNewAdapter

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
        val recommendationAdapter = KategoriAdapter(bookListRecomd)
        rvHomeRecommend.adapter = recommendationAdapter

        recommendationAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

    // Fungsi get data rs dari database
    private fun loadDataBuku(){
        // Get data from firebase
        val query: Query = dbRef.child("books").orderByChild("rating").limitToLast(5)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val book = p.getValue(Book::class.java)
                        bookListRecomd.add(book!!)
                    }
                    showRecyclerRecommend()
                    progressHomeRecommend.visibility = View.GONE
                }
                else{
                    progressHomeRecommend.visibility = View.GONE
                    imvEmptyHomeRecommend.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadDataBukuNew(){
        // Get data from firebase
        val query: Query = dbRef.child("books").limitToFirst(5)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val book = p.getValue(Book::class.java)
                        bookList.add(book!!)
                    }
                    showRecyclerHomeNew()
                    progressHomeNew.visibility = View.GONE
                }
                else{
                    progressHomeNew.visibility = View.GONE
                    imvEmptyHomeNew.visibility = View.VISIBLE
                }
            }
        })
    }

    // Fungsi get data rs dari database
    private fun loadDataSlider(){
        // Get data from firebase
        val query: Query = dbRef.child("books").orderByChild("rating").limitToLast(4)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val book = p.getValue(Book::class.java)
                        bookListSlider.add(book!!)
                    }
                    showSlider()
                }
                else{
                }
            }
        })
    }


}