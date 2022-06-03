package com.sunggil.flowandroidtest.data.network

enum class ErrorCode(value : Int) {
    UNKOWN(0),
    EMPTY_KEYWORD(1),
    LAST_PAGE(2),
    DB_FAIL(3),
}