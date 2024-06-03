package com.example.favfood.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favfood.MainActivity
import com.example.favfood.databinding.FragmentCongratesBottumSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CongratesBottumSheet : BottomSheetDialogFragment(){
    private lateinit var binding: FragmentCongratesBottumSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCongratesBottumSheetBinding.inflate(layoutInflater,container,false)

        binding.GoHome.setOnClickListener{
           val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}