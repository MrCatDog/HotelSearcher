package com.example.hotelsearcher.utils.network

import com.google.gson.annotations.SerializedName

data class HotelsListResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("stars")
    val stars: Float,
    @SerializedName("distance")
    val distance: Float,
    @SerializedName("suites_availability")
    val suites: String
)