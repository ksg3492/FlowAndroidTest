package com.sunggil.flowandroidtest.ui.viewholder

import android.view.View
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemFavoriteBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.base.ClickType
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener

class FavoriteViewHolder(
    val requestManager : RequestManager,
    val binding : ItemFavoriteBinding,
    val itemClickListener : OnItemClickListener<Movie>?,
) : BaseViewHolder<Movie, ItemFavoriteBinding>(binding, itemClickListener) {

    override fun setItem(item : Movie) {
        super.setItem(item)

        binding.tvTitle.text = item.title
        binding.tvDate.text = item.pubDate
        binding.tvRate.text = item.userRating

        binding.ibClose.tag = item
        binding.ibClose.setOnClickListener(this)

        requestManager.load(item.image)
            .centerCrop()
            .error(R.drawable.img_error)
            .into(binding.ivMovie)
    }

    override fun onClick(v : View?) {
        when (v?.id) {
            binding.ibClose.id -> {
                this.itemClickListener?.onItemClick(ClickType.DELETE, v.tag as Movie)
            }
            else -> {
                super.onClick(v)
            }
        }
    }
}