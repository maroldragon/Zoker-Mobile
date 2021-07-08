package com.example.perpustakaandigital.screen

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_profile_member.*
import java.io.IOException
import java.util.*

class ProfileMemberActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageReference: StorageReference = storage.reference

    lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_member)

        btnBack = findViewById(R.id.btn_profile_member_back)

        imgv_profil_member_edit_photo.setOnClickListener {
            addImage()
        }
        btnBack.setOnClickListener {
            finish()
        }

        loadData(auth.uid.toString())

    }

    // Fungsi membuka folder dengan seluruh file tipe gambar
    private fun addImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    // Fungsi hasil mengambil hasil gambar yang dipilih dan merubah kebentuk bitmap
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, filePath)
                imgv_profil_member.setImageBitmap(bitmap)
                uploudPost()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi menguploud foto profile user kedalam database
    private fun uploudPost(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        val ref = storageReference.child("images/" + UUID.randomUUID().toString())
        ref.putFile(filePath!!).addOnSuccessListener {

            ref.downloadUrl.addOnSuccessListener {
                val link = it.toString()
                val userId = auth.currentUser?.uid
                val postRef = dbRef.child("user").child(userId.toString()).child("photo")
                postRef.setValue(link).addOnSuccessListener {
                    progressDialog.dismiss()
                    //postDone()
                }
            }.addOnFailureListener {
                // Handle any errors
            }
        }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT).show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progressDialog.setMessage("Update Successful " + progress.toInt() + "%")
            }
    }



    // Fungsi get data user dari database untuk mengambil foto, nama, no hp
    private fun loadData(userId: String){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    val user: User = dataSnapshot.getValue(User::class.java)!!
                    tv_profile_member_username.text = user.userName
                    tv_profile_member_sosial.text= (user.email)?.substring(10)
                    tv_profile_member_country.text = user.negara
                    tv_profile_member_jk.text = user.jenisKelamin
                    tv_profile_member_ttl.text = user.tempatLahir
                    tv_profile_member_tgl.text = user.tanggalLahir
                    tv_profile_member_alamat.text = user.alamat
                    tv_profile_member_kota.text = user.kota
                    tv_profile_member_provinsi.text = user.provinsi
                    tv_profile_member_negara.text = user.negara
                    tv_profile_member_email.text = user.email

                    Toast.makeText(this@ProfileMemberActivity, user.photo + "Ya", Toast.LENGTH_LONG).show()
                    if(user.photo != ""){
                        Glide.with(this@ProfileMemberActivity).load(user.photo).into(imgv_profil_member)
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