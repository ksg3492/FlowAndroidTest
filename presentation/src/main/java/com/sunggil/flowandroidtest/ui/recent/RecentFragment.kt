package com.sunggil.flowandroidtest.ui.recent

import androidx.fragment.app.viewModels
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentRecentBinding
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>() {

    private val recentViewModel : RecentViewModel by viewModels()

    override fun getLayout() : Int = R.layout.fragment_recent

    override fun setContentView() {


    }
}