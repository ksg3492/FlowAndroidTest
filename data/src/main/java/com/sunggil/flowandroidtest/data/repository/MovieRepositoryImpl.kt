package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class MovieRepositoryImpl(
    private val movieLocalDataSource : MovieLocalDataSource,
    private val movieRemoteDataSource : MovieRemoteDataSource,
) : MovieRepository {

    override fun initPaging() {
        this.movieRemoteDataSource.initPaging()
    }

    override fun checkNextPage(list : ArrayList<*>) {
        this.movieRemoteDataSource.checkNextPage(list)
    }

    override fun searchMovieList(keyword : String) : Single<BaseResult<ArrayList<Movie>, Any>> {
        return this.movieRemoteDataSource.searchMovieList(keyword)
    }

    override fun selectKeywords() : Maybe<ArrayList<String>> {
        return this.movieLocalDataSource.selectKeywords()
    }

    override fun insertKeyword(keyword : String) : Maybe<Long> {
        return this.movieLocalDataSource.insertKeyword(keyword)
    }

    override fun deleteKeywords() : Maybe<Int> {
        return this.movieLocalDataSource.deleteKeywords()
    }
}