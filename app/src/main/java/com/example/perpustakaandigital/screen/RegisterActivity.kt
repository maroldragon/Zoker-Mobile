package com.example.perpustakaandigital.screen

import android.app.DatePickerDialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.perpustakaandigital.MainActivity
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity(){
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    lateinit var namaDepan : EditText
    lateinit var namaBelakang : EditText
    lateinit var username : EditText
    lateinit var spinJenisKelamin : Spinner
    lateinit var tempatLahir : EditText
    lateinit var tanggalLahir : EditText
    lateinit var spinAgama : Spinner
    lateinit var spinHobi : Spinner
    lateinit var negara : EditText
    lateinit var provinsi : EditText
    lateinit var kota : EditText
    lateinit var alamat : EditText
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var confirmPassword : EditText
    lateinit var imgVisibilityPass : ImageView
    lateinit var imgVisibilityPass2 : ImageView
    var status_img_pass = true
    var status_img_pass2 = true
    lateinit var btnDaftar : Button

    lateinit var imgvDatePicker : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signup_progress.visibility = View.GONE

        namaDepan = findViewById(R.id.edt_nama_depan)
        namaBelakang = findViewById(R.id.edt_nama_belakang)
        username = findViewById(R.id.edt_username)
        spinJenisKelamin = findViewById(R.id.spin_jenis_kelamin)
        tempatLahir = findViewById(R.id.edt_tempat_lahir)
        imgvDatePicker = findViewById(R.id.imgv_date_picker)
        tanggalLahir = findViewById(R.id.edt_tanggal_lahir)
        spinAgama = findViewById(R.id.spin_agama)
        spinHobi = findViewById(R.id.spin_hobi)
        negara = findViewById(R.id.edt_negara)
        provinsi = findViewById(R.id.edt_provinsi)
        kota = findViewById(R.id.edt_kota)
        alamat  = findViewById(R.id.edt_alamat)
        email = findViewById(R.id.edt_email)
        password  = findViewById(R.id.edt_password)
        confirmPassword  = findViewById(R.id.edt_confirm_password)
        imgVisibilityPass = findViewById(R.id.imgv_visibility_pass)
        imgVisibilityPass2 = findViewById(R.id.imgv_visibility_confirm)
        btnDaftar = findViewById(R.id.btn_daftar)

        ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kelamin_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinJenisKelamin.adapter = adapter
        }

        imgvDatePicker.setOnClickListener {
            val now = Calendar.getInstance()
            val tahun = now.get(Calendar.YEAR)
            val bulan = now.get(Calendar.MONTH)
            val hari = now.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                // Display Selected date in textbox
                tanggalLahir.setText("" + day + "/" + month + "/" + year)

            }, tahun, bulan, hari).show()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.agama_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinAgama.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.hobi_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinHobi.adapter = adapter
        }

        imgVisibilityPass.setOnClickListener {
            if(status_img_pass) {
                imgVisibilityPass.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_visibility_on // Drawable
                    )
                )
                password.setInputType(InputType.TYPE_CLASS_TEXT)
                status_img_pass = false
            }else {
                imgVisibilityPass.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_visibility_off // Drawable
                    )
                )
                password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                status_img_pass = true
            }
        }

        imgVisibilityPass2.setOnClickListener {
            if(status_img_pass2) {
                imgVisibilityPass2.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_visibility_on // Drawable
                    )
                )
                confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT)
                status_img_pass2 = false
            }else {
                imgVisibilityPass2.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_visibility_off // Drawable
                    )
                )
                confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                status_img_pass2 = true
            }
        }

        signup_progress.visibility = View.GONE

        btnDaftar.setOnClickListener {
            val check_input = checkingInput()
            if(check_input != "1"){
                Toast.makeText(this, check_input + " Harus Diisi", Toast.LENGTH_LONG).show()
            }else if(!isValidEmail(email.text.toString())) {
                Toast.makeText(this, "Email Tidak Valid", Toast.LENGTH_LONG).show()
            }else if(!isValidPassword(password.text.toString())){
                Toast.makeText(this, "Password Minimal 8 Karakter", Toast.LENGTH_LONG).show()
            }else if(!passwordIsSame(password.text.toString(), confirmPassword.text.toString())){
                Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_LONG).show()
            } else {
                createUser()
            }
        }

    }

    private fun passwordIsSame(password: String, confirmPass: String): Boolean {
        return password == confirmPass
    }

    // Fungsi untuk register user, dan dan menambahkan data user kedalam bucket/tabel user
    private fun createUser() {
        signup_progress.visibility = View.VISIBLE
        var jenisKelamin = ""
        var hobi = ""
        var agama = ""
        if (spinJenisKelamin.selectedItemPosition != 0){
            jenisKelamin = spinJenisKelamin.getSelectedItem().toString()
        }
        if (spinAgama.selectedItemPosition != 0){
            agama = spinAgama.getSelectedItem().toString()
        }
        if (spinHobi.selectedItemPosition != 0){
            hobi = spinHobi.getSelectedItem().toString()
        }
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).
        addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful){
                val userId = auth.currentUser?.uid
                val registerRef = dbRef.child("user").child(userId!!)
                val user = User(userId, namaDepan.text.toString(), namaBelakang.text.toString(), username.text.toString(), "",
                jenisKelamin,tempatLahir.text.toString(),tanggalLahir.text.toString(),
                    agama, hobi, negara.text.toString(), provinsi.text.toString(),
                    kota.text.toString(), alamat.text.toString(), email.text.toString(), password.text.toString(), 0)
                registerRef.setValue(user).addOnSuccessListener {
                    signup_progress.visibility = View.GONE
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else{
                signup_progress.visibility = View.GONE
                Toast.makeText(
                    baseContext,
                    "Please check your number Email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun isValidPassword(password: String) : Boolean{
        return password.length >= 8
    }

      private fun checkingInput(): String {
        if(namaDepan.text.toString().trim() == "") return "Nama Depat"
        else if (email.text.toString().trim() == "") return "Email"
        else if (alamat.text.toString().trim() == "") return "Alamat"
        else if (password.text.toString() == "") return "Password"
        else return "1"
    }

}