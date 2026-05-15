package com.example.gramavasathi

import java.io.Serializable

data class Property(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val price: String = "",
    val description: String = "",
    val images: List<String> = listOf(), // For Cloud Photos (Base64)
    val hostId: String = "",
    val localImage: Int = 0,             // Main Thumbnail
    val localGallery: List<Int> = listOf() // NEW: For Local Slider Photos
) : Serializable