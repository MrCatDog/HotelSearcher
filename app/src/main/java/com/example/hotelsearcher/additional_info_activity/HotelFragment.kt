package com.example.hotelsearcher.additional_info_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.hotelsearcher.R
import com.example.hotelsearcher.databinding.HotelFragmentBinding
import com.example.hotelsearcher.FullHotelInfo
import com.example.hotelsearcher.utils.CutOffBorderTransformation

const val DATA_TAG = "data"
const val BORDER_SIZE = 1

class HotelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HotelFragmentBinding.inflate(inflater)
        val hotel : FullHotelInfo = requireArguments().getParcelable(DATA_TAG)!!

        Glide.with(this)
            .load(hotel.url)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .transform(CutOffBorderTransformation(BORDER_SIZE))
            .placeholder(R.drawable.hotel_default)
            .error(R.drawable.hotel_default)
            .into(binding.imageHolder)

        hotel.apply {
            binding.stars.rating = base.stars
            binding.name.text = base.name
            binding.address.text = base.address
            binding.distance.text = base.distanceToShow
            binding.suitesAvailability.text = base.suitesToShow
            binding.longitude.text = lon
            binding.latitude.text = lat
        } //я не люблю DataBinding, но если надо сделал бы

        return binding.root
    }

    companion object HotelFragmentFactory {
        fun newInstance(hotel: FullHotelInfo): HotelFragment {
            val myFragment = HotelFragment()
            val args = Bundle()

            args.putParcelable(DATA_TAG, hotel)

            return myFragment.also { it.arguments = args }
        }
    }
}