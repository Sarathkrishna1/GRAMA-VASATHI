package com.example.gramavasathi

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class AddPropertyActivity : AppCompatActivity() {

    // List to store the Base64 text versions of your images
    private val encodedImages = mutableListOf<String>()
    private lateinit var tvCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        // 1. Initialize UI Views
        tvCount = findViewById(R.id.tvImageCount)
        val btnBrowse = findViewById<Button>(R.id.btnBrowse)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitProperty)

        val etName = findViewById<EditText>(R.id.etPropertyName)
        val etLoc = findViewById<EditText>(R.id.etPropertyLocation)
        val etPrice = findViewById<EditText>(R.id.etPropertyPrice)
        val etDesc = findViewById<EditText>(R.id.etPropertyDesc)

        // 2. Multi-Image Selection Logic
        val pickImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.isNotEmpty()) {
                encodedImages.clear()
                // Limit to 3 images to keep the database size small (free tier)
                val limitedUris = uris.take(3)

                limitedUris.forEach { uri ->
                    val base64String = uriToEncodedString(uri)
                    encodedImages.add(base64String)
                }
                tvCount.text = "${encodedImages.size} images selected"
            }
        }

        // Open gallery on button click
        btnBrowse.setOnClickListener {
            pickImages.launch("image/*")
        }

        // 3. Submit to Firebase Logic
        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val loc = etLoc.text.toString().trim()
            val price = etPrice.text.toString().trim()
            val desc = etDesc.text.toString().trim()

            // Check if user filled everything
            if (name.isNotEmpty() && loc.isNotEmpty() && price.isNotEmpty() && encodedImages.isNotEmpty()) {

                val db = FirebaseDatabase.getInstance().getReference("Properties")
                val id = db.push().key!! // Generate unique Property ID

                // --- CRITICAL CHANGE BELOW ---
                // We add hostId = "host123" so that bookings can be sent back to you.
                // In a real app, this would be the ID of the logged-in user.
                val prop = Property(
                    id = id,
                    name = name,
                    location = loc,
                    price = price,
                    description = desc,
                    images = encodedImages,
                    hostId = "host123"
                )

                // Save to Firebase
                db.child(id).setValue(prop).addOnSuccessListener {
                    Toast.makeText(this, "Property is now LIVE!", Toast.LENGTH_LONG).show()
                    finish() // Close screen and go back to Dashboard
                }.addOnFailureListener {
                    Toast.makeText(this, "Upload failed. Check internet.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please select images and fill all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Helper function: Shrinks the photo and converts it to a String
     * so it can be saved in the free Firebase database.
     */
    private fun uriToEncodedString(uri: Uri): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Resize to 480x320 to keep data usage very low
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 480, 320, false)

            val stream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
            val bytes = stream.toByteArray()

            Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            ""
        }
    }
}