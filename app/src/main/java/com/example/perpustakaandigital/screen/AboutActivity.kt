package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R

class AboutActivity : AppCompatActivity() {

    lateinit var btnBack : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btnBack = findViewById(R.id.btn_tentang_back)

        btnBack.setOnClickListener {
            finish()
        }
    }
}