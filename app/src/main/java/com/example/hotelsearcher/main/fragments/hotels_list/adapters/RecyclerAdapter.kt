package com.example.hotelsearcher.main.fragments.hotels_list.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelsearcher.R
import com.example.hotelsearcher.databinding.HotelItemBinding
import com.example.hotelsearcher.main.fragments.hotels_list.BaseHotelInfo
import com.example.hotelsearcher.main.fragments.hotels_list.HotelsListFragment
import java.util.ArrayList

class RecyclerAdapter(private val listener: HotelsListFragment) :
    RecyclerView.Adapter<RecyclerAdapter.VH>() {

    private var items: List<BaseHotelInfo> = ArrayList()

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: HotelItemBinding = HotelItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.hotel_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.stars.rating = item.stars
        holder.binding.name.text = item.name
        holder.binding.address.text = item.address
        holder.binding.distance.text = item.distanceToShow
        holder.binding.suitesAvailability.text = item.suitesToShow
        holder.binding.root.setOnClickListener {
            listener.onRecyclerItemClicked(item)
        }
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged") //all of them changed
    fun setData(items: List<BaseHotelInfo>) {
        this.items = items
        notifyDataSetChanged()
    }

}