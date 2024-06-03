package com.example.favfood

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.favfood.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {


    private val binding: ActivityChooseLocationBinding by lazy{

        ActivityChooseLocationBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val locationList = arrayOf("Kanpur","Lucknow","Fatehpur","bindaki")

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1,locationList)

        val autoCompleteTextView = binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)

    }
}