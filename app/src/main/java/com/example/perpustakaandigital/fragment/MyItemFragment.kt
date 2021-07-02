package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.screen.DetailActivity
import com.example.perpustakaandigital.screen.SearchResultActivity

class MyItemFragment : Fragment() {
    private var bookList: ArrayList<Book> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var rvMyItem : RecyclerView
    lateinit var progressMyItem: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_item, container, false)

        llFilter = view.findViewById(R.id.ll_myitem_filter)
        etSearch = view.findViewById(R.id.et_myitem_search_item)
        imgv_filter = view.findViewById(R.id.imgv_myitem_search_filter)
        etAuthorSearch = view.findViewById(R.id.et_myitem_search_author)
        etIsbnSearch = view.findViewById(R.id.et_myitem_search_isbn)
        etPenerbitSearch = view.findViewById(R.id.et_myitem_search_penerbit)
        btnSearch = view.findViewById(R.id.btn_myitem_search)
        rvMyItem = view.findViewById(R.id.rv_myItem)
        progressMyItem = view.findViewById(R.id.progress_myItem)
        imvEmpty = view.findViewById(R.id.imgv_empty_myItem)

        imgv_filter.setOnClickListener {
            if(llFilter.visibility == View.VISIBLE){
                llFilter.visibility = View.GONE
            }else {
                llFilter.visibility = View.VISIBLE
            }
        }

        btnSearch.setOnClickListener {
            startActivity(Intent(context, SearchResultActivity::class.java))
        }

        progressMyItem.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvMyItem.setHasFixedSize(true)
        addData()

        return view
    }

    private fun addData() {
        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList.add(book)
        bookList.add(book)
        showRecyclerMyItem()
    }

    private fun showRecyclerMyItem() {
        rvMyItem.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val myItemAdapter = KategoriAdapter(bookList)
        rvMyItem.adapter = myItemAdapter

        progressMyItem.visibility = View.GONE
        if(bookList.size == 0){
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
        }

        myItemAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra("myItem", "on")
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }
}