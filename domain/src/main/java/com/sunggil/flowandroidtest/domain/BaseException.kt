package com.sunggil.flowandroidtest.domain

class BaseException(
    var errorCode : Any? = null,
) : Throwable()