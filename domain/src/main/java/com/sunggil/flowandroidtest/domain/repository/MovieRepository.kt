package com.sunggil.flowandroidtest.domain.repository

import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun getMovieList(keyword : String, start : Int) : Single<ArrayList<Movie>>
}