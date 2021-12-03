package com.example.hotelsearcher.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsearcher.databinding.HotelsListFragmentBinding
import com.example.hotelsearcher.utils.viewModelsExt

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
            binding.progressbar.visibility = View.GONE
            binding.groupError.visibility = View.VISIBLE
            binding.errorText.text = it
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
                binding.groupHotels.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.GONE
                binding.groupHotels.visibility = View.VISIBLE
            }
        }

        binding.tryAgainBtn.setOnClickListener {
            binding.groupError.visibility = View.GONE
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

    fun onRecyclerItemClicked(id: String) {
        (requireActivity() as MainActivity).setHotelFragment(id)
    }



}