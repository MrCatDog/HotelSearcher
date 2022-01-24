package com.example.hotelsearcher.utils.network

import com.google.gson.annotations.SerializedName

data class HotelResponse(
    @SerializedName("lon")
    val lon: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("image")
    val url: String
)