package com.sunggil.flowandroidtest.domain.repository

import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun initPaging()

    fun checkNextPage(list : ArrayList<*>)

    fun searchMovieList(keyword : String) : Single<BaseResult<ArrayList<Movie>, Any>>

    suspend fun searchMovieListByCoroutine(keyword : String) : Result<ArrayList<Movie>>
}