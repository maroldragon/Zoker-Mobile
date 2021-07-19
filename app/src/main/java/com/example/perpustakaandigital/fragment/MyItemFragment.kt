package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Peminjaman
import com.example.perpustakaandigital.screen.DetailActivity
import com.example.perpustakaandigital.screen.SearchResultActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyItemFragment : Fragment() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference
    private var bookList: ArrayList<Book> = arrayListOf()
    private var dataPinjam: ArrayList<Peminjaman> = arrayListOf()
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
            if(etSearch.text.toString() != "" || etAuthorSearch.text.toString() != "" || etIsbnSearch.text.toString() != ""
                || etPenerbitSearch.text.toString() != ""){

                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("searching", "on")
                intent.putExtra("searchTitle", etSearch.text.toString())
                intent.putExtra("searchAuthor", etAuthorSearch.text.toString())
                intent.putExtra("searchIsbn", etIsbnSearch.text.toString())
                intent.putExtra("searchPenerbit", etPenerbitSearch.text.toString())
                startActivity(intent)
            }else {
                Toast.makeText(context, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
            }
        }

        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (etSearch.text.toString() != "") {
                    val intent = Intent(context, SearchResultActivity::class.java)
                    intent.putExtra("searchTitle", etSearch.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Tuliskan Keyword Pencarian", Toast.LENGTH_LONG).show()
                }
                true
            } else false
        })

        progressMyItem.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvMyItem.setHasFixedSize(true)
        loadDataMyItem()

        return view
    }

//    private fun addData() {
//        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
//                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
//        bookList.add(book)
//        bookList.add(book)
//        showRecyclerMyItem()
//    }

    private fun showRecyclerMyItem() {
        rvMyItem.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val myItemAdapter = KategoriAdapter(bookList)
        rvMyItem.adapter = myItemAdapter

        myItemAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra("myItem", "on")
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

    private fun loadDataMyItem(){
        // Get data from firebase
        bookList.clear()
        val idUser = auth.currentUser?.uid
        val query: Query = dbRef.child("peminjaman").orderByChild("idUser").equalTo(idUser)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val pinjam = p.getValue(Peminjaman::class.java)
                        getBookData(pinjam?.idBuku as String)
                    }
                    progressMyItem.visibility = View.GONE
                }
                else{
                    progressMyItem.visibility = View.GONE
                    imvEmpty.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun getBookData(idBuku: String){
        // Get data from firebase
        val query: Query = dbRef.child("books").orderByChild("isbn").equalTo(idBuku)
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
                    showRecyclerMyItem()
                }
            }
        })
    }

}