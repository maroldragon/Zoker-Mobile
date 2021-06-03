package com.example.perpustakaandigital.screen

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.perpustakaandigital.R


class LoginActivity : AppCompatActivity() {

    private lateinit var imgv_visibility : ImageView
    private lateinit var text_username : EditText
    private lateinit var text_password : EditText
    private lateinit var btn_daftar : Button
    private lateinit var btn_login : Button
    var status_img_pass = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imgv_visibility = findViewById<ImageView>(R.id.imgv_visibility)
        text_username = findViewById(R.id.edt_username)
        text_password = findViewById(R.id.edt_password)
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
                text_password.setInputType(InputType.TYPE_CLASS_TEXT)
                status_img_pass = false
            }else {
                imgv_visibility.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext, // Context
                                R.drawable.ic_visibility_off // Drawable
                        )
                )
                text_password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                status_img_pass = true
            }
        }

        btn_daftar.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}