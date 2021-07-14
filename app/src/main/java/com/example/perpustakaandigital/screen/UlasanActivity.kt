package com.example.perpustakaandigital.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.model.Peminjaman
import com.example.perpustakaandigital.model.Rating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_ulasan.*
import java.text.SimpleDateFormat
import java.util.*


class UlasanActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference
    lateinit var btn_back : ImageView
    lateinit var btn_kirim : Button
    lateinit var rating : TextView
    lateinit var image : ImageView
    lateinit var author : TextView
    lateinit var title : TextView
    lateinit var ratingGiven : TextView
    lateinit var textBoxUlasan : EditText
    lateinit var ratingBar : RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ulasan)

        btn_back = findViewById(R.id.btn_ulasan_back)
        btn_kirim = findViewById(R.id.btn_ulasan_kirim)
        rating = findViewById(R.id.tv_ulasan_rating)
        image = findViewById(R.id.imgv_ulasan)
        author = findViewById(R.id.tv_ulasan_author)
        title = findViewById(R.id.tv_ulasan_title)
        ratingGiven = findViewById(R.id.tv_ulasan_rating_given)
        textBoxUlasan = findViewById(R.id.et_ulasan_given)
        ratingBar = findViewById(R.id.ratingBar_Ulasan)

        val book = intent.getParcelableExtra<Book>("EXTRA_BOOK") as Book
        val rate = book.rating?.toDouble()
        rating.text = String.format("%.1f", rate).toDouble().toString()
        Glide.with(this)
            .load(book.cover)
            .centerCrop()
            .placeholder(R.drawable.no_image)
            .into(image)
        title.text = book.judul
        author.text = book.penulis

        btn_back.setOnClickListener {
            finish()
        }

        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser -> // update our result
                tv_ulasan_rating_given.setText("" + (rating*2) + " / 10")
            }

        btn_kirim.setOnClickListener {
            val idUser = auth.currentUser?.uid
            val idRating = idUser + "-" + book.isbn
            val idBuku = book.isbn
            val dateFormat = SimpleDateFormat("dd/M/yyyy")
            val currentDate = dateFormat.format(Date())
            val rate= (ratingBar.getRating()*2).toString()
            val rating = Rating(idRating, idUser, idBuku, rate, textBoxUlasan.text.toString(), currentDate)
            dbRef.child("ratings").child(idRating).setValue(rating).addOnSuccessListener {
                Toast.makeText(this, "Ulasan Berhasil Dikirim", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        loadDataUlasan(book.isbn as String)

    }

    private fun loadDataUlasan(idBuku : String){
        // Get data from firebase
        val idUser = auth.currentUser?.uid
        val query: Query = dbRef.child("ratings").orderByChild("idRating").equalTo(idUser+"-"+idBuku)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val rating = p.getValue(Rating::class.java)
                        textBoxUlasan.setText(rating?.ulasan)
                        ratingBar.rating = ((rating?.rating)!!.toFloat())/(2.toFloat())
                        btn_kirim.visibility = View.GONE
                    }
                }
            }
        })
    }


}