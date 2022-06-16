package com.sunggil.flowandroidtest.ui.fragment.recent

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentRecentBinding
import com.sunggil.flowandroidtest.ui.adapter.RecentRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>() {

    private val recentViewModel : RecentViewModel by viewModels()

    @Inject
    lateinit var adapter : RecentRecyclerAdapter

    private var keyword : String? = null

    override fun getLayout() : Int = R.layout.fragment_recent

    override fun setContentView() {
        this.binding.viewModel = this.recentViewModel

        this.binding.rvRecent.adapter = this.adapter
        this.binding.rvRecent.layoutManager = LinearLayoutManager(requireContext())
        this.adapter.setOnItemClickListener(this.onItemClickListener)

        this.recentViewModel.recentList.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.adapter.setAll(it)
            }
        })

        //db 에서 조회
        this.recentViewModel.selectKeywords(this.keyword)
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener<String> {
        override fun onItemClick(item : String) {
            val action = RecentFragmentDirections.actionNavigationRecentToNavigationSearch(item)
            findNavController().navigate(action)
        }
    }
}