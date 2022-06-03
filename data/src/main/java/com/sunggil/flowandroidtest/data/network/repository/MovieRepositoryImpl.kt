package com.sunggil.flowandroidtest.data.network.repository

import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import com.sunggil.flowandroidtest.data.network.json.mapper
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class MovieRepositoryImpl(
    private val movieApiService : MovieApiService
) : MovieRepository {
    override fun getMovieList(keyword : String, start : Int) : Single<ArrayList<Movie>> {
        return this.movieApiService.getMovieList(keyword, start).map { it.mapper() }
    }
}