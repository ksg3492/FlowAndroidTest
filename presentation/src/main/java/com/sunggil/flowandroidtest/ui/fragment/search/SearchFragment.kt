package com.sunggil.flowandroidtest.ui.fragment.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunggil.flowandroidtest.NavigationArgument
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.databinding.FragmentSearchBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.activity.detail.DetailActivity
import com.sunggil.flowandroidtest.ui.adapter.MovieRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import com.sunggil.flowandroidtest.ui.base.ClickType
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import com.sunggil.flowandroidtest.ui.base.PagingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), View.OnClickListener {

    private val searchViewModel : SearchViewModel by viewModels()

    @Inject
    lateinit var adapter : MovieRecyclerAdapter

    @Inject
    lateinit var pagingHelper : PagingHelper

    private var needToSearch = false

    override fun getLayout() : Int = R.layout.fragment_search

    override fun setContentView() {
        this.binding.viewModel = this.searchViewModel

        this.parseArguments()

        this.binding.btSearch.setOnClickListener(this)

        this.binding.etSearch.setText(this.searchViewModel.searchedKeyword)
        this.binding.etSearch.setOnEditorActionListener(editorActionListener)

        this.binding.rvMovie.adapter = this.adapter
        this.binding.rvMovie.layoutManager = LinearLayoutManager(requireContext())
        this.adapter.setOnItemClickListener(this.onItemClickListener)

        this.pagingHelper.setCallback(this.loadMoreScrollListener)
        this.binding.rvMovie.addOnScrollListener(this.pagingHelper.getScrollListener())

        this.searchViewModel.movieList.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.adapter.setAll(it)
                this.pagingHelper.setIsLoading(false)
            }
        })

        //Navigation?????? ????????? ???????????? ?????? ??????, ?????? ???????????? ??????
        if (this.needToSearch) {
            this.needToSearch = false
            this.search(this.searchViewModel.searchedKeyword)
        }
    }

    /**
     * Arguments ??????
     */
    private fun parseArguments() {
        arguments?.let {
            if (it.containsKey(NavigationArgument.ARGUMENT_KEYWORD)) {  //?????? ?????????
                val keyword = it.getString(NavigationArgument.ARGUMENT_KEYWORD)
                if (keyword.isNullOrEmpty().not()) {
                    this.searchViewModel.searchedKeyword = keyword!!
                    this.needToSearch = true
                }
            }
            it.clear()
        }
    }

    /**
     * ??? ?????? ?????? ?????????
     */
    private val editorActionListener : TextView.OnEditorActionListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v : TextView?, actionId : Int, event : KeyEvent?) : Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_DONE,
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_SEARCH,
                EditorInfo.IME_ACTION_NEXT,
                EditorInfo.IME_ACTION_SEND,
                -> {
                    binding.btSearch.callOnClick()
                    return true
                }
            }
            return false
        }
    }

    /**
     * ?????? ?????????
     */
    private val failCallback : ((ErrorCode) -> Unit) = {
        val msgId : Int = when (it) {
            ErrorCode.EMPTY_KEYWORD -> R.string.empty_keyword
            ErrorCode.LAST_PAGE -> {
                this.pagingHelper.setIsEndItem(true)

                R.string.last_page
            }
            else -> R.string.unknown_error
        }

        showSnackbar(getString(msgId))
    }

    /**
     * ????????? ?????? ?????????
     */
    private val loadMoreScrollListener = object : PagingHelper.OnLoadMoreDataCallback {
        override fun onLoadMoreData() {
            Log.e("SG2", "onLoadMoreData...")
            searchViewModel.search(searchViewModel.searchedKeyword, failCallback)
        }
    }

    /**
     * ????????? ????????? ?????? ?????????
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
            }
        }
    }

    /**
     * ?????? ??????
     */
    private fun search(keyword : String) {
        this.pagingHelper.setIsEndItem(false)

        this.searchViewModel.clear()
        this.searchViewModel.insertKeyword(keyword, failCallback)
    }

    /**
     * ????????? ??????
     */
    fun hideKeyboard() {
        val inputManager = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    /**
     * ?????? ?????????
     */
    override fun onClick(v : View?) {
        when (v?.id) {
            binding.btSearch.id -> {
                val keyword = binding.etSearch.text.toString()
                this.search(keyword)
                this.hideKeyboard()
            }
        }
    }
}