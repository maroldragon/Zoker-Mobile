package com.example.perpustakaandigital.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.perpustakaandigital.R

class ReadBookActivity : AppCompatActivity() {
    lateinit var btn_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        btn_back = findViewById(R.id.btn_read_back)

        btn_back.setOnClickListener {
            finish()
        }
    }
}