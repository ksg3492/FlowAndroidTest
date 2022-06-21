package com.sunggil.flowandroidtest.ui.fragment.favorite

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunggil.flowandroidtest.NavigationArgument
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentFavoriteBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.activity.detail.DetailActivity
import com.sunggil.flowandroidtest.ui.adapter.FavoriteRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import com.sunggil.flowandroidtest.ui.base.ClickType
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val favoriteViewModel : FavoriteViewModel by viewModels()

    @Inject
    lateinit var adapter : FavoriteRecyclerAdapter

    override fun getLayout() : Int = R.layout.fragment_favorite

    override fun setContentView() {
        this.binding.viewModel = this.favoriteViewModel

        this.binding.rvFavorite.adapter = this.adapter
        this.binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        this.adapter.setOnItemClickListener(this.onItemClickListener)

        this.favoriteViewModel.favorites.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.adapter.setAll(it)
            }
        })

        //db 에서 조회
        this.favoriteViewModel.getFavoriteList()
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener<Movie> {
        override fun onItemClick(type : ClickType, item : Movie) {
            when (type) {
                ClickType.ROOT -> {
                    //Detail Activity
                    val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra(NavigationArgument.ARGUMENT_MOVIE, item)
                    }
                    startActivity(intent)
                }
                ClickType.DELETE -> {
                    favoriteViewModel.deleteFavorite(item, deleteFavoriteCallback)
                }
            }
        }
    }

    /**
     * 즐겨찾기 삭제 이후 adapter refresh
     */
    private var deleteFavoriteCallback : ((Movie) -> Unit)? = {
        val index = adapter.getAll().indexOf(it)

        adapter.getAll().removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.deleteFavoriteCallback = null
    }
}