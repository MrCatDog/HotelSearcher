package com.example.hotelsearcher.utils.network

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

class DataReceiver {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val serverApi: ServerApi = retrofit.create(ServerApi::class.java)

    fun requestHotels(
        onResponse: KFunction1<Response<List<HotelsListResponse>>, Unit>,
        onFail: KFunction2<Call<List<HotelsListResponse>>, Throwable, Unit>
    ) {
        val hotels = serverApi.getHotelList()

        hotels.enqueue(object : Callback<List<HotelsListResponse>> {

            override fun onFailure(call: Call<List<HotelsListResponse>>, t: Throwable) {
                onFail(call, t)
            }

            override fun onResponse(
                call: Call<List<HotelsListResponse>>,
                response: Response<List<HotelsListResponse>>
            ) {
                onResponse(response)
            }
        })
    }

    fun requestHotel(
        id: String,
        onResponse: KFunction1<Response<HotelResponse>, Unit>,
        onFail: KFunction2<Call<HotelResponse>, Throwable, Unit>
    ) {
        val hotel = serverApi.getHotel(id)

        hotel.enqueue(object : Callback<HotelResponse> {

            override fun onFailure(call: Call<HotelResponse>, t: Throwable) {
                onFail(call, t)
            }

            override fun onResponse(
                call: Call<HotelResponse>,
                response: Response<HotelResponse>
            ) {
                onResponse(response)
            }
        })
    }
}