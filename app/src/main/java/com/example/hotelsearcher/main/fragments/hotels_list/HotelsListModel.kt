package com.example.hotelsearcher.main.fragments.hotels_list

const val SUITES_SEPARATOR = ", "
const val DELIMITER = ':'

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