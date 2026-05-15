package com.example.gramavasathi

import java.io.Serializable

data class FarmStay(
    val name: String,
    val location: String,
    val price: String,
    val activity: String,
    val description: String,
    val imageRes: Int,     // Main Farm Image
    val activityImg: Int,  // New: Activity Image
    val foodImg: Int,      // New: Food Image
    val rating: String
) : Serializable