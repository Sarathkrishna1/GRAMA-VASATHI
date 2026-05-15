package com.example.gramavasathi

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val property = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("FARM_DATA", Property::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("FARM_DATA") as? Property
        }

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerImages)
        val tvName = findViewById<TextView>(R.id.tvDetName)
        val tvLoc = findViewById<TextView>(R.id.tvDetLoc)
        val tvPrice = findViewById<TextView>(R.id.tvDetPrice)
        val tvDesc = findViewById<TextView>(R.id.tvDetDesc)
        val btnCall = findViewById<Button>(R.id.btnCallHost)
        val btnBook = findViewById<Button>(R.id.btnBookNow)

        property?.let { prop ->
            tvName.text = prop.name
            tvLoc.text = prop.location
            tvPrice.text = "₹${prop.price}/night"
            tvDesc.text = prop.description

            // --- SMART SLIDER LOGIC ---
            if (prop.localImage != 0) {
                // If it's a local property, we use the local resource IDs
                viewPager.adapter = LocalImageSliderAdapter(prop.localGallery)
            } else {
                // If it's a cloud property, we use the Base64 strings
                viewPager.adapter = ImageSliderAdapter(prop.images)
            }

            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:9876543210")
                startActivity(intent)
            }

            btnBook.setOnClickListener {
                val intent = Intent(this, BookingActivity::class.java)
                intent.putExtra("PROP_ID", prop.id)
                intent.putExtra("PROP_NAME", prop.name)
                intent.putExtra("HOST_ID", prop.hostId)
                startActivity(intent)
            }
        }
    }
}