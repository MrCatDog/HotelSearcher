package com.example.hotelsearcher.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumsearcher.util.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.hotelsearcher.AdditionalInfoActivity
import com.example.hotelsearcher.R

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    private val viewModel by viewModelsExt {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hotelList.layoutManager = LinearLayoutManager(baseContext)
        val adapter = RecyclerAdapter(this)
        binding.hotelList.adapter = adapter

        binding.tryAgainBtn.setOnClickListener {
            viewModel.tryAgainBtnClicked()
        }

        viewModel.hotels.observe(this) {
            adapter.setData(it)
        }

        viewModel.isLoading.observe(this) {
            if(it) {
                binding.progressbar.visibility = View.VISIBLE
                binding.hotelList.visibility = View.GONE
                binding.tryAgainBtn.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.GONE
                binding.hotelList.visibility = View.VISIBLE
                binding.tryAgainBtn.visibility = View.GONE
            }
        }

        viewModel.err.observe(this) {
            binding.tryAgainBtn.visibility = View.VISIBLE
            binding.progressbar.visibility = View.GONE
            binding.hotelList.visibility = View.GONE
        }

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
            putExtra("test", id)
        }
        startActivity(intent)
    }
}