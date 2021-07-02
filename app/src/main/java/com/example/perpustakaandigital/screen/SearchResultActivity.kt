package com.example.perpustakaandigital.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.model.Book

class SearchResultActivity : AppCompatActivity() {
    var searching : String? = null
    var searchTitle: String? = null
    var searchAuthor: String? = null
    var searchIsbn : String? = null
    var searchPenerbit : String? = null

    private var bookList: ArrayList<Book> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var rvKategori : RecyclerView
    lateinit var progressKategori: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        llFilter = findViewById(R.id.ll_result_filter)
        etSearch = findViewById(R.id.et_result_search_item)
        imgv_filter = findViewById(R.id.imgv_result_search_filter)
        etAuthorSearch = findViewById(R.id.et_result_search_author)
        etIsbnSearch = findViewById(R.id.et_result_search_isbn)
        etPenerbitSearch = findViewById(R.id.et_result_search_penerbit)
        btnSearch = findViewById(R.id.btn_result_search)
        rvKategori = findViewById(R.id.rv_result_search)
        progressKategori = findViewById(R.id.progress_result)
        imvEmpty = findViewById(R.id.imgv_empty_result)

        searching = intent.getStringExtra("searching")
        searchTitle = intent.getStringExtra("searchTitle")
        if(searching == "on") {
            searchAuthor = intent.getStringExtra("searchAuthor")
            searchIsbn = intent.getStringExtra("searchIsbn")
            searchPenerbit = intent.getStringExtra("searchPenerbit")
            addDataKompleks(searchTitle.toString(), searchAuthor.toString(), searchIsbn.toString(), searchPenerbit.toString())
        }else {
            addData(searchTitle.toString())
        }

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
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
                addDataKompleks(etSearch.text.toString(), etAuthorSearch.text.toString(), etIsbnSearch.text.toString(), etPenerbitSearch.text.toString())
            }else {
                Toast.makeText(this, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
            }
        }

        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (etSearch.text.toString() != "") {
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
                    addData(etSearch.text.toString())
                } else {
                    Toast.makeText(this, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
                }
                true
            } else false
        })

        rvKategori.setHasFixedSize(true)

    }

    private fun addData(title: String) {
        bookList.clear()
        progressKategori.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList.add(book)
        bookList.add(book)
        showRecyclerResultSearch(bookList)
    }

    private fun addDataKompleks(title : String, author : String, isbn : String, penerbit: String) {
        bookList.clear()
        progressKategori.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList.add(book)
        bookList.add(book)
        showRecyclerResultSearch(bookList)
    }

    private fun showRecyclerResultSearch(bookListParams: ArrayList<Book>) {
        rvKategori.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val kategoriAdapter = KategoriAdapter(bookListParams)
        rvKategori.adapter = kategoriAdapter

        progressKategori.visibility = View.GONE
        if(bookListParams.size == 0){
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
        }

        kategoriAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(this@SearchResultActivity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                intents.putExtra("searchResult", "searchResult")
                startActivity(intents)
            }
        })
    }


}