package com.example.gramavasathi

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // 1. Get Data from Intent (Passed from DetailsActivity)
        // It is CRITICAL that DetailsActivity passes "HOST_ID"
        val propId = intent.getStringExtra("PROP_ID") ?: ""
        val propName = intent.getStringExtra("PROP_NAME") ?: ""
        val hostId = intent.getStringExtra("HOST_ID") ?: "host123"

        val etName = findViewById<EditText>(R.id.etGuestName)
        val etPhone = findViewById<EditText>(R.id.etGuestPhone)
        val etDate = findViewById<EditText>(R.id.etBookingDate)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmBooking)

        // 2. DATE PICKER LOGIC (Calendar Popup)
        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Month is +1 because January is 0
                val dateString = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDate.setText(dateString)
            }, year, month, day)

            // Prevent selecting past dates (Professional touch)
            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.show()
        }

        // 3. CONFIRM BUTTON LOGIC
        btnConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val date = etDate.text.toString().trim()

            // Validation: Make sure nothing is empty
            if (name.isEmpty() || phone.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all details and select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 4. SAVE TO FIREBASE
            val db = FirebaseDatabase.getInstance().getReference("Bookings")
            val bId = db.push().key!! // Generate a unique ID for this booking

            // Create the booking object using the template in Booking.kt
            // Notice how 'hostId' is included here so the host can find it later
            val booking = Booking(
                bookingId = bId,
                propertyId = propId,
                propertyName = propName,
                hostId = hostId,
                guestName = name,
                guestPhone = phone,
                checkInDate = date,
                status = "Pending" // Initial status
            )

            // Upload the data to the cloud
            db.child(bId).setValue(booking).addOnSuccessListener {
                Toast.makeText(this, "Booking request sent to Host!", Toast.LENGTH_LONG).show()

                // Move to Success screen
                val intent = Intent(this, ConfirmationActivity::class.java)
                startActivity(intent)
                finish() // Close this screen
            }.addOnFailureListener {
                Toast.makeText(this, "Network Error. Please check your connection.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}