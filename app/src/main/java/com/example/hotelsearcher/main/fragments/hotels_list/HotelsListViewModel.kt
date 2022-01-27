package com.example.hotelsearcher.main.fragments.hotels_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.utils.network.DataReceiver
import com.example.hotelsearcher.utils.network.HotelsListResponse
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

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
        dataReceiver.requestHotels(this::onMainResponse, this::onMainFailure)
    }

    fun sortBySuites() {
        val hotels = _hotels.value
        _hotels.value =
            hotels?.sortedByDescending { it.suites.size } ?: ArrayList<BaseHotelInfo>()
    }

    fun sortByDistance() {
        val hotels = _hotels.value
        _hotels.value = hotels?.sortedBy { it.distance } ?: ArrayList<BaseHotelInfo>()
    }

    private fun onMainFailure(call: Call<List<HotelsListResponse>>, e: Throwable) {
        if (!call.isCanceled) {
            _err.postValue(e.message)
            _visibility.postValue(Visibility.ERROR)
        }
    }

    private fun onMainResponse(response: Response<List<HotelsListResponse>>) {
        if (response.isSuccessful) {
            _visibility.postValue(Visibility.HOTELS)
            val hotels: List<BaseHotelInfo> = response.body()?.map {
                BaseHotelInfo(
                    id = it.id,
                    name = it.name,
                    address = it.address,
                    stars = it.stars,
                    distance = it.distance,
                    suites = it.suites.trim(DELIMITER).split(DELIMITER)
                )
            } ?: listOf() //todo что я вообще должен тут ответить? ошибка?
            _hotels.postValue(hotels)
        } else {
            _visibility.postValue(Visibility.ERROR)
            _err.postValue(response.errorBody().toString())
        }
    }

    fun tryAgainBtnClicked() {
        loadHotels()
    }

}