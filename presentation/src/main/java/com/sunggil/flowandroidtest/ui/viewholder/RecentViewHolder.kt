package com.sunggil.flowandroidtest.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sunggil.flowandroidtest.databinding.ItemRecentBinding
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener

class RecentViewHolder(
    val binding : ItemRecentBinding,
    val itemClickListener : OnItemClickListener<String>?,
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    fun setItem(item : String) {
        binding.root.tag = item
        binding.root.setOnClickListener(this)

        binding.tvKeyword.text = item
    }

    override fun onClick(v : View?) {
        this.itemClickListener?.onItemClick(v?.tag as String)
    }
}