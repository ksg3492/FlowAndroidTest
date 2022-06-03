package com.sunggil.flowandroidtest.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemMovieBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener

class MovieViewHolder(
    val requestManager : RequestManager,
    val binding : ItemMovieBinding,
    val itemClickListener : OnItemClickListener?
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    fun setItem(item : Movie) {
        binding.root.tag = item
        binding.root.setOnClickListener(this)

        binding.tvTitle.text = item.title
        binding.tvDate.text = item.pubDate
        binding.tvRate.text = item.userRating

        requestManager.load(item.image)
            .centerCrop()
            .error(R.drawable.img_error)
            .into(binding.ivMovie)
    }

    override fun onClick(v : View?) {
        this.itemClickListener?.onItemClick(v?.tag as Movie)
    }
}