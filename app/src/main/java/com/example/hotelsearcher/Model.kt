package com.example.hotelsearcher.main

const val SUITES_SEPARATOR = ", "

data class BaseHotelInfo(
    val id: String,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suites: List<String>
) {
    val distanceToShow
        get() = distance.toString()
    val suitesToShow
        get() = suites.joinToString(separator = SUITES_SEPARATOR)
}

data class FullHotelInfo(val base: BaseHotelInfo, val lon: String, val lat: String, val url: String)