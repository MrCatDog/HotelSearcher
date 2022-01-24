package com.example.hotelsearcher.main.fragments.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo
import com.example.hotelsearcher.utils.network.DataReceiver
import com.example.hotelsearcher.utils.network.HotelResponse
import retrofit2.Call
import retrofit2.Response

const val URL_IMG = "https://github.com/iMofas/ios-android-test/raw/master/"

class HotelViewModel(private val baseHotelInfo: BaseHotelInfo) : ViewModel() {

    enum class Visibility {
        HOTEL,
        ERROR,
        LOADING
    }

    private val dataReceiver = DataReceiver()

    private val _hotel = MutableLiveData<FullHotelInfo>()
    val hotel: LiveData<FullHotelInfo>
        get() = _hotel

    private val _err = MutableLiveData<String>()
    val err: LiveData<String>
        get() = _err

    private val _visibility = MutableLiveData<Visibility>()
    val visibility: LiveData<Visibility>
        get() = _visibility

    init {
        loadHotel()
    }

    private fun loadHotel() {
        _visibility.value = Visibility.LOADING
        dataReceiver.requestHotel(
            baseHotelInfo.id,
            this::onHotelResponse,
            this::onHotelFailure
        )
    }

    private fun onHotelResponse(response: Response<HotelResponse>) {
        if (response.isSuccessful) {
            val hotelResponse = response.body()!! //todo
            _hotel.postValue(
                FullHotelInfo(
                    baseHotelInfo,
                    hotelResponse.lon,
                    hotelResponse.lat,
                    URL_IMG + hotelResponse.url
                )
            )
            _visibility.postValue(Visibility.HOTEL)
        } else {
            _visibility.postValue(Visibility.ERROR)
            _err.postValue(response.errorBody().toString())
        }
    }

    private fun onHotelFailure(call: Call<HotelResponse>, e: Throwable) {
        if (!call.isCanceled) {
            _err.postValue(e.message)
            _visibility.postValue(Visibility.ERROR)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotel()
    }

}