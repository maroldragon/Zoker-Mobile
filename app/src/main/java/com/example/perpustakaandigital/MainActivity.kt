package com.example.perpustakaandigital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.perpustakaandigital.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var state: String? = ""

    private lateinit var bottomNavigation : com.google.android.material.bottomnavigation.BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        state = intent.getStringExtra("PINJAM")

        bottomNavigation = findViewById(R.id.bottom_navigation)
        isSelectedBottomNav(savedInstanceState)

    }

    // Fungsi ketika icon padd bottom navigation diclick makan akan memanggil fungsi replace fragment
    private fun isSelectedBottomNav(savedInstanceState: Bundle?){
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_category -> {
                    replaceFragment(CategoryFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_bookmark -> {
                    replaceFragment(MyItemFragment())
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.menu_notification -> {
//                    replaceFragment(NotificationFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
                R.id.menu_profil -> {
                    replaceFragment(ProfilFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        if (savedInstanceState == null){
            if(state == "buku"){
                bottom_navigation.selectedItemId = R.id.menu_bookmark
            }else {
                bottom_navigation.selectedItemId = R.id.menu_home
            }
        }
    }

    // Fungsi replace fragment ketika dipilih sesuai id
    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}