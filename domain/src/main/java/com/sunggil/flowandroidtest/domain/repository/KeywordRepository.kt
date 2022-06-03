package com.sunggil.flowandroidtest.domain.repository

import io.reactivex.rxjava3.core.Maybe

interface KeywordRepository {
    fun selectKeywords() : Maybe<ArrayList<String>>

    fun insertKeyword(keyword : String) : Maybe<Long>

    fun deleteKeywords() : Maybe<Int>
}