package com.sunggil.flowandroidtest.domain.repository

import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun initPaging()

    fun checkNextPage(list : ArrayList<*>)

    fun searchMovieList(keyword : String) : Single<BaseResult<ArrayList<Movie>, Any>>

    fun selectKeywords() : Maybe<ArrayList<String>>

    fun insertKeyword(keyword : String) : Maybe<Long>
}