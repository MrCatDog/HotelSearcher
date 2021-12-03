package com.example.hotelsearcher.main.fragments.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo
import com.example.hotelsearcher.utils.DataReceiver
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection

const val URL_HOTEL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"
const val URL_HOTEL_ENDING = ".json"
const val URL_IMG = "https://github.com/iMofas/ios-android-test/raw/master/"

const val HOTEL_IMG = "image"
const val HOTEL_LAT = "lat"
const val HOTEL_LON = "lon"

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
        dataReceiver.requestData(
            URL_HOTEL + baseHotelInfo.id + URL_HOTEL_ENDING,
            this::onHotelResponse,
            this::onHotelFailure
        )
    }

    private fun onHotelResponse(response: Response) {
        if (response.code == HttpURLConnection.HTTP_OK) {
            val answer = JSONObject(response.body!!.string())

            _hotel.postValue(
                FullHotelInfo(
                    baseHotelInfo,
                    lon = answer.getString(HOTEL_LON),
                    lat = answer.getString(HOTEL_LAT),
                    url = URL_IMG + answer.getString(HOTEL_IMG)
                )
            )
            _visibility.postValue(Visibility.HOTEL)
        } else {
            _visibility.postValue(Visibility.ERROR)
            _err.postValue(response.message)
        }
    }

    private fun onHotelFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e.message)
            _visibility.postValue(Visibility.ERROR)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotel()
    }

}