package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.perpustakaandigital.R

class UlasanActivity : AppCompatActivity() {

    lateinit var btn_back : ImageView
    lateinit var btn_kirim : Button
    lateinit var rating : TextView
    lateinit var image : ImageView
    lateinit var author : TextView
    lateinit var title : TextView
    lateinit var ratingGiven : TextView
    lateinit var textBoxUlasan : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ulasan)

        btn_back = findViewById(R.id.btn_ulasan_back)
        btn_kirim = findViewById(R.id.btn_ulasan_kirim)
        rating = findViewById(R.id.tv_ulasan_rating)
        image = findViewById(R.id.imgv_ulasan)
        author = findViewById(R.id.tv_ulasan_author)
        title = findViewById(R.id.tv_ulasan_title)
        ratingGiven = findViewById(R.id.tv_ulasan_rating_given)
        textBoxUlasan = findViewById(R.id.et_ulasan_given)

        btn_back.setOnClickListener {
            finish()
        }
    }
}