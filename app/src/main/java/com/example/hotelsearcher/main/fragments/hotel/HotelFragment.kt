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
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo

const val DATA_TAG = "data"
const val BORDER_SIZE = 1

class HotelFragment : Fragment() {

    private val viewModel by viewModelsExt {
        HotelViewModel(requireArguments().getString(DATA_TAG)!!)
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
                binding.stars.rating = stars
                binding.name.text = name
                binding.address.text = address
                binding.distance.text = distanceToShow
                binding.suitesAvailability.text = suitesToShow
                binding.longitude.text = lon
                binding.latitude.text = lat
            }
        }

        viewModel.err.observe(viewLifecycleOwner) {
            binding.errorText.text = it
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

    companion object HotelFragmentFactory {
        fun newInstance(hotelID: String): HotelFragment {
            val myFragment = HotelFragment()
            val args = Bundle()

            args.putString(DATA_TAG, hotelID)

            return myFragment.also { it.arguments = args }
        }
    }
}