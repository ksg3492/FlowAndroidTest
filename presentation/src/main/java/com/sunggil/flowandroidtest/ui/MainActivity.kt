package com.sunggil.flowandroidtest.ui

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
import com.sunggil.flowandroidtest.databinding.ActivityMainBinding
import com.sunggil.flowandroidtest.ui.adapter.MovieRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    @Inject lateinit var adapter : MovieRecyclerAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        binding.btSearch.setOnClickListener(this)
        binding.etSearch.addTextChangedListener(textWatcher)
        binding.etSearch.setOnEditorActionListener(editorActionListener)

        binding.rvMovie.adapter = this.adapter
        binding.rvMovie.layoutManager = LinearLayoutManager(this)

        mainViewModel.movieList.observe(this, Observer {
            it?.let {
                this.adapter.setAll(it)

                Log.e("SG2","movie list size : ${it.size}")
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
                EditorInfo.IME_ACTION_SEND -> {
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
    private val failCallback : ((Int) -> Unit) = {
        Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT).show()
    }


    /**
     * 클릭 리스너
     */
    override fun onClick(v : View?) {
        when (v?.id) {
            binding.btSearch.id -> {
                val input = binding.etSearch.text.toString()

                this.mainViewModel.clear()
                this.mainViewModel.search(input, failCallback)
            }
        }

    }
}