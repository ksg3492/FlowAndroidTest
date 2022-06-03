package com.sunggil.flowandroidtest.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import com.sunggil.flowandroidtest.ui.viewholder.BaseViewHolder

abstract class BaseRecyclerAdapter<T, VB : ViewBinding, VH : BaseViewHolder<T, VB>>
    : RecyclerView.Adapter<VH>() {

    protected var itemClickListener : OnItemClickListener<T>? = null
    private val lists = ArrayList<T>()

    abstract fun getLayout() : Int

    fun setOnItemClickListener(clickListener : OnItemClickListener<T>) {
        this.itemClickListener = clickListener
    }

    /**
     * 데이터 초기화
     */
    fun setAll(list : ArrayList<T>) {
        this.lists.clear()
        this.lists.addAll(list.clone() as ArrayList<T>)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder : VH, position : Int) {
        this.lists[position]?.let {
            holder.setItem(it)
        }
    }

    override fun getItemCount() : Int {
        return this.lists.count()
    }
}