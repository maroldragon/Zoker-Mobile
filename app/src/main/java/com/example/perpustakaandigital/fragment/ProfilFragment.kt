package com.example.perpustakaandigital.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.R

class ProfilFragment : Fragment() {

    lateinit var imgv_testing : ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

//        imgv_testing = view.findViewById(R.id.imgv_testing2)
        val url = "https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/download-show-image-glide-banner.png"
//        Glide.with(this)
//                .load(url)
//                .centerCrop()
////                .placeholder(R.drawable.ic_person)
//                .into(imgv_testing)

        return view
    }

}