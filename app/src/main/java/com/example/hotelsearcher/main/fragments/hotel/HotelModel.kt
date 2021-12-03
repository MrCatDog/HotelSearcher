package com.example.hotelsearcher.main.fragments.hotel

import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo

data class FullHotelInfo(
    val base: BaseHotelInfo,
    val lon: String,
    val lat: String,
    val url: String
)