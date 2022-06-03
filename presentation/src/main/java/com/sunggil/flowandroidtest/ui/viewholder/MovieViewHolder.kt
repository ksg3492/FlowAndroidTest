package com.sunggil.flowandroidtest.ui.viewholder

import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemMovieBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener

class MovieViewHolder(
    val requestManager : RequestManager,
    val binding : ItemMovieBinding,
    itemClickListener : OnItemClickListener<Movie>?
) : BaseViewHolder<Movie, ItemMovieBinding>(binding, itemClickListener) {

    override fun setItem(item : Movie) {
        super.setItem(item)

        binding.tvTitle.text = item.title
        binding.tvDate.text = item.pubDate
        binding.tvRate.text = item.userRating

        requestManager.load(item.image)
            .centerCrop()
            .error(R.drawable.img_error)
            .into(binding.ivMovie)
    }
}