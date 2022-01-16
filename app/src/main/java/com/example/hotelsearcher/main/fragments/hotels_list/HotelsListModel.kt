package com.example.hotelsearcher.main.fragments.hotels_list

import com.google.gson.annotations.SerializedName

const val SUITES_SEPARATOR = ", "
const val DELIMITER = ':'

data class BaseHotelInfo(
    val id: String,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    @SerializedName("suites_availability")
    val suites: String
) {
    val distanceToShow
        get() = distance.toString()
    val suitesList
        get() = suites.trim(DELIMITER).split(DELIMITER)
    val suitesToShow
        get() = suitesList.joinToString(separator = SUITES_SEPARATOR)
}