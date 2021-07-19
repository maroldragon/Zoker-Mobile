package com.example.perpustakaandigital.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.Feedback
import com.example.perpustakaandigital.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_kontak.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class KontakActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference
    lateinit var nama : EditText
    lateinit var email : EditText
    lateinit var pesan : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kontak)

        nama = findViewById(R.id.edt_kontak_nama)
        email = findViewById(R.id.edt_kontak_email)
        pesan = findViewById(R.id.edt_kontak_pesan)

        btn_kontak_back.setOnClickListener {
            finish()
        }

        btn_kontak_kirim.setOnClickListener {
            if(nama.text.toString() == "" || email.text.toString() =="" || pesan.text.toString()==""){
                Toast.makeText(this@KontakActivity, "Lengkapi Data", Toast.LENGTH_LONG).show()
            }else {
                sendText(nama.text.toString(), email.text.toString(), pesan.text.toString())
            }
        }
    }

    fun sendText(nama : String, email: String, pesan: String) {
        val idFeedback = UUID.randomUUID().toString()
        val now = Calendar.getInstance()
        val tahun = now.get(Calendar.YEAR)
        val bulan = now.get(Calendar.MONTH)
        val hari = now.get(Calendar.DAY_OF_MONTH)
        val registerRef = dbRef.child("feedback").child(idFeedback)
        val feedback = Feedback(idFeedback, nama, email, pesan, ""+hari+"/"+bulan+"/"+tahun)
        registerRef.setValue(feedback).addOnSuccessListener {
            Toast.makeText(this@KontakActivity, "Pesan Sudah Dikirimkan", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}