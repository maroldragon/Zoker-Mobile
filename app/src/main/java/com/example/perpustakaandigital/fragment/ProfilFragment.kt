package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.screen.AboutActivity
import com.example.perpustakaandigital.screen.ProfileMemberActivity
import com.example.perpustakaandigital.screen.RiwayatActivity
import com.example.perpustakaandigital.screen.SearchResultActivity

class ProfilFragment : Fragment() {

    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var btn_lihat_profile : Button
    lateinit var btn_riwayat : Button
    lateinit var btn_kontak : Button
    lateinit var btn_tentang : Button
    lateinit var btn_keluar : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        llFilter = view.findViewById(R.id.ll_profile_filter)
        etSearch = view.findViewById(R.id.et_profile_search_item)
        imgv_filter = view.findViewById(R.id.imgv_profile_search_filter)
        etAuthorSearch = view.findViewById(R.id.et_profile_search_author)
        etIsbnSearch = view.findViewById(R.id.et_profile_search_isbn)
        etPenerbitSearch = view.findViewById(R.id.et_profile_search_penerbit)
        btnSearch = view.findViewById(R.id.btn_profile_search)
        btn_lihat_profile = view.findViewById(R.id.btn_lihat_profil)
        btn_riwayat = view.findViewById(R.id.btn_riwayat)
        btn_kontak = view.findViewById(R.id.btn_kontak)
        btn_tentang = view.findViewById(R.id.btn_tentang)
        btn_keluar = view.findViewById(R.id.btn_keluar)

        imgv_filter.setOnClickListener {
            if(llFilter.visibility == View.VISIBLE){
                llFilter.visibility = View.GONE
            }else {
                llFilter.visibility = View.VISIBLE
            }
        }

        btnSearch.setOnClickListener {
            startActivity(Intent(context, SearchResultActivity::class.java))
        }
        btn_lihat_profile.setOnClickListener {
            startActivity(Intent(context, ProfileMemberActivity::class.java))
        }
        btn_riwayat.setOnClickListener {
            startActivity(Intent(context, RiwayatActivity::class.java))
        }
        btn_kontak.setOnClickListener {
            startActivity(Intent(context, RiwayatActivity::class.java))
        }
        btn_tentang.setOnClickListener {
            startActivity(Intent(context, AboutActivity::class.java))
        }
        btn_keluar.setOnClickListener {
            startActivity(Intent(context, RiwayatActivity::class.java))
        }

        return view
    }

}