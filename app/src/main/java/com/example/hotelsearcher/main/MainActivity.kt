package com.example.hotelsearcher.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding
import androidx.fragment.app.Fragment
import com.example.hotelsearcher.main.fragments.hotel.HotelFragment
import com.example.hotelsearcher.main.fragments.hotels_list.HotelsListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModelsExt {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.hotelItemEvent.observe(this) {
            changeFragment(HotelFragment.newInstance(it))
        }

        viewModel.hotelsListEvent.observe(this) {
            changeFragment(HotelsListFragment())
        }

    }

    private fun changeFragment(newFragment: Fragment) {
        val fTrans = supportFragmentManager.beginTransaction()
            .replace(binding.fragment.id, newFragment)
        if (newFragment is HotelFragment) {
            fTrans.addToBackStack(null)
        }
        fTrans.commit()
    }

    fun setHotelFragment(hotelID: String) {
        viewModel.setHotelFragment(hotelID)
    }
}