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
import com.example.hotelsearcher.utils.CutOffBorderTransformation
import com.example.hotelsearcher.utils.viewModelsExt

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
                .load(it.url)
                .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
                .transform(CutOffBorderTransformation(BORDER_SIZE))
                .placeholder(R.drawable.hotel_default)
                .error(R.drawable.hotel_default)
                .into(binding.imageHolder)

            it.apply {
                binding.stars.rating = base.stars
                binding.name.text = base.name
                binding.address.text = base.address
                binding.distance.text = base.distanceToShow
                binding.suitesAvailability.text = base.suitesToShow
                binding.longitude.text = lon
                binding.latitude.text = lat
            }
        }

        viewModel.err.observe(viewLifecycleOwner) {
            binding.progressbar.visibility = View.GONE
            binding.groupError.visibility = View.VISIBLE
            binding.errorText.text = it
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
                binding.groupHotel.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.GONE
                binding.groupHotel.visibility = View.VISIBLE
            }
        }

        binding.tryAgainBtn.setOnClickListener {
            binding.groupError.visibility = View.GONE
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