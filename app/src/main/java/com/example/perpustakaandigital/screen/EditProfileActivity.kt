package com.example.perpustakaandigital.screen

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile_member.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class EditProfileActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    lateinit var namaLengkap : EditText
    lateinit var username : EditText
    lateinit var spinJenisKelamin : Spinner
    lateinit var tempatLahir : EditText
    lateinit var tanggalLahir : EditText
    lateinit var negara : EditText
    lateinit var provinsi : EditText
    lateinit var kota : EditText
    lateinit var alamat : EditText
    lateinit var btnEdit : Button

    lateinit var imgvDatePicker : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        signup_progress.visibility = View.GONE

        namaLengkap = findViewById(R.id.edt_nama_lengkap)
        username = findViewById(R.id.edt_username)
        //spinJenisKelamin = findViewById(R.id.spin_jenis_kelamin)
        tempatLahir = findViewById(R.id.edt_tempat_lahir)
        imgvDatePicker = findViewById(R.id.imgv_date_picker)
        tanggalLahir = findViewById(R.id.edt_tanggal_lahir)
        negara = findViewById(R.id.edt_negara)
        provinsi = findViewById(R.id.edt_provinsi)
        kota = findViewById(R.id.edt_kota)
        alamat  = findViewById(R.id.edt_alamat)
        btnEdit = findViewById(R.id.btn_update)

//        ArrayAdapter.createFromResource(
//            this,
//            R.array.jenis_kelamin_array,
//            android.R.layout.simple_spinner_dropdown_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinJenisKelamin.adapter = adapter
//        }

        imgvDatePicker.setOnClickListener {
            val now = Calendar.getInstance()
            val tahun = now.get(Calendar.YEAR)
            val bulan = now.get(Calendar.MONTH)
            val hari = now.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                // Display Selected date in textbox
                tanggalLahir.setText("" + year + "-" + month + "-" + day)

            }, tahun, bulan, hari).show()
        }

        btnEdit.setOnClickListener {
            val check_input = checkingInput()
            if(check_input != "1"){
                Toast.makeText(this, check_input + " Harus Diisi", Toast.LENGTH_LONG).show()
            } else {
                editDataUser()
            }
        }
        loadData(auth.uid.toString())
    }

    private fun editDataUser() {
        val userId = auth.currentUser?.uid
        dbRef.child("user").child(userId!!).child("namaLengkap").setValue(namaLengkap.text.toString())
        dbRef.child("user").child(userId!!).child("tempatLahir").setValue(tempatLahir.text.toString())
        dbRef.child("user").child(userId!!).child("tanggalLahir").setValue(tanggalLahir.text.toString())
        dbRef.child("user").child(userId!!).child("negara").setValue(negara.text.toString())
        dbRef.child("user").child(userId!!).child("provinsi").setValue(provinsi.text.toString())
        dbRef.child("user").child(userId!!).child("kota").setValue(kota.text.toString())
        dbRef.child("user").child(userId!!).child("alamat").setValue(alamat.text.toString()).addOnSuccessListener {
            Toast.makeText(this, "Data Berhasil Diedit", Toast.LENGTH_LONG).show()
            val intent = Intent(this,ProfileMemberActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    // Fungsi get data user dari database untuk mengambil foto, nama, no hp
    private fun loadData(userId: String){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    val user: User = dataSnapshot.getValue(User::class.java)!!
                    namaLengkap.setText(user.namaLengkap)
                    //spinJenisKelamin
                    username.setText(user.userName)
                    tempatLahir.setText(user.tempatLahir)
                    tanggalLahir.setText(user.tanggalLahir)
                    negara.setText(user.negara)
                    provinsi.setText(user.provinsi)
                    kota.setText(user.kota)
                    alamat.setText(user.alamat)

//                    val spinnerPosition: Int = getIndex(spinJenisKelamin, user.jenisKelamin as String)
//                    spinJenisKelamin.setSelection(spinnerPosition)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //
            }
        }
        // Path database untuk data user
        dbRef.child("user").child(userId).addListenerForSingleValueEvent(dataListener)

    }

    private fun checkingInput(): String {
        if(namaLengkap.text.toString().trim() == "") return "Nama"
        else if (alamat.text.toString().trim() == "") return "Alamat"
        else return "1"
    }

//    private fun getIndex(spinner: Spinner, myString: String): Int {
//        for (i in 0 until spinner.count) {
//            if (spinner.getItemAtPosition(i).toString()
//                    .equals(myString, ignoreCase = true)
//            ) {
//                return i
//            }
//        }
//        return 0
//    }

}