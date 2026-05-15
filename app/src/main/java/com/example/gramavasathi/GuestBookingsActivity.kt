package com.example.gramavasathi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class GuestBookingsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val bookingList = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guest_bookings)

        // Standard modern Android padding logic
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Setup RecyclerView
        recyclerView = findViewById(R.id.rvGuestBookings)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 2. Fetch Bookings from Firebase Realtime Database
        val database = FirebaseDatabase.getInstance().getReference("Bookings")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookingList.clear() // Prevent duplicates on refresh

                for (data in snapshot.children) {
                    val booking = data.getValue(Booking::class.java)
                    if (booking != null) {
                        bookingList.add(booking)
                    }
                }

                // 3. Connect to the Guest Adapter
                val adapter = GuestBookingAdapter(bookingList)
                recyclerView.adapter = adapter

                if (bookingList.isEmpty()) {
                    Toast.makeText(this@GuestBookingsActivity, "You have no bookings yet.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GuestBookingsActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}