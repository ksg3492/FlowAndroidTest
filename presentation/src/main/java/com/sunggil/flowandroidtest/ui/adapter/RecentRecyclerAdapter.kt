package com.sunggil.flowandroidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemRecentBinding
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import com.sunggil.flowandroidtest.ui.viewholder.RecentViewHolder
import javax.inject.Inject

class RecentRecyclerAdapter @Inject constructor() : RecyclerView.Adapter<RecentViewHolder>() {

    private var itemClickListener : OnItemClickListener<String>? = null
    private val lists = ArrayList<String>()
    @Inject
    lateinit var requestManager : RequestManager

    fun setOnItemClickListener(clickListener : OnItemClickListener<String>) {
        this.itemClickListener = clickListener
    }

    /**
     * 데이터 초기화
     */
    fun setAll(list : ArrayList<String>) {
        this.lists.clear()
        this.lists.addAll(list.clone() as ArrayList<String>)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecentViewHolder {
        val binding : ItemRecentBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recent, parent, false)

        return RecentViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder : RecentViewHolder, position : Int) {
        this.lists[position]?.let {
            holder.setItem(it)
        }
    }

    override fun getItemCount() : Int {
        return this.lists.count()
    }
}