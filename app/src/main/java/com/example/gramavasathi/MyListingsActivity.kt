package com.example.gramavasathi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyListingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_listings)

        val recyclerView = findViewById<RecyclerView>(R.id.rvMyListings)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Use the saved properties from our Global Memory
        val myData = PropertyStorage.hostProperties

        // Reuse the Adapter we created for the Guest side!
        val adapter = FarmStayAdapter(myData) { selectedFarm ->
            // (Optional) You can add click logic here too
        }
        recyclerView.adapter = adapter
    }
}