package com.example.hotelsearcher.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.utils.MutableLiveEvent

class MainViewModel : ViewModel() {

    private val _hotelsListEvent = MutableLiveEvent<Unit>()
    val hotelsListEvent: LiveData<Unit>
        get() = _hotelsListEvent

    private val _hotelItemEvent = MutableLiveEvent<String>()
    val hotelItemEvent: LiveData<String>
        get() = _hotelItemEvent

    init {
        _hotelsListEvent.setValue(Unit)
    }

    fun setHotelFragment(hotelID: String) {
        _hotelItemEvent.setValue(hotelID)
    }

}