package com.furkanpasalioglu.coinyeni.ui.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanpasalioglu.coinyeni.R
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin
import com.furkanpasalioglu.coinyeni.databinding.ItemLayoutBinding

class MainAdapter(private var coins: List<DatabaseCoin>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var binding:ItemLayoutBinding = ItemLayoutBinding.bind(itemView)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(coin: DatabaseCoin) {
            binding.title.text = coin.symbol
            binding.textViewKar.text = coin.getYuzdeKar(coin.lastPrice)
            if (binding.textViewKar.text.contains("-"))
                binding.textViewKar.setTextColor(Color.parseColor("#FF5050"))
            else
                binding.textViewKar.setTextColor(Color.parseColor("#27BCFF"))
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent,false)
        , onItemClicked)

    override fun getItemCount(): Int = coins.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(coins[position])

    fun addData(list: List<DatabaseCoin>) {
        coins = list
    }
}