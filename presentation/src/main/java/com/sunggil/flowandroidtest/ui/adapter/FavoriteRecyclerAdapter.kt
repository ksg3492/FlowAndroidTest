package com.sunggil.flowandroidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ItemFavoriteBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.viewholder.FavoriteViewHolder
import javax.inject.Inject

class FavoriteRecyclerAdapter @Inject constructor() : BaseRecyclerAdapter<Movie, ItemFavoriteBinding, FavoriteViewHolder>() {

    @Inject
    lateinit var requestManager : RequestManager

    override fun getLayout() : Int = R.layout.item_favorite

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : FavoriteViewHolder {
        val binding : ItemFavoriteBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayout(), parent, false)

        return FavoriteViewHolder(requestManager, binding, itemClickListener)
    }
}