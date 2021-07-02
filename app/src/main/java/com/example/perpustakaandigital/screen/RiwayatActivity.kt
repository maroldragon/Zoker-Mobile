package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.adapter.RiwayatAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Pinjam

class RiwayatActivity : AppCompatActivity() {

    private var pinjamList: ArrayList<Pinjam> = arrayListOf()
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
        val pinjam =  Pinjam("2323", "134", "10/20/30")
        pinjamList.add(pinjam)
        pinjamList.add(pinjam)
        showRecyclerRiwayat()
    }

    private fun showRecyclerRiwayat() {
        rvRiwayat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val riwayatAdapter = RiwayatAdapter(pinjamList)
        rvRiwayat.adapter = riwayatAdapter

        progressRiwayat.visibility = View.GONE
        if(pinjamList.size == 0){
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
        }


        riwayatAdapter.setOnItemClickCallback(object : RiwayatAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Pinjam) {
                Toast.makeText(this@RiwayatActivity, "Peminjaman Sedang di proses", Toast.LENGTH_LONG).show()
//                val intents = Intent(this@RiwayatActivity, DetailActivity::class.java)
//                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
//                startActivity(intents)
            }
        })
    }

}