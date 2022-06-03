package com.sunggil.flowandroidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemRecentBinding
import com.sunggil.flowandroidtest.ui.viewholder.RecentViewHolder
import javax.inject.Inject

class RecentRecyclerAdapter @Inject constructor() : BaseRecyclerAdapter<String, ItemRecentBinding, RecentViewHolder>() {

    override fun getLayout() : Int = R.layout.item_recent

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecentViewHolder {
        val binding : ItemRecentBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recent, parent, false)

        return RecentViewHolder(binding, itemClickListener)
    }
}