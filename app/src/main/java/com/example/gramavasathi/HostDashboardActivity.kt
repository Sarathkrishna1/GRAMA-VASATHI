package com.example.gramavasathi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HostDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_dashboard)

        // 1. Find the Cards
        val cardAdd = findViewById<CardView>(R.id.cardAddProperty)
        val cardReq = findViewById<CardView>(R.id.cardRequests)
        val cardMsg = findViewById<CardView>(R.id.cardMessages)
        val cardSet = findViewById<CardView>(R.id.cardSettings)
        val cardStats = findViewById<CardView>(R.id.cardStatListings)

        // 2. Set Action for "Add Property"
        cardAdd.setOnClickListener {
            startActivity(Intent(this, AddPropertyActivity::class.java))
        }

        // 3. Set Action for "Requests"
        cardReq.setOnClickListener {
            startActivity(Intent(this, HostBookingsActivity::class.java))
        }

        // 4. Set Action for "Messages"
        cardMsg.setOnClickListener {
            // Reusing a Toast or creating a simple Activity
            Toast.makeText(this, "Opening Inbox...", Toast.LENGTH_SHORT).show()
            // Optional: startActivity(Intent(this, MessagesActivity::class.java))
        }

        // 5. Set Action for "Settings"
        cardSet.setOnClickListener {
            // Clearing training status to test the "New User" flow (Logout)
            val sharedPrefs = getSharedPreferences("GramaPrefs", Context.MODE_PRIVATE)
            sharedPrefs.edit().clear().apply()
            Toast.makeText(this, "Logged Out / Settings reset", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 6. Set Action for "Listings Stat"
        cardStats.setOnClickListener {
            startActivity(Intent(this, MyListingsActivity::class.java))
        }

        // 7. Bottom Navigation Logic
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_bookings -> {
                    startActivity(Intent(this, HostBookingsActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Profile feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}