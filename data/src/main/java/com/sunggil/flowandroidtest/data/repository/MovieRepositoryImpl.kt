package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class MovieRepositoryImpl(
    private val favoriteLocalDataSource : FavoriteLocalDataSource,
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

    override suspend fun searchMovieListByCoroutine(keyword : String) : Result<ArrayList<Movie>> {
        return this.movieRemoteDataSource.searchMovieListByCoroutine(keyword)
    }
}