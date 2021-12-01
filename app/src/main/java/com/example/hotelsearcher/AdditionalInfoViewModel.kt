package com.example.hotelsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albumsearcher.util.MutableLiveEvent
import com.example.hotelsearcher.main.*
import com.example.hotelsearcher.utils.DataReceiver
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

const val URL_HOTEL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"
const val URL_HOTEL_ENDING = ".json"

const val URL_IMG = "https://github.com/iMofas/ios-android-test/raw/master/"

const val HOTEL_IMG = "image"
const val HOTEL_LAT = "lat"
const val HOTEL_LON = "lon"

class AdditionalInfoViewModel(private val itemId: String) : ViewModel() {

    private val dataReceiver = DataReceiver()

    private val _hotel = MutableLiveData<FullHotelInfo>()
    val hotel: LiveData<FullHotelInfo>
        get() = _hotel

    private val _isLoading = MutableLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _err = MutableLiveEvent<Exception>()
    val err: LiveData<Exception>
        get() = _err

    init {
        loadHotel()
    }

    private fun loadHotel() {
        _isLoading.setValue(true)
        dataReceiver.requestData(
            URL_HOTEL + itemId + URL_HOTEL_ENDING,
            this::onHotelResponse,
            this::onHotelFailure
        )
    }

    private fun onHotelResponse(response: Response) {
        if (response.code == HTTP_OK) {
            val answer = JSONObject(
                response.body?.string() ?: throw IOException()
            ) //todo: куда уходит это исключение?

            _hotel.postValue(
                FullHotelInfo(
                    base = BaseHotelInfo(
                        id = answer.getString(HOTEL_ID),
                        name = answer.getString(HOTEL_NAME),
                        address = answer.getString(HOTEL_ADDRESS),
                        stars = answer.getDouble(HOTEL_STARS).toFloat(),
                        distance = answer.getDouble(HOTEL_DISTANCE).toFloat(),
                        suites = answer.getString(HOTEL_SUITES).trim(DELIMITER).split(DELIMITER)
                    ),
                    lon = answer.getString(HOTEL_LON),
                    lat = answer.getString(HOTEL_LAT),
                    url = URL_IMG + answer.getString(HOTEL_IMG)
                )
            )

        } else {
            _err.postValue(IOException())
        }
    }

    private fun onHotelFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotel()
    }
}