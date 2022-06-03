package com.sunggil.flowandroidtest.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

/**
 * 페이징 처리 리스너
 */
class PagingHelper @Inject constructor() {
    private var isLoading : Boolean = false
    private var isEndItem : Boolean = false
    private val VISIBLE_THRESHOLD : Int = 1
    private var callback : OnLoadMoreDataCallback? = null

    fun setCallback (callback : OnLoadMoreDataCallback) {
        this.callback = callback
    }

    fun setIsLoading(loading : Boolean) {
        this.isLoading = loading
    }

    fun setIsEndItem(endItem : Boolean) {
        this.isEndItem = endItem
    }

    fun getScrollListener() = scrollListener

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (recyclerView.computeVerticalScrollOffset() == 0) return
            recyclerView.layoutManager?.let { layoutManager ->
                if (layoutManager is LinearLayoutManager) {
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!isLoading && totalItemCount != 0 && !isEndItem
                        && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD
                    ) {
                        isLoading = true
                        callback?.onLoadMoreData()
                    }
                }
            }
        }
    }

    interface OnLoadMoreDataCallback {
        fun onLoadMoreData()
    }
}