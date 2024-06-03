package com.example.favfood.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favfood.databinding.BuyagainitemBinding

class BuyAgainAdapter(private val buyAgainFoodName: MutableList<String>, private  val buyAgainFoodPrice: MutableList<String>, private val buyAgainFoodNImage: MutableList<String>,private var requireContext: Context ): RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {



    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {

        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodNImage[position])

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
      val binding = BuyagainitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)


    }

    override fun getItemCount(): Int = buyAgainFoodName.size

   inner class BuyAgainViewHolder(private val binding: BuyagainitemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: String) {

        binding.buyAgainFoodName.text=foodName
            binding.buyAgainFoodPrice.text = foodPrice
            val uriString = foodImage
             val    uri =  Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainFoodImage)

        }

    }


}