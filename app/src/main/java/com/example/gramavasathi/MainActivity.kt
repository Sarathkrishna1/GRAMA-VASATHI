package com.example.gramavasathi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Find all buttons from the layout
        val btnGuest = findViewById<Button>(R.id.btnViewListings)
        val btnHost = findViewById<Button>(R.id.btnBecomeHost)
        val btnStatus = findViewById<Button>(R.id.btnGuestBookings)
        val btnGuide = findViewById<Button>(R.id.btnCulturalGuide) // New Button for Guide

        // 2. GUEST FLOW: Explore Farm Stays
        btnGuest.setOnClickListener {
            startActivity(Intent(this, ListingActivity::class.java))
        }

        // 3. GUEST FLOW: Check Booking Status (Pending/Accepted)
        btnStatus.setOnClickListener {
            startActivity(Intent(this, GuestBookingsActivity::class.java))
        }

        // 4. NEW FEATURE: Open the Cultural Guide Handbook
        btnGuide.setOnClickListener {
            startActivity(Intent(this, CulturalGuideActivity::class.java))
        }

        // 5. HOST FLOW: Checks if training is done before opening Dashboard
        btnHost.setOnClickListener {
            // Access the app's local memory
            val sharedPrefs = getSharedPreferences("GramaPrefs", Context.MODE_PRIVATE)
            val isDone = sharedPrefs.getBoolean("isTrainingCompleted", false)

            if (isDone) {
                // If user finished training, go straight to Host Dashboard
                startActivity(Intent(this, HostDashboardActivity::class.java))
            } else {
                // If new host, go to the Readiness Checklist
                startActivity(Intent(this, HostActivity::class.java))
            }
        }
    }
}