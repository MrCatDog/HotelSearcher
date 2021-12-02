package com.example.hotelsearcher.additional_info_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.databinding.AdditionalInfoActivityBinding
import com.example.hotelsearcher.shared.Constants.ITEM_ID
import com.example.hotelsearcher.shared.fragment.ProgressFragment
import com.example.hotelsearcher.shared.fragment.TryAgainFragment

class AdditionalInfoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: AdditionalInfoActivityBinding

    private val viewModel by viewModelsExt {
        AdditionalInfoViewModel(intent.getStringExtra(ITEM_ID)!!) //todo проверить как передавать инфу в Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdditionalInfoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            changeFragment(ProgressFragment())
        }

        viewModel.err.observe(this) {
            changeFragment(TryAgainFragment.newInstance(it))
        }

        viewModel.hotel.observe(this) {
            changeFragment(HotelFragment.newInstance(it))
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