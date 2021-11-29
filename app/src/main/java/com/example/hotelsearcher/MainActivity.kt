package com.example.hotelsearcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumsearcher.util.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {

    lateinit var binding: MainActivityBinding

    val viewModel by viewModelsExt {
        MainViewModel()
    }

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hotelList.layoutManager = LinearLayoutManager(baseContext)
        val adapter = RecyclerAdapter(this)
        binding.hotelList.adapter = adapter
    }
}