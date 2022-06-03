package com.sunggil.flowandroidtest.data.network.api

import com.sunggil.flowandroidtest.data.network.json.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("/v1/search/movie.json")
    fun getMovieList(
        @Query("query") query: String,
        @Query("start") start: Int = 0,
        @Query("display") display: Int = 20,
    ) : Single<MovieResponse>
}