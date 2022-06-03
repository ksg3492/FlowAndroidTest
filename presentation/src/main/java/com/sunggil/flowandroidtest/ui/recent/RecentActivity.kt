package com.sunggil.flowandroidtest.ui.recent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ActivityRecentBinding
import com.sunggil.flowandroidtest.ui.adapter.RecentRecyclerAdapter
import com.sunggil.flowandroidtest.ui.base.ActivityValue
import com.sunggil.flowandroidtest.ui.base.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecentBinding
    private val recentViewModel : RecentViewModel by viewModels()

    @Inject
    lateinit var adapter : RecentRecyclerAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_recent)
        this.binding.viewModel = this.recentViewModel
        this.binding.lifecycleOwner = this

        this.binding.rvRecent.adapter = this.adapter
        this.binding.rvRecent.layoutManager = LinearLayoutManager(this)
        this.adapter.setOnItemClickListener(this.onItemClickListener)

        this.recentViewModel.recentList.observe(this, Observer {
            it?.let {
                this.adapter.setAll(it)
            }
        })

        //db 에서 조회
        this.recentViewModel.selectKeywords()
    }

    /**
     * 어댑터 아이템 클릭 리스너
     */
    private val onItemClickListener = object : OnItemClickListener<String> {
        override fun onItemClick(item : String) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(ActivityValue.Extra.KEYWORD, item)
            })

            finish()
        }
    }
}