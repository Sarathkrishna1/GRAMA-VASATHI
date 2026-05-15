package com.example.gramavasathi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ListingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val fullList = mutableListOf<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        recyclerView = findViewById(R.id.recyclerViewFarms)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. Detailed Local List (Added long descriptions and 3-image galleries)
        val featuredList = listOf(
            Property("f1", "Malnad Mist Farmhouse", "Agumbe, Shimoga", "1500",
                "Experience the magic of the Western Ghats. This stay is located inside a lush areca nut plantation. You will get home-cooked Malnad style food (Akki Roti/Kadubu). Perfect for trekking enthusiasts and bird watchers.",
                listOf(), "host123", R.drawable.malnad_stay,
                listOf(R.drawable.malnad_stay, R.drawable.malnad_act, R.drawable.malnad_food)),

            Property("f2", "Kaveri Riverside Stay", "Srirangapatna, Mandya", "1200",
                "Located on the banks of the Kaveri river. Wake up to the sound of flowing water and chirping birds. Activities include organic farming, bullock cart rides, and evening campfires with traditional music.",
                listOf(), "host123", R.drawable.matti_stay,
                listOf(R.drawable.matti_stay, R.drawable.matti_act, R.drawable.matti_food)),

            Property("f3", "Coorg Coffee Retreat", "Madikeri, Coorg", "2000",
                "A 100-year-old traditional Ainmane house. Stay right in the middle of a sprawling coffee estate. Learn how coffee is picked and processed. Enjoy authentic Kodava cuisine like Pandi Curry and Bamboo shoot curry.",
                listOf(), "host123", R.drawable.coorg_stay,
                listOf(R.drawable.coorg_stay, R.drawable.coorg_act, R.drawable.coorg_food))
        )

        val database = FirebaseDatabase.getInstance().getReference("Properties")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fullList.clear()
                fullList.addAll(featuredList) // Add Local stays
                for (data in snapshot.children) {
                    data.getValue(Property::class.java)?.let { fullList.add(it) } // Add Cloud stays
                }
                recyclerView.adapter = PropertyAdapter(fullList)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}