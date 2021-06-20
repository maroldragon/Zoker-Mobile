package com.example.perpustakaandigital.pragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.adapter.NotifikasiAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Notif
import com.example.perpustakaandigital.screen.DetailActivity

class NotificationFragment : Fragment() {
    private var notifList: ArrayList<Notif> = arrayListOf()
    lateinit var rvNotif : RecyclerView
    lateinit var progressNotif: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        rvNotif = view.findViewById(R.id.rv_notifikasi)
        progressNotif = view.findViewById(R.id.progress_notifikasi)
        imvEmpty = view.findViewById(R.id.imgv_empty_notifikasi)

        progressNotif.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvNotif.setHasFixedSize(true)
        addData()

        return view
    }

    private fun addData() {
        val notif = Notif("1234", "Harry Potter And Then",
               "Peminjaman Buku Berhasil", "false")
        notifList.add(notif)
        showRecyclerNotifikasi()
    }

    private fun showRecyclerNotifikasi() {
        rvNotif.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val notifAdapter = NotifikasiAdapter(notifList)
        rvNotif.adapter = notifAdapter

        progressNotif.visibility = View.GONE
        if(notifList.size == 0){
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
        }


        notifAdapter.setOnItemClickCallback(object : NotifikasiAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Notif) {
                val intents = Intent(activity, DetailActivity::class.java)
                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
                startActivity(intents)
            }
        })
    }

}