package com.example.hotelsearcher.main.fragments.hotels_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.utils.DataReceiver
import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import java.util.ArrayList

const val URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/0777.json"

const val HOTEL_ID = "id"
const val HOTEL_NAME = "name"
const val HOTEL_ADDRESS = "address"
const val HOTEL_DISTANCE = "distance"
const val HOTEL_STARS = "stars"
const val HOTEL_SUITES = "suites_availability"

const val DELIMITER = ':'

class HotelsListViewModel : ViewModel() {

    enum class Visibility {
        HOTELS,
        ERROR,
        LOADING
    }

    private val dataReceiver = DataReceiver()

    private val _hotels = MutableLiveData<List<BaseHotelInfo>>()
    val hotels: LiveData<List<BaseHotelInfo>>
        get() = _hotels

    private val _visibility = MutableLiveData<Visibility>()
    val visibility: LiveData<Visibility>
        get() = _visibility

    private val _err = MutableLiveData<String>()
    val err: LiveData<String>
        get() = _err

    init {
        loadHotels()
    }

    private fun loadHotels() {
        _visibility.value = Visibility.LOADING
        dataReceiver.requestData(URL, this::onMainResponse, this::onMainFailure)
    }

    fun sortBySuites() {
        val hotels = _hotels.value
        _hotels.value = hotels?.sortedByDescending { it.suites.size } ?: ArrayList<BaseHotelInfo>()
    }

    fun sortByDistance() {
        val hotels = _hotels.value
        _hotels.value = hotels?.sortedBy { it.distance } ?: ArrayList<BaseHotelInfo>()
    }

    private fun onMainFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e.message)
            _visibility.postValue(Visibility.ERROR)
        }
    }

    private fun onMainResponse(response: Response) {
        if (response.code == HttpURLConnection.HTTP_OK) {
            val answer = JSONArray(response.body!!.string())
            val hotels = ArrayList<BaseHotelInfo>()
            for (i in 0 until answer.length()) {
                val hotel = answer.getJSONObject(i)
                hotels.add(
                    BaseHotelInfo(
                        id = hotel.getString(HOTEL_ID),
                        name = hotel.getString(HOTEL_NAME),
                        address = hotel.getString(HOTEL_ADDRESS),
                        stars = hotel.getDouble(HOTEL_STARS).toFloat(),
                        distance = hotel.getDouble(HOTEL_DISTANCE).toFloat(),
                        suites = hotel.getString(HOTEL_SUITES)
                            .trim(DELIMITER)
                            .split(DELIMITER)
                    )
                )
            }
            _visibility.postValue(Visibility.HOTELS)
            _hotels.postValue(hotels)
        } else {
            _visibility.postValue(Visibility.ERROR)
            _err.postValue(response.message)
        }
    }

    fun tryAgainBtnClicked() {
        loadHotels()
    }

}