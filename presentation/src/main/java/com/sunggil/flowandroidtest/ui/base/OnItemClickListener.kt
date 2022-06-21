package com.sunggil.flowandroidtest.ui.base

interface OnItemClickListener<T> {
    fun onItemClick(type : ClickType, item : T)
}

enum class ClickType(value : Int) {
    ROOT(0),
    DELETE(1),
}