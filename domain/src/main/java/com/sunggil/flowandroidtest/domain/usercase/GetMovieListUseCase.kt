package com.sunggil.flowandroidtest.domain.usercase

import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMovieListUseCase(
    private val movieRepository : MovieRepository,
) {
    fun initPaging() {
        this.movieRepository.initPaging()
    }

    fun checkNextPaging(list : ArrayList<Movie>) {
        this.movieRepository.checkNextPage(list)
    }

    fun searchMovieList(keyword : String) : Single<BaseResult<ArrayList<Movie>, Any>> {
        return this.movieRepository.searchMovieList(keyword)
    }
}