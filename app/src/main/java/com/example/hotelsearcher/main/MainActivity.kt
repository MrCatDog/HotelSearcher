package com.example.hotelsearcher.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelsearcher.utils.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hotelsearcher.additional_info_activity.AdditionalInfoActivity
import com.example.hotelsearcher.R
import com.example.hotelsearcher.shared.Constants.ITEM_ID
import com.example.hotelsearcher.shared.fragment.ProgressFragment
import com.example.hotelsearcher.shared.fragment.TryAgainFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModelsExt {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hotelsListFragment = HotelsListFragment()

        viewModel.isLoading.observe(this) {
            if(it) {
                changeFragment(ProgressFragment())
            } else {
                //changeFragment(hotelsListFragment)
            }

        }
        //todo говно говна, совсем не хочется обновлять каждый раз
        viewModel.hotels.observe(this) {
            changeFragment(hotelsListFragment)
            hotelsListFragment.setData(it)
        }

        viewModel.err.observe(this) {
            changeFragment(TryAgainFragment.newInstance(it))
        }

    }

    private fun changeFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragment.id, newFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_distance -> {
                viewModel.sortByDistance()
                true
            }
            R.id.action_sort_by_suites -> {
                viewModel.sortBySuites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onRecyclerItemClicked(id:String) {
        val intent = Intent(this, AdditionalInfoActivity::class.java).apply {
            putExtra(ITEM_ID, id)
        }
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        viewModel.tryAgainBtnClicked()
    }
}