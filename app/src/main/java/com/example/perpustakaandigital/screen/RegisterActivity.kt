package com.example.perpustakaandigital.screen

import android.app.DatePickerDialog
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.perpustakaandigital.R
import java.util.*

class RegisterActivity : AppCompatActivity(){

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
    lateinit var btnDaftar : Button

    lateinit var imgvDatePicker : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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

        btnDaftar.setOnClickListener {
            Toast.makeText(this, spinJenisKelamin.getSelectedItem().toString(), Toast.LENGTH_LONG).show()
        }
    }

}