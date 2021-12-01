package com.example.hotelsearcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hotelsearcher.databinding.HotelFragmentBinding
import com.example.hotelsearcher.main.FullHotelInfo

class HotelFragment(private val hotel: FullHotelInfo) : Fragment() {

    private var _binding: HotelFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HotelFragmentBinding.inflate(inflater)

        Glide.with(this)
            .load(hotel.url) //какой хлам возвращает эта ваша "API"
            //todo граница
            .placeholder(R.drawable.hotel_default)
            .error(R.drawable.hotel_default)
            .into(binding.imageHolder)

        binding.stars.rating = hotel.base.stars
        binding.name.text = hotel.base.name
        binding.address.text = hotel.base.address
        binding.distance.text = hotel.base.distanceToShow
        binding.suitesAvailability.text = hotel.base.suitesToShow
        binding.longitude.text = hotel.lon
        binding.latitude.text = hotel.lat

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}