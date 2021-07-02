package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R

class DetailNotifActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTIF = "extra_notif"
    }

    lateinit var btn_back : ImageView
    lateinit var btn_beri_ulasan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_notif)

        btn_back = findViewById(R.id.btn_notif_detail_back)
        btn_beri_ulasan = findViewById(R.id.btn_notif_detail_ulasan)

        btn_beri_ulasan.setOnClickListener {
            val intent = Intent(this, UlasanActivity::class.java)
            intent.putExtra("MAIN", "notif")
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}