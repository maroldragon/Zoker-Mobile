package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.adapter.KategoriAdapter
import com.example.perpustakaandigital.adapter.RiwayatAdapter
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Peminjaman
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RiwayatActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    private var pinjamList: ArrayList<Peminjaman> = arrayListOf()
    lateinit var btnRiwayatBack : ImageView
    lateinit var rvRiwayat : RecyclerView
    lateinit var progressRiwayat: ProgressBar
    lateinit var imvEmpty : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        btnRiwayatBack = findViewById(R.id.btn_riwayat_back)
        rvRiwayat = findViewById(R.id.rv_riwayat)
        progressRiwayat = findViewById(R.id.progress_riwayat)
        imvEmpty = findViewById(R.id.imgv_empty_riwayat)

        btnRiwayatBack.setOnClickListener {
            finish()
        }

        progressRiwayat.visibility = View.VISIBLE
        imvEmpty.visibility = View.GONE
        rvRiwayat.setHasFixedSize(true)
        loadDataPinjam()

    }

//    private fun addData() {
//        val pinjam =  Pinjam("2323", "134", "10/20/30")
//        pinjamList.add(pinjam)
//        pinjamList.add(pinjam)
//        showRecyclerRiwayat()
//    }

    private fun showRecyclerRiwayat() {
        rvRiwayat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val riwayatAdapter = RiwayatAdapter(pinjamList)
        rvRiwayat.adapter = riwayatAdapter

        progressRiwayat.visibility = View.GONE
        if(pinjamList.size == 0){
            imvEmpty.visibility = View.VISIBLE
        }else {
            imvEmpty.visibility = View.GONE
        }


        riwayatAdapter.setOnItemClickCallback(object : RiwayatAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Peminjaman) {
                Toast.makeText(this@RiwayatActivity, "Peminjaman Sedang di proses", Toast.LENGTH_LONG).show()
//                val intents = Intent(this@RiwayatActivity, DetailActivity::class.java)
//                intents.putExtra(DetailActivity.EXTRA_BOOK, data)
//                startActivity(intents)
            }
        })
    }

    private fun loadDataPinjam(){
        // Get data from firebase
        val idUser = auth.currentUser?.uid
        val query: Query = dbRef.child("peminjaman").orderByChild("idUser").equalTo(idUser)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val pinjam = p.getValue(Peminjaman::class.java)
                        pinjamList.add(pinjam!!)
                    }
                    showRecyclerRiwayat()
                    progressRiwayat.visibility = View.GONE
                }
                else{
                    progressRiwayat.visibility = View.GONE
                    imvEmpty.visibility = View.VISIBLE
                }
            }
        })
    }

}