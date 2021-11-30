package com.example.hotelsearcher

const val SUITES_SEPARATOR = ","

data class BaseHotelInfo(
    val id: String,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suites: ArrayList<String>
) {
    val distanceToShow
        get() = distance.toString()
    val suitesToShow
        get() = suites.joinToString(separator = SUITES_SEPARATOR)
}