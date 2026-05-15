package com.example.gramavasathi

import java.io.Serializable

data class Booking(
    val bookingId: String = "",
    val propertyId: String = "",
    val propertyName: String = "",
    val hostId: String = "",
    val guestName: String = "",
    val guestPhone: String = "",
    val checkInDate: String = "",
    val status: String = "Pending" // Status: Pending, Accepted, Rejected
) : Serializable