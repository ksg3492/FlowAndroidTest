package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.data.database.MovieDataBase
import io.reactivex.rxjava3.core.Flowable

class MovieLocalDataSource(
    private val movieDataBase : MovieDataBase
) {
    init {
        this.movieDataBase.openDataBaseFile()
    }

    fun selectKeywords() : Flowable<ArrayList<String>> {
        return this.movieDataBase.getDatabase()?.selectKeywords() ?: Flowable.just(arrayListOf())
    }

    fun insertKeyword(keyword : String) {
        this.movieDataBase.getDatabase()?.insertKeyword(keyword)
    }
}