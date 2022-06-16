package com.sunggil.flowandroidtest.ui.activity.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
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
import com.sunggil.flowandroidtest.ui.base.ActivityValue
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import com.sunggil.flowandroidtest.ui.base.PagingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    private var snackbar : Snackbar? = null
    private var snackbarText : String? = null

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
        this.binding.btSearchRecent.setOnClickListener(this)

        this.binding.etSearch.setText(this.mainViewModel.searchedKeyword)
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
     * Activity Result
     */
    private val recentResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.extras?.get(ActivityValue.Extra.KEYWORD)?.let {
                //pass된 keyword로 새로 검색
                val keyword = it as String
                binding.etSearch.setText(keyword)
                this.search(keyword)
            }
        }
    }

    override fun onBackPressed() {
        this.appFinishDelay()
    }

    /**
     * 취소키로 종료 기능
     */
    private fun appFinishDelay() {
        val exitString = getString(R.string.snackbar_msg_app_exit)
        if (this.snackbar?.isShown == true && (exitString == snackbarText)) {
            finish()
        } else {
            this.showSnackbar(exitString)
        }
    }

    private fun showSnackbar(msg : String) {
        this.snackbarText = msg
        this.snackbar = Snackbar.make(this.binding.root, msg, Snackbar.LENGTH_SHORT)
        this.snackbar?.show()
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
            mainViewModel.searchByCoroutine(mainViewModel.searchedKeyword, failCallback)
        }
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener<Movie> {
        override fun onItemClick(item : Movie) {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                startActivity(browserIntent)
            } catch (e : Exception) {
                showSnackbar(getString(R.string.error_browser))
            }
        }
    }

    /**
     * 검색 기능
     */
    private fun search(keyword : String) {
        this.pagingHelper.setIsEndItem(false)

        this.mainViewModel.clear()
        this.mainViewModel.insertKeyword(keyword, failCallback)
    }

    /**
     * 키보드 숨김
     */
    fun hideKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
            binding.btSearchRecent.id -> {
//                this.recentResultLauncher.launch(Intent(this@MainActivity, RecentActivity::class.java))
            }
        }
    }
}