package com.example.perpustakaandigital.screen

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    private lateinit var imgv_visibility : ImageView
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var btn_daftar : Button
    private lateinit var btn_login : Button
    var status_img_pass = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imgv_visibility = findViewById<ImageView>(R.id.imgv_visibility)
        username = findViewById(R.id.edt_username)
        password = findViewById(R.id.edt_password)
        btn_daftar = findViewById(R.id.btn_daftar)
        btn_login = findViewById(R.id.btn_login)
        imgv_visibility.setOnClickListener {
            if(status_img_pass) {
                imgv_visibility.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext, // Context
                                R.drawable.ic_visibility_on // Drawable
                        )
                )
                password.setInputType(InputType.TYPE_CLASS_TEXT)
                status_img_pass = false
            }else {
                imgv_visibility.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext, // Context
                                R.drawable.ic_visibility_off // Drawable
                        )
                )
                password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                status_img_pass = true
            }
        }

        btn_daftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            if(checkInput()) {
                auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            updateUI(null)
                            // ...
                        }
                        // ...
                    }

                //For Development
                //startActivity(Intent(this, MainActivity::class.java))

            }
        }

    }

    // Fungsi pengecekan apakah user sudah login
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // FUngsi navigasi jika login berhasil, mengarah ke halaman home
    private fun updateUI(currentUser : FirebaseUser?){
        if (currentUser != null){
            loadData(auth.uid.toString())
        }
        else{
            Toast.makeText(baseContext, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
        }
    }

    //Pengecekan Inputan apakah sudah lengkap
    private fun checkInput() : Boolean{
        return if(username.text.toString().trim() == ""){
            Toast.makeText(baseContext, "Silahkan Isi Email", Toast.LENGTH_LONG).show()
            false
        }else if(password.text.toString().trim() == ""){
            Toast.makeText(baseContext, "Silahkan Isi Password", Toast.LENGTH_LONG).show()
            false
        }else true
    }

    // Fungsi get data user dari database untuk mengambil foto, nama, no hp
    private fun loadData(userId: String){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    val user: User = dataSnapshot.getValue(User::class.java)!!
                    if(user.status == "unverified"){
                        Toast.makeText(this@LoginActivity, "Akun anda belum diverifikasi oleh admin", Toast.LENGTH_LONG).show()
                        auth.signOut()
                    }else {
                        startActivity(Intent(baseContext, MainActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //
            }
        }
        // Path database untuk data user
        dbRef.child("user").child(userId).addListenerForSingleValueEvent(dataListener)

    }

}