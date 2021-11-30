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

const val URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/0777.json"

const val HOTEL_ID = "id"
const val HOTEL_NAME = "name"
const val HOTEL_ADDRESS = "address"
const val HOTEL_DISTANCE = "distance"
const val HOTEL_STARS = "stars"
const val HOTEL_SUITES = "suites_availability"

class MainViewModel : ViewModel() {

    private val dataReceiver = DataReceiver()

    private val _hotels = MutableLiveData<List<BaseHotelInfo>>()
    val hotels: LiveData<List<BaseHotelInfo>>
        get() = _hotels

    private val _err = MutableLiveEvent<Exception>()
    val err: LiveData<Exception>
        get() = _err

    init {
        loadHotels()
    }

    fun loadHotels() {
        dataReceiver.requestData(URL, this::onResponse, this::onFailure)
    }

    private fun onFailure(call: Call, e: IOException) {
        if (!call.isCanceled()) {
            _err.postValue(e)
        }
    }

    private fun onResponse(response: Response) {
        if(response.code == 200) {
            val answer = JSONArray(response.body?.string() ?: throw IOException())
            val hotels = ArrayList<BaseHotelInfo>()
            for (i in 0 until answer.length()) {
                val hotel = answer.getJSONObject(i)
                hotels.add(
                    BaseHotelInfo(
                        hotel.getString(COLLECTION_NAME),
                        hotel.getString(ARTIST_NAME),
                        hotel.getString(ARTWORK),
                        hotel.getString(ID)
                    )
                )
            }
        } else {
            _err.postValue()
        }

        if (!response.isSuccessful) {
            _err.postValue(IOException(response.message + response.code.toString()))
        } else {

            val count = answer.get(RESULT_COUNT_TAG) as Int
            if (count == 0) {
                _noResultEvent.postValue(Unit)
                return
            } else {
                val jArray = answer.get(RESULT_TAG) as JSONArray
                val albums = ArrayList<BaseAlbumInfo>()
                var album: JSONObject
                for (i in 0 until count) {
                    album = jArray.getJSONObject(i)
                    albums.add(
                        BaseAlbumInfo(
                            album.getString(COLLECTION_NAME),
                            album.getString(ARTIST_NAME),
                            album.getString(ARTWORK),
                            album.getString(ID)
                        )
                    )
                }
                albums.sortBy { it.albumName.lowercase() }
                _hotels.postValue(albums)
            }
        }
    }

}