package com.example.hotelsearcher.utils.network

import com.example.hotelsearcher.main.fragments.hotel.FullHotelInfo
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo
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
        onResponse: KFunction1<Response<List<BaseHotelInfo>>, Unit>,
        onFail: KFunction2<Call<List<BaseHotelInfo>>, Throwable, Unit>
    ) {
        val hotels = serverApi.getHotelList()

        hotels.enqueue(object : Callback<List<BaseHotelInfo>> {

            override fun onFailure(call: Call<List<BaseHotelInfo>>, t: Throwable) {
                onFail(call, t)
            }

            override fun onResponse(
                call: Call<List<BaseHotelInfo>>,
                response: Response<List<BaseHotelInfo>>
            ) {
                onResponse(response)
            }
        })
    }

    fun requestHotel(
        id: String,
        onResponse: KFunction1<Response<FullHotelInfo>, Unit>,
        onFail: KFunction2<Call<FullHotelInfo>, Throwable, Unit>
    ) {
        val hotel = serverApi.getHotel(id)

        hotel.enqueue(object : Callback<FullHotelInfo> {

            override fun onFailure(call: Call<FullHotelInfo>, t: Throwable) {
                onFail(call, t)
            }

            override fun onResponse(
                call: Call<FullHotelInfo>,
                response: Response<FullHotelInfo>
            ) {
                onResponse(response)
            }
        })
    }
}