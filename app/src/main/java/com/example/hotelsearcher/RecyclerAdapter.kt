package com.example.hotelsearcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelsearcher.databinding.HotelItemBinding
import java.util.ArrayList

class RecyclerAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.VH>() {

    private var items: List<JokesModel.JokeItem> = ArrayList()

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: HotelItemBinding = HotelItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.hotel_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.stars.rating =

    }

    override fun getItemCount() = items.size

    fun setData(items: List<JokesModel.JokeItem>, itemCount: Int) {
        this.items = items
        notifyItemRangeInserted(items.lastIndex, itemCount)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}