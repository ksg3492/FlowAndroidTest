package com.sunggil.flowandroidtest.domain

data class BaseResult<T, F>(
    var data : T? = null,
    var failCode : F? = null,
) {
    val isSuccess : Boolean = (data != null)
}