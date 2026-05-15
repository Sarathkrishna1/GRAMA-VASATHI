package com.example.gramavasathi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class HostBookingAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<HostBookingAdapter.ViewHolder>() {

    // 1. ViewHolder: Links the code to the IDs in item_host_booking.xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPropertyName: TextView = view.findViewById(R.id.tvBookProp)
        val tvGuestInfo: TextView = view.findViewById(R.id.tvBookGuest)
        val tvDate: TextView = view.findViewById(R.id.tvBookDate)
        val tvStatus: TextView = view.findViewById(R.id.tvBookStatus)
        val btnAccept: Button = view.findViewById(R.id.btnAccept)
        val btnReject: Button = view.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_host_booking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookingList[position]

        // 2. Fill the card with Guest data
        holder.tvPropertyName.text = booking.propertyName
        holder.tvGuestInfo.text = "Guest: ${booking.guestName} (${booking.guestPhone})"
        holder.tvDate.text = "Check-in: ${booking.checkInDate}"
        holder.tvStatus.text = "Status: ${booking.status}"

        // 3. Connect to Firebase for status updates
        val databaseRef = FirebaseDatabase.getInstance().getReference("Bookings").child(booking.bookingId)

        // ACCEPT BUTTON LOGIC
        holder.btnAccept.setOnClickListener {
            databaseRef.child("status").setValue("Accepted")
            Toast.makeText(holder.itemView.context, "Booking Accepted!", Toast.LENGTH_SHORT).show()
        }

        // REJECT BUTTON LOGIC
        holder.btnReject.setOnClickListener {
            databaseRef.child("status").setValue("Rejected")
            Toast.makeText(holder.itemView.context, "Booking Rejected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }
}