package com.example.favfood


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.favfood.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var NavController = findNavController(R.id.fragmentContainerView3)
        var bottumnav =  findViewById<BottomNavigationView>(R.id.bottomNavigationView1)
        bottumnav.setupWithNavController(NavController)
        binding.notificationbell.setOnClickListener{
            val bottomSheetDialog = NotificationBottumFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }


    }
}