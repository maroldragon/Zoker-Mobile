package com.example.perpustakaandigital.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.NotifikasiAdapter
import com.example.perpustakaandigital.model.Notif
import com.example.perpustakaandigital.screen.DetailActivity
import com.example.perpustakaandigital.screen.DetailNotifActivity
import com.example.perpustakaandigital.screen.SearchResultActivity

class NotificationFragment : Fragment() {
    private var notifList: ArrayList<Notif> = arrayListOf()
    lateinit var etSearch : EditText
    lateinit var imgv_filter : ImageView
    lateinit var llFilter : LinearLayout
    lateinit var etAuthorSearch : EditText
    lateinit var etIsbnSearch : EditText
    lateinit var etPenerbitSearch : EditText
    lateinit var btnSearch : Button
    lateinit var rvNotif : RecyclerView
    lateinit var progressNotif: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        llFilter = view.findViewById(R.id.ll_notif_filter)
        etSearch = view.findViewById(R.id.et_notif_search_item)
        imgv_filter = view.findViewById(R.id.imgv_notif_search_filter)
        etAuthorSearch = view.findViewById(R.id.et_notif_search_author)
        etIsbnSearch = view.findViewById(R.id.et_notif_search_isbn)
        etPenerbitSearch = view.findViewById(R.id.et_notif_search_penerbit)
        btnSearch = view.findViewById(R.id.btn_notif_search)
        rvNotif = view.findViewById(R.id.rv_notifikasi)
        progressNotif = view.findViewById(R.id.progress_notifikasi)
        imvEmpty = view.findViewById(R.id.imgv_empty_notifikasi)

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
        notifList.add(notif)
        showRecyclerNotifikasi()
    }

    private fun showRecyclerNotifikasi() {
        rvNotif.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
                val intents = Intent(activity, DetailNotifActivity::class.java)
                intents.putExtra(DetailNotifActivity.EXTRA_NOTIF, data)
                startActivity(intents)
            }
        })
    }

}