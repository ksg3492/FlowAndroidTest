package com.sunggil.flowandroidtest.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemMovieBinding
import com.sunggil.flowandroidtest.domain.Movie

class MovieViewHolder(
    val requestManager : RequestManager,
    val binding : ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item : Movie) {
        binding.tvTitle.text = item.title
        binding.tvDate.text = item.pubDate
        binding.tvRate.text = item.userRating

        requestManager.load(item.image)
            .centerCrop()
            .error(R.drawable.img_error)
            .into(binding.ivMovie)
    }
}