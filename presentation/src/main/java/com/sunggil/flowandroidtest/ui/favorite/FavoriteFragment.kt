package com.sunggil.flowandroidtest.ui.favorite

import androidx.fragment.app.viewModels
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentFavoriteBinding
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val favoriteViewModel : FavoriteViewModel by viewModels()

    override fun getLayout() : Int = R.layout.fragment_favorite

    override fun setContentView() {


    }
}