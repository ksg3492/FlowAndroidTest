package com.sunggil.flowandroidtest.ui.base

import com.sunggil.flowandroidtest.domain.Movie

interface OnItemClickListener<T> {
    fun onItemClick(item : T)
}