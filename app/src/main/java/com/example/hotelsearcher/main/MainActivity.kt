package com.example.hotelsearcher.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding
import androidx.navigation.fragment.NavHostFragment
import com.example.hotelsearcher.R
import com.example.hotelsearcher.main.fragments.hotel.DATA_TAG
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModelsExt {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        viewModel.hotelItemEvent.observe(this) {
            navController.navigate(
                R.id.action_hotelsListFragment_to_hotelFragment,
                bundleOf(DATA_TAG to it)
            )
        }

    }

    fun setHotelFragment(hotel: BaseHotelInfo) {
        viewModel.setHotelFragment(hotel)
    }
}