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

class CategoryFragment : Fragment() {

    private var bookList: ArrayList<Book> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var spinKateogri : Spinner
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
        spinKateogri = view.findViewById(R.id.spin_kategori)
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

        ArrayAdapter.createFromResource(
                context!!,
                R.array.kategori_array,
                android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinKateogri.adapter = adapter
        }

        progressKategori.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvKategori.setHasFixedSize(true)
        addData()

        return view
    }

    private fun addData() {
        val book = Book("123", "Milk And Honey",
            "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList.add(book)
        bookList.add(book)
        showRecyclerKategori()
    }


    private fun showRecyclerKategori() {
        rvKategori.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val kategoriAdapter = KategoriAdapter(bookList)
        rvKategori.adapter = kategoriAdapter

        progressKategori.visibility = View.GONE
        if(bookList.size == 0){
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

}