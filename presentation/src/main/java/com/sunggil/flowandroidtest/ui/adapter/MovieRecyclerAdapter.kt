package com.sunggil.flowandroidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemMovieBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.viewholder.MovieViewHolder
import javax.inject.Inject

class MovieRecyclerAdapter @Inject constructor() : BaseRecyclerAdapter<Movie, ItemMovieBinding, MovieViewHolder>() {

    @Inject
    lateinit var requestManager : RequestManager

    override fun getLayout() : Int = R.layout.item_movie

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MovieViewHolder {
        val binding : ItemMovieBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayout(), parent, false)

        return MovieViewHolder(requestManager, binding, itemClickListener)
    }
}