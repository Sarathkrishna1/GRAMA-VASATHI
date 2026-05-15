package com.example.gramavasathi

// An 'object' in Kotlin is a Singleton - it exists everywhere in your app
object PropertyStorage {
    // This list will hold the properties the Host adds
    val hostProperties = mutableListOf<FarmStay>()
}