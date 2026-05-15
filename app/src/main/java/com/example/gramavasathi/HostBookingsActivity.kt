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

class HostBookingsActivity : AppCompatActivity() {

    // 1. Declare the variables for the list
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HostBookingAdapter
    private val bookingList = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host_bookings)

        // Handle the system bar padding (Standard modern Android code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Initialize the RecyclerView
        recyclerView = findViewById(R.id.rvHostBookings)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 3. Connect to Firebase "Bookings" folder
        val database = FirebaseDatabase.getInstance().getReference("Bookings")

        // 4. Filter: Only show bookings for this host (host123)
        // Ensure you have updated Firebase Rules with ".indexOn": ["hostId"]
        val hostQuery = database.orderByChild("hostId").equalTo("host123")

        // 5. Start listening for Guest bookings in real-time
        hostQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookingList.clear() // Clear the list before adding new data

                for (data in snapshot.children) {
                    val booking = data.getValue(Booking::class.java)
                    if (booking != null) {
                        bookingList.add(booking)
                    }
                }

                // 6. Send the list to the Adapter
                adapter = HostBookingAdapter(bookingList)
                recyclerView.adapter = adapter

                if (bookingList.isEmpty()) {
                    Toast.makeText(this@HostBookingsActivity, "No booking requests found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HostBookingsActivity, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}