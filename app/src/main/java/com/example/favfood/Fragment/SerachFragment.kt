package com.example.favfood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favfood.Adapter.MenuAdapter
import com.example.favfood.Model.MenuItem
import com.example.favfood.databinding.FragmentSerachBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.ValueEventListener


class SerachFragment : Fragment() {
    private lateinit var binding: FragmentSerachBinding
    private lateinit var  adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems = mutableListOf<MenuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = FragmentSerachBinding.inflate(inflater,container,false)
        
        // retrieve menu item from database
        retrieveMenuItem()

        //setup for search view
        setupSearchView()
        return binding.root
    }

    private fun retrieveMenuItem() {
        //get database ref

        database =  FirebaseDatabase.getInstance()
        //ref to the menu node
        val foodReference: DatabaseReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               for (foodSnapshot in snapshot.children){

                   val menuItem= foodSnapshot.getValue(MenuItem::class.java)
                   menuItem?.let {
                       originalMenuItems.add(it)

                   }
               }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuItems)
       setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
adapter = MenuAdapter(filteredMenuItem , requireContext())
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.MenuRecyclerView.adapter = adapter
    }


    private fun setupSearchView() {


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
               filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
             filterMenuItems(newText)
                return true
            }
        })

    }

    private fun filterMenuItems(query: String) {

         val filteredMenuItems = originalMenuItems.filter {
             it.foodName?.contains(query,ignoreCase = true) == true
         }
        setAdapter(filteredMenuItems)
        }


    }
