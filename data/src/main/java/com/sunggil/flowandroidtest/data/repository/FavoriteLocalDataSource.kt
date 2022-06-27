package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.data.database.MovieDataBase
import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

class FavoriteLocalDataSource(
    private val movieDataBase : MovieDataBase,
) {
    fun selectMovie(id : String) : Maybe<Movie?> {
        return this.movieDataBase.getDatabase()?.selectMovie(id) ?: Maybe.just(null)
    }

    fun selectMovies() : Flowable<ArrayList<Movie>> {
        return this.movieDataBase.getDatabase()?.selectMovies() ?: Flowable.just(arrayListOf())
    }

    fun insertMovie(movie : Movie) : Maybe<Long> {
        return this.movieDataBase.getDatabase()?.insertMovie(movie) ?: Maybe.just(-1)
    }

    fun deleteMovie(movie : Movie) : Maybe<Int> {
        return this.movieDataBase.getDatabase()?.deleteMovie(movie) ?: Maybe.just(-1)
    }
}