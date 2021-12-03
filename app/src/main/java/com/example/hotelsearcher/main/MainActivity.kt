package com.example.hotelsearcher.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding
import androidx.fragment.app.Fragment
import com.example.hotelsearcher.additional_info_activity.HotelFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModelsExt {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setHotelListFragment()

    }

    private fun changeFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragment.id, newFragment)
            .commit()
    }

    fun setHotelListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragment.id, HotelsListFragment())
            .commit()
    }

    fun setHotelFragment(id:String) {
        //changeFragment(HotelFragment.newInstance(id))
        supportFragmentManager.beginTransaction()
            .replace(binding.fragment.id, HotelFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}