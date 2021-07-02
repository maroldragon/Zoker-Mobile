package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_BOOK = "extra_book"
    }
    var myItem : String? = null

    lateinit var btn_back : ImageView
    lateinit var btn_pinjam_baca : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        myItem = intent.getStringExtra("myItem")
        btn_back = findViewById(R.id.btn_detail_back)
        btn_pinjam_baca = findViewById(R.id.btn_detail_pinjam)

        if(myItem == "on") {
            btn_pinjam_baca.setText("Baca Buku")
        }

        btn_pinjam_baca.setOnClickListener {
            if(myItem == "on"){
                val intent = Intent(this, ReadBookActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Peminjaman Sedang Diproses", Toast.LENGTH_LONG).show()
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}