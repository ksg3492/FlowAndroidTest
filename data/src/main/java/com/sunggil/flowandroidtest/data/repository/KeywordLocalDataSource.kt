package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.data.database.MovieDataBase
import io.reactivex.rxjava3.core.Maybe

class KeywordLocalDataSource(
    private val movieDataBase : MovieDataBase,
) {
    fun selectKeywords() : Maybe<ArrayList<String>> {
        return this.movieDataBase.getDatabase()?.selectKeywords() ?: Maybe.just(arrayListOf())
    }

    fun insertKeyword(keyword : String) : Maybe<Long> {
        return this.movieDataBase.getDatabase()?.insertKeyword(keyword) ?: Maybe.just(-1)
    }

    fun deleteKeywords() : Maybe<Int> {
        return this.movieDataBase.getDatabase()?.deleteKeywords() ?: Maybe.just(-1)
    }
}