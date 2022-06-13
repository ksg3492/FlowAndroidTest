package com.sunggil.flowandroidtest.data.network.api

import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.network.json.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("/v1/search/movie.json")
    fun getMovieList(
        @Query("query") query: String,
        @Query("start") start: Int = ConstValue.PAGING_DEFAULT_INDEX,
        @Query("display") display: Int = ConstValue.PAGING_DEFAULT_SIZE,
    ) : Single<MovieResponse>

    @GET("/v1/search/movie.json")
    suspend fun getMovieListByCoroutine(
        @Query("query") query: String,
        @Query("start") start: Int = ConstValue.PAGING_DEFAULT_INDEX,
        @Query("display") display: Int = ConstValue.PAGING_DEFAULT_SIZE,
    ) : MovieResponse
}