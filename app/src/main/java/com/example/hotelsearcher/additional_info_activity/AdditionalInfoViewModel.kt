package com.example.hotelsearcher.additional_info_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.utils.MutableLiveEvent
import com.example.hotelsearcher.main.*
import com.example.hotelsearcher.shared.Constants
import com.example.hotelsearcher.utils.DataReceiver
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK

const val URL_HOTEL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"
const val URL_HOTEL_ENDING = ".json"

const val URL_IMG = "https://github.com/iMofas/ios-android-test/raw/master/"

class AdditionalInfoViewModel(private val itemId: String) : ViewModel() {

    private val dataReceiver = DataReceiver()

    private val _hotel = MutableLiveData<FullHotelInfo>()
    val hotel: LiveData<FullHotelInfo>
        get() = _hotel

    private val _isLoading = MutableLiveEvent<Unit>()
    val isLoading: LiveData<Unit>
        get() = _isLoading

    private val _err = MutableLiveEvent<String>()
    val err: LiveData<String>
        get() = _err

    init {
        loadHotel()
    }

    private fun loadHotel() {
        _isLoading.setValue(Unit)
        dataReceiver.requestData(
            URL_HOTEL + itemId + URL_HOTEL_ENDING,
            this::onHotelResponse,
            this::onHotelFailure
        )
    }

    private fun onHotelResponse(response: Response) {
        if (response.code == HTTP_OK) {
            val answer = JSONObject(response.body!!.string())

            _hotel.postValue(
                FullHotelInfo(
                    base = BaseHotelInfo(
                        id = answer.getString(Constants.HOTEL_ID),
                        name = answer.getString(Constants.HOTEL_NAME),
                        address = answer.getString(Constants.HOTEL_ADDRESS),
                        stars = answer.getDouble(Constants.HOTEL_STARS).toFloat(),
                        distance = answer.getDouble(Constants.HOTEL_DISTANCE).toFloat(),
                        suites = answer.getString(Constants.HOTEL_SUITES)
                            .trim(Constants.DELIMITER)
                            .split(Constants.DELIMITER)
                    ),
                    lon = answer.getString(Constants.HOTEL_LON),
                    lat = answer.getString(Constants.HOTEL_LAT),
                    url = URL_IMG + answer.getString(Constants.HOTEL_IMG)
                )
            )
        } else {
            _err.postValue(response.message)
        }
    }

    private fun onHotelFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e.message)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotel()
    }
}