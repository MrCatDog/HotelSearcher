package com.example.hotelsearcher.main.fragments.hotel

import com.example.hotelsearcher.main.fragments.hotels_list.DELIMITER
import com.example.hotelsearcher.main.fragments.hotels_list.SUITES_SEPARATOR
import com.google.gson.annotations.SerializedName

const val URL_IMG = "https://github.com/iMofas/ios-android-test/raw/master/"

data class FullHotelInfo(
    val id: String,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    @SerializedName("suites_availability")
    val suites: String,
    val lon: String,
    val lat: String,
    @SerializedName("image")
    val url: String
) {
    val imgUrl
        get() = URL_IMG + url
    val distanceToShow
        get() = distance.toString()
    val suitesToShow
        get() = suites.trim(DELIMITER).replace(DELIMITER.toString(), SUITES_SEPARATOR)
}