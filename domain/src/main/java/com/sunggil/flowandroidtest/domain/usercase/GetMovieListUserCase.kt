package com.sunggil.flowandroidtest.domain.usercase

import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMovieListUserCase(
    private val movieRepository : MovieRepository
) {
    fun getMovieList(keyword : String, start : Int) : Single<ArrayList<Movie>> {
        return this.movieRepository.getMovieList(keyword, start)
    }
}