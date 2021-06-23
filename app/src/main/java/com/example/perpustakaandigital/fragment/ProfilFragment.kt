package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.screen.RiwayatActivity

class ProfilFragment : Fragment() {

    lateinit var imgv_testing : ImageView
    lateinit var btn_riwayat : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        btn_riwayat = view.findViewById(R.id.btn_riwayat)
        btn_riwayat.setOnClickListener {
            startActivity(Intent(context, RiwayatActivity::class.java))
        }

        return view
    }

}