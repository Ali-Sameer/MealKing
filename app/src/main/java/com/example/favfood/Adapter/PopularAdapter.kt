package com.example.favfood.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favfood.DetailsActivity
import com.example.favfood.databinding.PopularitemsBinding

class PopularAdapter (private val items:List<String>,private val price:List<String>,private val image:List<Int>,private val requireContext:Context): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
       return  PopularViewHolder(PopularitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {

        val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item, price, images)

holder.itemView.setOnClickListener{



    val intent = Intent(requireContext, DetailsActivity::class.java)
    intent.putExtra("MenuItemName", item)
    intent.putExtra("MenuItemImages", images)
    requireContext.startActivity(intent)

}

    }

    override fun getItemCount(): Int {

        return  items.size
    }


    class PopularViewHolder (private val binding: PopularitemsBinding): RecyclerView.ViewHolder(binding.root){
      private  val imagesView = binding.foodimage
        fun bind(item: String,price:String, images: Int ) {

            binding.foodname.text = item
            binding.foodprice.text = price
           imagesView.setImageResource(images)



        }

    }

}