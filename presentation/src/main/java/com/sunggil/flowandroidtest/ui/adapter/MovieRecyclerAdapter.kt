package com.sunggil.flowandroidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemMovieBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.viewholder.MovieViewHolder
import javax.inject.Inject

class MovieRecyclerAdapter @Inject constructor() : RecyclerView.Adapter<MovieViewHolder>() {
    private val lists = ArrayList<Movie>()
    @Inject lateinit var requestManager : RequestManager

    /**
     * 데이터 초기화
     */
    fun setAll(list : ArrayList<Movie>) {
        this.lists.clear()
        this.lists.addAll(list.clone() as ArrayList<Movie>)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MovieViewHolder {
        val binding : ItemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)

        return MovieViewHolder(requestManager, binding)
    }

    override fun onBindViewHolder(holder : MovieViewHolder, position : Int) {
        this.lists[position]?.let {
            holder.setItem(it)
        }
    }

    override fun getItemCount() : Int {
        return this.lists.count()
    }
}