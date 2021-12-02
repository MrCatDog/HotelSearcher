package com.example.hotelsearcher

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val SUITES_SEPARATOR = ", "

@Parcelize
data class BaseHotelInfo(
    val id: String,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suites: List<String>
) : Parcelable {
    val distanceToShow
        get() = distance.toString()
    val suitesToShow
        get() = suites.joinToString(separator = SUITES_SEPARATOR)
}

@Parcelize
data class FullHotelInfo(
    val base: BaseHotelInfo,
    val lon: String,
    val lat: String,
    val url: String
) : Parcelable