package com.example.hotelsearcher.main.fragments.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.hotelsearcher.R
import com.example.hotelsearcher.databinding.HotelFragmentBinding
import com.example.hotelsearcher.utils.CutOffBorderTransformation
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.main.fragments.hotel.HotelViewModel.Visibility.*

const val BORDER_SIZE = 1
const val HOTEL_BASE_INFO_TAG = "data"

class HotelFragment : Fragment() {

    private val viewModel by viewModelsExt {
        HotelViewModel(arguments?.getParcelable(HOTEL_BASE_INFO_TAG)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HotelFragmentBinding.inflate(inflater)

        viewModel.hotel.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it.imgUrl)
                .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
                .transform(CutOffBorderTransformation(BORDER_SIZE))
                .placeholder(R.drawable.hotel_default)
                .error(R.drawable.hotel_default)
                .into(binding.imageHolder)

            it.apply {
                binding.stars.rating = baseHotelInfo.stars
                binding.name.text = baseHotelInfo.name
                binding.address.text = baseHotelInfo.address
                binding.distance.text = baseHotelInfo.distanceToShow
                binding.suitesAvailability.text = baseHotelInfo.suitesToShow
                binding.longitude.text = lon
                binding.latitude.text = lat
            }
        }

        viewModel.err.observe(viewLifecycleOwner) {
            binding.errorText.text = it ?: getString(R.string.unknown_error)
        }

        viewModel.visibility.observe(viewLifecycleOwner) {
            when (it) {
                LOADING -> binding.apply {
                    progressbar.visibility = View.VISIBLE
                    groupError.visibility = View.GONE
                    groupHotel.visibility = View.GONE
                }
                HOTEL -> binding.apply {
                    progressbar.visibility = View.GONE
                    groupError.visibility = View.GONE
                    groupHotel.visibility = View.VISIBLE
                }
                else -> binding.apply {
                    progressbar.visibility = View.GONE
                    groupError.visibility = View.VISIBLE
                    groupHotel.visibility = View.GONE
                }
            }
        }

        binding.tryAgainBtn.setOnClickListener {
            viewModel.tryAgainBtnClicked()
        }

        return binding.root
    }
}