package com.example.hotelsearcher.main.fragments.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.utils.network.DataReceiver
import retrofit2.Call
import retrofit2.Response

class HotelViewModel(private val hotelID: String) : ViewModel() {

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
            hotelID,
            this::onHotelResponse,
            this::onHotelFailure
        )
    }

    private fun onHotelResponse(response: Response<FullHotelInfo>) {
        if (response.isSuccessful) {
            _hotel.postValue(
                response.body()
            )
            _visibility.postValue(Visibility.HOTEL)
        } else {
            _visibility.postValue(Visibility.ERROR)
            _err.postValue(response.errorBody().toString())
        }
    }

    private fun onHotelFailure(call: Call<FullHotelInfo>, e: Throwable) {
        if (!call.isCanceled) {
            _err.postValue(e.message)
            _visibility.postValue(Visibility.ERROR)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotel()
    }

}