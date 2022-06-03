package com.sunggil.flowandroidtest.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.databinding.ActivityMainBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.adapter.MovieRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import com.sunggil.flowandroidtest.ui.base.PagingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    @Inject
    lateinit var adapter : MovieRecyclerAdapter
    @Inject
    lateinit var pagingHelper : PagingHelper

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.binding.viewModel = this.mainViewModel
        this.binding.lifecycleOwner = this

        this.binding.btSearch.setOnClickListener(this)
        this.binding.etSearch.setText(this.mainViewModel.searchedKeyword)
        this.binding.etSearch.addTextChangedListener(textWatcher)
        this.binding.etSearch.setOnEditorActionListener(editorActionListener)

        this.binding.rvMovie.adapter = this.adapter
        this.binding.rvMovie.layoutManager = LinearLayoutManager(this)
        this.adapter.setOnItemClickListener(this.onItemClickListener)

        this.pagingHelper.setCallback(this.loadMoreScrollListener)
        this.binding.rvMovie.addOnScrollListener(this.pagingHelper.getScrollListener())

        this.mainViewModel.movieList.observe(this, Observer {
            it?.let {
                this.adapter.setAll(it)
                this.pagingHelper.setIsLoading(false)
            }
        })
    }

    /**
     * 입력 감지 리스너
     */
    private val textWatcher : TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {
        }

        override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {
        }

        override fun afterTextChanged(s : Editable?) {
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

        Snackbar.make(binding.root, getString(msgId), Snackbar.LENGTH_SHORT).show()
    }

    /**
     * 페이징 처리 리스너
     */
    private val loadMoreScrollListener = object : PagingHelper.OnLoadMoreDataCallback {
        override fun onLoadMoreData() {
            Log.e("SG2", "onLoadMoreData...")
            mainViewModel.search(mainViewModel.searchedKeyword, failCallback)


        }
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(item : Movie) {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                startActivity(browserIntent)
            } catch (e : Exception) {
                Snackbar.make(binding.root, getString(R.string.error_browser), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 클릭 리스너
     */
    override fun onClick(v : View?) {
        when (v?.id) {
            binding.btSearch.id -> {
                val input = binding.etSearch.text.toString()

                this.pagingHelper.setIsEndItem(false)

                this.mainViewModel.clear()
                this.mainViewModel.search(input, failCallback)
            }
        }
    }
}