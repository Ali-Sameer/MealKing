package com.example.favfood

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favfood.Adapter.NotificationAdapter
import com.example.favfood.databinding.FragmentNotificationBottumBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottumFragment :BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationBottumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      binding = FragmentNotificationBottumBinding.inflate(layoutInflater,container,false)

        val notification = listOf("Your order has been Canceled","Order has been taken by the driver","Congrats Your Order Placed")
        val notificationImages = listOf(
            R.drawable.dukhi,R.drawable.boy,R.drawable.successful
        )

        val adapter = NotificationAdapter(
            ArrayList(notification),
            ArrayList(notificationImages)
        )
binding.notificationrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationrecyclerView.adapter = adapter
        return binding.root
    }

    companion object {


    }
}