package com.sunggil.flowandroidtest.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.sunggil.flowandroidtest.databinding.ItemMovieBinding
import com.sunggil.flowandroidtest.domain.Movie

class MovieViewHolder(val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item : Movie) {
        binding.tvTitle.text = item.title
        binding.tvDate.text = item.pubDate
        binding.tvRate.text = item.userRating
    }
}