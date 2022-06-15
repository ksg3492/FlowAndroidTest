package com.sunggil.flowandroidtest.ui.search

import androidx.fragment.app.viewModels
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentSearchBinding
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewModel : SearchViewModel by viewModels()

    override fun getLayout() : Int = R.layout.fragment_search

    override fun setContentView() {

    }
}