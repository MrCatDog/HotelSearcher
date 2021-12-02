package com.example.hotelsearcher.additional_info_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hotelsearcher.R
import com.example.hotelsearcher.databinding.HotelFragmentBinding
import com.example.hotelsearcher.main.FullHotelInfo
import com.example.hotelsearcher.shared.Constants

class HotelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HotelFragmentBinding.inflate(inflater)
        val args = requireArguments()

        Glide.with(this)
            .load(args.getString(Constants.HOTEL_IMG))
            //todo граница
            .placeholder(R.drawable.hotel_default)
            .error(R.drawable.hotel_default)
            .into(binding.imageHolder)

        args.apply {
            binding.stars.rating = getFloat(Constants.HOTEL_STARS)
            binding.name.text = getString(Constants.HOTEL_NAME)
            binding.address.text = getString(Constants.HOTEL_ADDRESS)
            binding.distance.text = getString(Constants.HOTEL_DISTANCE)
            binding.suitesAvailability.text = getString(Constants.HOTEL_SUITES)
            binding.longitude.text = getString(Constants.HOTEL_LAT)
            binding.latitude.text = getString(Constants.HOTEL_LON)
        }

        return binding.root
    }

    companion object HotelFragmentFactory {
        fun newInstance(hotel: FullHotelInfo): HotelFragment {
            val myFragment = HotelFragment()
            val args = Bundle()

            args.putString(Constants.HOTEL_NAME, hotel.base.name) //мог бы и Parcelable сделать
            args.putString(Constants.HOTEL_DISTANCE, hotel.base.distanceToShow)
            args.putString(Constants.HOTEL_ADDRESS, hotel.base.address)
            args.putString(Constants.HOTEL_SUITES, hotel.base.suitesToShow)
            args.putFloat(Constants.HOTEL_STARS, hotel.base.stars)
            args.putString(Constants.HOTEL_LAT, hotel.lat)
            args.putString(Constants.HOTEL_LON, hotel.lon)
            args.putString(Constants.HOTEL_IMG, hotel.url)
            return myFragment.also { it.arguments = args }
        }
    }
}