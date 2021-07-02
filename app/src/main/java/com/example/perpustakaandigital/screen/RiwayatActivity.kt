package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.adapter.RiwayatAdapter
import com.example.perpustakaandigital.model.Book

class RiwayatActivity : AppCompatActivity() {

    private var bookList: ArrayList<Book> = arrayListOf()
    lateinit var btnRiwayatBack : ImageView
    lateinit var rvRiwayat : RecyclerView
    lateinit var progressRiwayat: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        btnRiwayatBack = findViewById(R.id.btn_riwayat_back)
        rvRiwayat = findViewById(R.id.rv_riwayat)
        progressRiwayat = findViewById(R.id.progress_riwayat)
        imvEmpty = findViewById(R.id.imgv_empty_riwayat)

        btnRiwayatBack.setOnClickListener {
            finish()
        }

        progressRiwayat.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvRiwayat.setHasFixedSize(true)
        addData()

    }

    private fun addData() {
        val book = Book("123", "Milk And Honey","132423423423", "1923", "Erlangga","4.5",
                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1534&q=80", "Love", "Evan Owen", "This talk about love and live")
        bookList.add(book)
        bookList.add(book)
        showRecyclerRiwayat()
    }

    private fun showRecyclerRiwayat() {
        rvRiwayat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val riwayatAdapter = RiwayatAdapter(bookList)
        rvRiwayat.adapter = riwayatAdapter

        progressRiwayat.visibility = View.GONE
        if(bookList.size == 0){
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
        }


        riwayatAdapter.setOnItemClickCallback(object : RiwayatAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                val intents = Intent(this@RiwayatActivity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

}