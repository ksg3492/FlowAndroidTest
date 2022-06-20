package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.FavoriteRepository
import io.reactivex.rxjava3.core.Maybe

class FavoriteRepositoryImpl(
    private val favoriteLocalDataSource : FavoriteLocalDataSource,
) : FavoriteRepository {
    override fun selectMovie(id : String) : Maybe<Movie?> {
        return this.favoriteLocalDataSource.selectMovie(id)
    }

    override fun selectMovies() : Maybe<ArrayList<Movie>> {
        return this.favoriteLocalDataSource.selectMovies()
    }

    override fun insertMovie(movie : Movie) : Maybe<Long> {
        return this.favoriteLocalDataSource.insertMovie(movie)
    }

    override fun deleteMovie(movie : Movie) : Maybe<Int> {
        return this.favoriteLocalDataSource.deleteMovie(movie)
    }

}