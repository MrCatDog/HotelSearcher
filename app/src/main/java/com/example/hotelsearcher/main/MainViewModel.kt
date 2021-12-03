package com.example.hotelsearcher.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo
import com.example.hotelsearcher.utils.MutableLiveEvent

class MainViewModel : ViewModel() {

    private val _hotelsListEvent = MutableLiveEvent<Unit>()
    val hotelsListEvent: LiveData<Unit>
        get() = _hotelsListEvent

    private val _hotelItemEvent = MutableLiveEvent<BaseHotelInfo>()
    val hotelItemEvent: LiveData<BaseHotelInfo>
        get() = _hotelItemEvent

    init {
        _hotelsListEvent.setValue(Unit)
    }

    fun setHotelFragment(hotel: BaseHotelInfo) {
        _hotelItemEvent.setValue(hotel)
    }

}