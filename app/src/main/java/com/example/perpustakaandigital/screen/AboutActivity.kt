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
    lateinit var btn_read : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btnBack = findViewById(R.id.btn_tentang_back)
        btn_read = findViewById(R.id.btn_tentang_read)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("profile", "profile")
            startActivity(intent)
        }

        btn_read.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}