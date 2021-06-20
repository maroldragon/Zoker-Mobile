package com.example.perpustakaandigital.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perpustakaandigital.R

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_BOOK = "extra_book"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}