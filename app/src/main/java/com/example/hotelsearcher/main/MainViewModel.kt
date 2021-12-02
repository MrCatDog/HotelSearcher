package com.example.hotelsearcher.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.utils.MutableLiveEvent
import com.example.hotelsearcher.shared.Constants
import com.example.hotelsearcher.utils.DataReceiver
import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.util.ArrayList

const val URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/0777.json"

class MainViewModel : ViewModel() {

    private val dataReceiver = DataReceiver()

    private val _hotels = MutableLiveData<List<BaseHotelInfo>>()
    val hotels: LiveData<List<BaseHotelInfo>>
        get() = _hotels

    private val _isLoading = MutableLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _err = MutableLiveEvent<String>()
    val err: LiveData<String>
        get() = _err

    init {
        loadHotels()
    }

    private fun loadHotels() {
        _isLoading.setValue(true)
        dataReceiver.requestData(URL, this::onMainResponse, this::onMainFailure)
    }

    fun sortBySuites() {
        val hotels = _hotels.value
        _hotels.value = hotels?.sortedByDescending { it.suites.size } ?: ArrayList<BaseHotelInfo>()
        //todo мб вынести эти кнопки из  меню
    }

    fun sortByDistance() {
        val hotels = _hotels.value
        _hotels.value = hotels?.sortedBy { it.distance } ?: ArrayList<BaseHotelInfo>()
    }

    private fun onMainFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e.message)
        }
    }

    private fun onMainResponse(response: Response) {
        if (response.code == HTTP_OK) {
            val answer = JSONArray(response.body!!.string())
            val hotels = ArrayList<BaseHotelInfo>()
            for (i in 0 until answer.length()) {
                val hotel = answer.getJSONObject(i)
                hotels.add(
                    BaseHotelInfo(
                        id = hotel.getString(Constants.HOTEL_ID),
                        name = hotel.getString(Constants.HOTEL_NAME),
                        address = hotel.getString(Constants.HOTEL_ADDRESS),
                        stars = hotel.getDouble(Constants.HOTEL_STARS).toFloat(),
                        distance = hotel.getDouble(Constants.HOTEL_DISTANCE).toFloat(),
                        suites = hotel.getString(Constants.HOTEL_SUITES)
                            .trim(Constants.DELIMITER)
                            .split(Constants.DELIMITER)
                    )
                )
            }
            _isLoading.postValue(false)
            _hotels.postValue(hotels)
        } else {
            _err.postValue(response.message)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotels()
    }
}