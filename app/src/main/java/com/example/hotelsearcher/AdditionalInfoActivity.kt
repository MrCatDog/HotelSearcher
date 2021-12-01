package com.example.hotelsearcher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.albumsearcher.util.viewModelsExt
import com.example.hotelsearcher.databinding.AdditionalInfoActivityBinding

class AdditionalInfoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: AdditionalInfoActivityBinding

    private val viewModel by viewModelsExt {
        AdditionalInfoViewModel(intent.getStringExtra("test")!!) //todo проверить как передавать инфу в Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdditionalInfoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            changeFragment(ProgressFragment())
        }

        viewModel.err.observe(this) {
            changeFragment(TryAgainFragment(this))
        }

        viewModel.hotel.observe(this) {
            changeFragment(HotelFragment(it))
        }
    }

    private fun changeFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragment.id, newFragment)
            .commit()
    }

    override fun onClick(v: View?) {
        viewModel.tryAgainBtnClicked()
    }
}