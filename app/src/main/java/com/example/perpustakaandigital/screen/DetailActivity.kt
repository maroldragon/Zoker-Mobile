package com.example.perpustakaandigital.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Peminjaman
import com.example.perpustakaandigital.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference
    companion object {
        const val EXTRA_BOOK = "extra_book"
    }
    var myItem : String? = null

    lateinit var btn_back : ImageView
    lateinit var btn_pinjam_baca : Button
    lateinit var btn_ulasan : Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        myItem = intent.getStringExtra("myItem")
        btn_back = findViewById(R.id.btn_detail_back)
        btn_pinjam_baca = findViewById(R.id.btn_detail_pinjam)
        btn_ulasan= findViewById(R.id.btn_detail_ulasan)

        val book = intent.getParcelableExtra<Book>(EXTRA_BOOK) as Book

        if(myItem == "on") {
            btn_pinjam_baca.setText("Baca Buku")
        }else {
            checkBuku(book)
            btn_ulasan.visibility = View.GONE
        }

        Glide.with(this)
            .load(book.cover)
            .centerCrop()
            .placeholder(R.drawable.no_image)
            .into(imgv_detail_book)

        tv_detail_author.text = book.penulis
        tv_detail_author2.text = ":  " + book.penulis
        tv_detail_title.text = ":  " + book.judul
        tv_detail_isbn.text = ":  " + book.isbn
        tv_detail_klasifikasi.text = ":  " + book.kategori
        tv_detail_penerbit.text = ":  " + book.penerbit
        tv_detail_rating.text = book.rating
        tv_detail_tahun_terbit.text = ":  " + book.tahunTerbit
        var deskripsi = book.deskripsi as String
        if(deskripsi.length >= 200){
            deskripsi = deskripsi.substring(0,199) + "..."
        }
        tv_detail_ringkasan.text = ":  " + deskripsi

        btn_pinjam_baca.setOnClickListener {
            if(myItem == "on"){
                val intent = Intent(this, ReadBookActivity::class.java)
                intent.putExtra("FILE", book.file.toString())
                startActivity(intent)
            }else {
                checkPeminjaman(book)
            }
        }

        btn_ulasan.setOnClickListener {
            val intent = Intent(this, UlasanActivity::class.java)
            intent.putExtra("EXTRA_BOOK", book)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPeminjaman(book:Book) {
        var totalPinjaman = 0
        val idUser = auth.currentUser?.uid
        val idPeminjaman = idUser + "-" + book.isbn
        val idBuku = book.isbn

        val currentDateTime = LocalDateTime.now()
        val currentDate = currentDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"))

//        val dateFormat = SimpleDateFormat("dd/M/yyyy ")
//        val currentDate = dateFormat.format(Date())
        val pinjam = Peminjaman(idPeminjaman, idUser, idBuku, currentDate, "unfinished")
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (p in dataSnapshot.children) {
                        totalPinjaman += 1
                    }
                    if(totalPinjaman >= 5){
                        Toast.makeText(this@DetailActivity, "Anda sudah meminjam " + totalPinjaman.toString() + " Buku", Toast.LENGTH_LONG).show()
                    }else {
                        dbRef.child("peminjaman").child(idPeminjaman).setValue(pinjam).addOnSuccessListener {
                            Toast.makeText(this@DetailActivity, "Buku Berhasil Dipinjam", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this@DetailActivity, MainActivity::class.java)
                            intent.putExtra("PINJAM", "buku")
                            startActivity(intent)
                        }
                    }
                }else {
                    dbRef.child("peminjaman").child(idPeminjaman).setValue(pinjam).addOnSuccessListener {
                        Toast.makeText(this@DetailActivity, "Buku Berhasil Dipinjam", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this@DetailActivity, MainActivity::class.java)
                        intent.putExtra("PINJAM", "buku")
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //
            }
        }
        // Path database untuk data user
        dbRef.child("peminjaman").orderByChild("idUser").equalTo(idUser).addListenerForSingleValueEvent(dataListener)
    }


    fun checkBuku(book : Book){
        val idUser = auth.currentUser?.uid
        val idPeminjaman = idUser + "-" + book.isbn
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    btn_pinjam_baca.isEnabled = false

                    btn_pinjam_baca.setText("Buku Sudah Dipinjam")
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //
            }
        }
        // Path database untuk data user
        dbRef.child("peminjaman").child(idPeminjaman).addListenerForSingleValueEvent(dataListener)
    }

}