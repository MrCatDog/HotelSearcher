package com.example.hotelsearcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumsearcher.util.viewModelsExt
import com.example.hotelsearcher.databinding.MainActivityBinding
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {

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

        viewModel.hotels.observe(this) {
            adapter.setData(it)
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

    override fun onItemClick(position: Int) {
//        val intent = Intent(this, AlbumInfoActivity::class.java).apply {
//            putExtra(Shared.CLICKED_ITEM_ID, viewModel.getItemIDByPosition(position))
//        }
//        startForResult.launch(intent)
    }
}