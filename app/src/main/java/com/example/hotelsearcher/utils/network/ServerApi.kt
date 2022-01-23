package com.example.hotelsearcher.utils.network

import com.example.hotelsearcher.main.fragments.hotel.FullHotelInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerApi {

    @GET("0777.json")
    fun getHotelList(): Call<List<HotelsListResponse>>

    @GET("{id}.json")
    fun getHotel(@Path("id") hotelId: String): Call<FullHotelInfo>

}