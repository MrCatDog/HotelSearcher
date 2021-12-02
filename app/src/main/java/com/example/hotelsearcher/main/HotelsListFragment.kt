package com.example.hotelsearcher.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsearcher.BaseHotelInfo
import com.example.hotelsearcher.databinding.HotelsListFragmentBinding

class HotelsListFragment : Fragment() {

    private val adapter = RecyclerAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HotelsListFragmentBinding.inflate(inflater)

        binding.hotelList.layoutManager = LinearLayoutManager(context)
        binding.hotelList.adapter = adapter

        return binding.root
    }

    fun setData(hotels: List<BaseHotelInfo>) {
        adapter.setData(hotels)
    }

    fun onRecyclerItemClicked(id: String) {
        (requireActivity() as MainActivity).onRecyclerItemClicked(id)
    }

}