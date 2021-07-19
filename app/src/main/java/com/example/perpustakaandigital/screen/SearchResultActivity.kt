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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SearchResultActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference
    
    var searching : String? = null
    var searchTitle: String? = null
    var searchAuthor: String? = null
    var searchIsbn : String? = null
    var searchPenerbit : String? = null

    private var bookList: ArrayList<Book> = arrayListOf()
    private var bookListFilter: ArrayList<Book> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var rvKategori : RecyclerView
    lateinit var progressSearch: ProgressBar
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
        progressSearch = findViewById(R.id.progress_result)
        imvEmpty = findViewById(R.id.imgv_empty_result)

        searching = intent.getStringExtra("searching")
        searchTitle = intent.getStringExtra("searchTitle")
        searchAuthor = intent.getStringExtra("searchAuthor")
        searchIsbn = intent.getStringExtra("searchIsbn")
        searchPenerbit = intent.getStringExtra("searchPenerbit")
        if(searching == "on") {
            etSearch.setText(searchTitle)
            etAuthorSearch.setText(searchAuthor)
            etIsbnSearch.setText(searchIsbn)
            etPenerbitSearch.setText(searchPenerbit)
        }else {
            etSearch.setText(searchTitle)
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
                    addData()
                } else {
                    Toast.makeText(this, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
                }
                true
            } else false
        })
        progressSearch.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvKategori.setHasFixedSize(true)
        loadDataBukuSearch()
    }

    private fun addData() {
        bookListFilter.clear()
        for(i in 0..bookList.size-1){
            if((bookList[i].judul!!.toLowerCase())!!.contains(etSearch.text.toString().toLowerCase())){
                bookListFilter.add(bookList[i])
            }
        }
        if(bookListFilter.size == 0) {
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
            //Toast.makeText(this@SearchResultActivity, bookList.toString(), Toast.LENGTH_LONG).show()
            showRecyclerResultSearch(bookListFilter)
        }
    }

    private fun addDataKompleks(title : String, author : String, isbn : String, penerbit: String) {
        bookListFilter.clear()
        for(i in 0..bookList.size-1){
            if((bookList[i].judul!!.toLowerCase())!!.contains(title.toLowerCase()) &&
                (bookList[i].penulis!!.toLowerCase())!!.contains(author.toLowerCase()) &&
                (bookList[i].isbn!!.toLowerCase())!!.contains(isbn.toLowerCase()) &&
                (bookList[i].penerbit!!.toLowerCase())!!.contains(penerbit.toLowerCase())){
                bookListFilter.add(bookList[i])
            }
        }
        if(bookListFilter.size == 0) {
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
            //Toast.makeText(this@SearchResultActivity, bookList.toString(), Toast.LENGTH_LONG).show()
            showRecyclerResultSearch(bookListFilter)
        }
    }

    private fun showRecyclerResultSearch(bookListParams: ArrayList<Book>) {
        rvKategori.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val kategoriAdapter = KategoriAdapter(bookListParams)
        rvKategori.adapter = kategoriAdapter

        kategoriAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(this@SearchResultActivity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                intents.putExtra("searchResult", "searchResult")
                startActivity(intents)
            }
        })
    }

    private fun loadDataBukuSearch(){
        // Get data from firebase
        val query: Query = dbRef.child("books")
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
                    if(searching == "on"){
                        addDataKompleks(etSearch.text.toString(), etAuthorSearch.text.toString(), etIsbnSearch.text.toString(), etPenerbitSearch.text.toString())
                    }else {
                        addData()
                    }
                    progressSearch.visibility = View.GONE
                }
                else{
                    progressSearch.visibility = View.GONE
                    imvEmpty.visibility = View.VISIBLE
                }
            }
        })
    }


}