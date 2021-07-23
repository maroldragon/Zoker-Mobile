package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.screen.DetailActivity
import com.example.perpustakaandigital.screen.SearchResultActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference
    private var bookList: ArrayList<Book> = arrayListOf()
    private var kategoriMap: MutableMap<String, Int> = mutableMapOf()
    private var dataList: ArrayList<String> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var spinKategori : Spinner
    lateinit var rvKategori : RecyclerView
    lateinit var progressKategori: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_category, container, false)

        llFilter = view.findViewById(R.id.ll_kategori_filter)
        etSearch = view.findViewById(R.id.et_kategori_search_item)
        imgv_filter = view.findViewById(R.id.imgv_kategori_search_filter)
        etAuthorSearch = view.findViewById(R.id.et_kategori_search_author)
        etIsbnSearch = view.findViewById(R.id.et_kategori_search_isbn)
        etPenerbitSearch = view.findViewById(R.id.et_kategori_search_penerbit)
        btnSearch = view.findViewById(R.id.btn_kategori_search)
        spinKategori = view.findViewById(R.id.spin_kategori)
        rvKategori = view.findViewById(R.id.rv_kategori)
        progressKategori = view.findViewById(R.id.progress_kategori)
        imvEmpty = view.findViewById(R.id.imgv_empty_kategori)

        imgv_filter.setOnClickListener {
            if(llFilter.visibility == View.VISIBLE){
                llFilter.visibility = View.GONE
            }else {
                llFilter.visibility = View.VISIBLE
            }
        }

//        dataList.add("Cinta");
//        dataList.add("rahasia")

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

//        var arraySpin : ArrayList<String> = arrayListOf()
//        arraySpin.add("Sa")
//        arraySpin.add("yo")

//        ArrayAdapter.createFromResource(
//                context!!,
//                R.array.kategori_array,
//                android.R.layout.simple_spinner_dropdown_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinKategori.adapter = adapter
//        }

        progressKategori.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvKategori.setHasFixedSize(true)

        spinKategori.onItemSelectedListener = this

        loadDataBukuKategori()
        return view
    }

//    private fun addData(kategori : String) {
//        bookList.clear()
//        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
//                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
//        bookList.add(book)
//        bookList.add(book)
//        showRecyclerKategori(bookList)
//    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(context, spinKategori.getSelectedItem().toString(), Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        progressKategori.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        val bookListFilter: ArrayList<Book> = arrayListOf()
        val kategori = spinKategori.getSelectedItem().toString()
        if(position != 0) {
            for(i in 0..bookList.size-1){
                if(bookList[i].kategori == kategori){
                    bookListFilter.add(bookList[i])
                }
            }
            showRecyclerKategori(bookListFilter)
        }else {
            showRecyclerKategori(bookList);
            //loadDataBukuKategori("")
        }
    }

    private fun showRecyclerKategori(bookListParams: ArrayList<Book>) {
        rvKategori.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

    private fun loadDataBukuKategori(){
        // Get data from firebase
        bookList.clear()

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
                    showRecyclerKategori(bookList)
                    generateKategori()
                    progressKategori.visibility = View.GONE
                }
                else{
                    showRecyclerKategori(bookList)
                    progressKategori.visibility = View.GONE
                    imvEmpty.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun generateKategori(){
        dataList.add("Semua Kategori")
        for(i in 0..bookList.size-1){
            if(kategoriMap[bookList[i].kategori.toString()] == null) {
                dataList.add(bookList[i].kategori.toString())
                kategoriMap.put(bookList[i]?.kategori.toString(), 1)
            }
        }
        var arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, dataList)
        spinKategori.adapter = arrayAdapter
        //Toast.makeText(context, kategoriMap.keys.toString(), Toast.LENGTH_LONG).show()
    }

}