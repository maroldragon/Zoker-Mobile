package com.example.perpustakaandigital.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.perpustakaandigital.fragment.DaftarHalaman1Fragment
import com.example.perpustakaandigital.fragment.DaftarHalaman2Fragment
import java.util.*

class TabsDaftarAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
        DaftarHalaman1Fragment(),
        DaftarHalaman2Fragment()
    )

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Halaman"
            else -> "Bookmark"
        }
    }
}