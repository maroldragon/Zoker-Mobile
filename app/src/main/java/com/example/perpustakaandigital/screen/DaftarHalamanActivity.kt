package com.example.perpustakaandigital.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.TabsDaftarAdapter
import com.example.perpustakaandigital.fragment.DaftarHalaman1Fragment
import com.google.android.material.tabs.TabLayout

class DaftarHalamanActivity : AppCompatActivity() {
    lateinit var view_pager: ViewPager
//    lateinit var  viewPagerAdapter: TabsDaftarAdapter
    lateinit var tab_layout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_halaman)

        view_pager = findViewById(R.id.view_pager)
        tab_layout = findViewById(R.id.tab_layout)

        view_pager.adapter = TabsDaftarAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)

    }

}