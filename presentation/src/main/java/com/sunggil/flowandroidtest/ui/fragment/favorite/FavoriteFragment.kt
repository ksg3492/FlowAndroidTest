package com.sunggil.flowandroidtest.ui.fragment.favorite

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentFavoriteBinding
import com.sunggil.flowandroidtest.ui.adapter.RecentRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val favoriteViewModel : FavoriteViewModel by viewModels()

    @Inject
    lateinit var adapter : RecentRecyclerAdapter

    override fun getLayout() : Int = R.layout.fragment_favorite

    override fun setContentView() {
        this.binding.viewModel = this.favoriteViewModel

        this.binding.rvFavorite.adapter = this.adapter
        this.binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        this.adapter.setOnItemClickListener(this.onItemClickListener)

        this.favoriteViewModel.favorites.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.adapter.setAll(it.map { it.title } as ArrayList<String>)
            }
        })

        //db 에서 조회
        this.favoriteViewModel.getFavorites()
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener<String> {
        override fun onItemClick(item : String) {
        }
    }
}