package com.example.hotelsearcher.main.fragments.hotels_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsearcher.R
import com.example.hotelsearcher.databinding.HotelsListFragmentBinding
import com.example.hotelsearcher.main.fragments.hotel.HOTEL_BASE_INFO_TAG
import com.example.hotelsearcher.main.fragments.hotels_list.adapters.RecyclerAdapter
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.main.fragments.hotels_list.HotelsListViewModel.Visibility.*

class HotelsListFragment : Fragment() {

    private val adapter = RecyclerAdapter(this)

    private val viewModel by viewModelsExt {
        HotelsListViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HotelsListFragmentBinding.inflate(inflater)

        binding.hotelList.layoutManager = LinearLayoutManager(context)
        binding.hotelList.adapter = adapter

        viewModel.hotels.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel.err.observe(viewLifecycleOwner) {
            binding.errorText.text = it
        }

        viewModel.visibility.observe(viewLifecycleOwner) {
            when (it) {
                LOADING -> binding.apply {
                    progressbar.visibility = View.VISIBLE
                    groupError.visibility = View.GONE
                    groupHotels.visibility = View.GONE
                }
                HOTELS -> binding.apply {
                    progressbar.visibility = View.GONE
                    groupError.visibility = View.GONE
                    groupHotels.visibility = View.VISIBLE
                }
                else -> binding.apply {
                    progressbar.visibility = View.GONE
                    groupError.visibility = View.VISIBLE
                    groupHotels.visibility = View.GONE
                }
            }
        }

        binding.tryAgainBtn.setOnClickListener {
            viewModel.tryAgainBtnClicked()
        }

        binding.sortByDistanceBtn.setOnClickListener {
            viewModel.sortByDistance()
        }

        binding.sortBySuites.setOnClickListener {
            viewModel.sortBySuites()
        }

        return binding.root
    }

    fun onRecyclerItemClicked(hotel: BaseHotelInfo) {
        findNavController().navigate(
            R.id.action_hotelsListFragment_to_hotelFragment,
            bundleOf(HOTEL_BASE_INFO_TAG to hotel)
        )
    }
}