package com.sunggil.flowandroidtest.ui.viewholder

import com.sunggil.flowandroidtest.databinding.ItemRecentBinding
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener

class RecentViewHolder(
    val binding : ItemRecentBinding,
    itemClickListener : OnItemClickListener<String>?,
) : BaseViewHolder<String, ItemRecentBinding>(binding, itemClickListener) {

    override fun setItem(item : String) {
        super.setItem(item)

        binding.tvKeyword.text = item
    }
}