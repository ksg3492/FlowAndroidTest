package com.sunggil.flowandroidtest.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener

abstract class BaseViewHolder<T, VB : ViewBinding>(
    private val _binding : VB,
    private val _itemClickListener : OnItemClickListener<T>?,
) : RecyclerView.ViewHolder(_binding.root), View.OnClickListener {

    open fun setItem(item : T) {
        _binding.root.tag = item
        _binding.root.setOnClickListener(this)
    }

    override fun onClick(v : View?) {
        this._itemClickListener?.onItemClick(v?.tag as T)
    }
}