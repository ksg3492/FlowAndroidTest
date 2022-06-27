package com.sunggil.flowandroidtest.domain.repository

import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface FavoriteRepository {
    fun selectMovie(id : String) : Maybe<Movie?>

    fun selectMovies() : Flowable<ArrayList<Movie>>

    fun insertMovie(movie : Movie) : Maybe<Long>

    fun deleteMovie(movie : Movie) : Maybe<Int>
}