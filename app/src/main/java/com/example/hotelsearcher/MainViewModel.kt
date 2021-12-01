package com.example.hotelsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albumsearcher.util.MutableLiveEvent
import com.example.hotelsearcher.utils.DataReceiver
import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicBoolean

const val URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/0777.json"

const val HOTEL_ID = "id"
const val HOTEL_NAME = "name"
const val HOTEL_ADDRESS = "address"
const val HOTEL_DISTANCE = "distance"
const val HOTEL_STARS = "stars"
const val HOTEL_SUITES = "suites_availability"

const val DELIMITER = ':'

const val HTTP_OK = 200

class MainViewModel : ViewModel() {

    private val dataReceiver = DataReceiver()

    private val _hotels = MutableLiveData<List<BaseHotelInfo>>()
    val hotels: LiveData<List<BaseHotelInfo>>
        get() = _hotels

    val isItemListInUse = AtomicBoolean(false)

    private val _err = MutableLiveEvent<Exception>()
    val err: LiveData<Exception>
        get() = _err

    init {
        loadHotels()
    }

    private fun loadHotels() {
        if(isItemListInUse.compareAndSet(false, true)) {
            dataReceiver.requestData(URL, this::onResponse, this::onFailure)
        }
    }

    fun sortBySuites() {
        val hotels = _hotels.value
        if(isItemListInUse.compareAndSet(false, true)) {
            _hotels.value = hotels?.sortedByDescending { it.suites.size }
        }
    }

    fun sortByDistance() {
        //todo flag чтоб racing condition избежать
        //todo вынести в новый поток
        val hotels = _hotels.value
        _hotels.value = hotels?.sortedBy { it.distance }
    }


    private fun onFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e)
        }
    }

    private fun onResponse(response: Response) {
        if (response.code == HTTP_OK) {
            val answer = JSONArray(response.body?.string() ?: throw IOException())
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
                        suites = hotel.getString(HOTEL_SUITES).trim(DELIMITER).split(DELIMITER)
                    )
                )
            }
            _hotels.postValue(hotels)
            isItemListInUse.set(false)
        } else {
            _err.postValue(IOException())
        }
    }

    fun getItemIDByPosition(position: Int) {

    }
}