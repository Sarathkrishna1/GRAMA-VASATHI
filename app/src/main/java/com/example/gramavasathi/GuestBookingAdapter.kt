package com.example.gramavasathi

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuestBookingAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<GuestBookingAdapter.ViewHolder>() {

    // 1. ViewHolder: Connects the Kotlin code to the IDs in item_guest_booking.xml
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvGuestPropName)
        val date: TextView = v.findViewById(R.id.tvGuestDate)
        val status: TextView = v.findViewById(R.id.tvGuestStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guest_booking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val b = bookingList[position]

        // 2. Set the text from the Booking data
        holder.name.text = b.propertyName
        holder.date.text = "Check-in Date: ${b.checkInDate}"
        holder.status.text = "Status: ${b.status}"

        // 3. COLOR LOGIC: Changes text color based on Host's action
        when (b.status) {
            "Accepted" -> {
                holder.status.setTextColor(Color.parseColor("#2E7D32")) // Success Green
                holder.status.text = "Status: ACCEPTED ✅"
            }
            "Rejected" -> {
                holder.status.setTextColor(Color.RED) // Danger Red
                holder.status.text = "Status: REJECTED ❌"
            }
            else -> {
                holder.status.setTextColor(Color.parseColor("#FF9800")) // Pending Orange
                holder.status.text = "Status: PENDING ⏳"
            }
        }
    }

    override fun getItemCount() = bookingList.size
}