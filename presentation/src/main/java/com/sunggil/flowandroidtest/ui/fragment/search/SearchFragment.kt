package com.sunggil.flowandroidtest.ui.fragment.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunggil.flowandroidtest.NavigationArgument
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.databinding.FragmentSearchBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.adapter.MovieRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.BaseFragment
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

        //Navigation으로 넘어온 검색어가 있을 경우, 검색 프로세스 진행
        if (this.needToSearch) {
            this.needToSearch = false
            this.search(this.searchViewModel.searchedKeyword)
        }
    }

    /**
     * Arguments 파싱
     */
    private fun parseArguments() {
        arguments?.let {
            if (it.containsKey(NavigationArgument.ARGUMENT_KEYWORD)) {  //검색 키워드
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
     * 키 버튼 감지 리스너
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
     * 에러 스낵바
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
     * 페이징 처리 리스너
     */
    private val loadMoreScrollListener = object : PagingHelper.OnLoadMoreDataCallback {
        override fun onLoadMoreData() {
            Log.e("SG2", "onLoadMoreData...")
            searchViewModel.search(searchViewModel.searchedKeyword, failCallback)
        }
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener<Movie> {
        override fun onItemClick(item : Movie) {
            val action = SearchFragmentDirections.actionNavigationSearchToNavigationDetail(item.image, item.title)
            findNavController().navigate(action)

//            try {
//                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
//                startActivity(browserIntent)
//            } catch (e : Exception) {
//                showSnackbar(getString(R.string.error_browser))
//            }
        }
    }

    /**
     * 검색 기능
     */
    private fun search(keyword : String) {
        this.pagingHelper.setIsEndItem(false)

        this.searchViewModel.clear()
        this.searchViewModel.insertKeyword(keyword, failCallback)
    }

    /**
     * 키보드 숨김
     */
    fun hideKeyboard() {
        val inputManager = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    /**
     * 클릭 리스너
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