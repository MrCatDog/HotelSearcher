package com.example.hotelsearcher.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo
import com.example.hotelsearcher.utils.MutableLiveEvent

class MainViewModel : ViewModel() {

    private val _hotelItemEvent = MutableLiveEvent<BaseHotelInfo>()
    val hotelItemEvent: LiveData<BaseHotelInfo>
        get() = _hotelItemEvent

    fun setHotelFragment(hotel: BaseHotelInfo) {
        _hotelItemEvent.setValue(hotel)
    }

}