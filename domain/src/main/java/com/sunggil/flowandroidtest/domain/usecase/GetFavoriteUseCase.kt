package com.sunggil.flowandroidtest.domain.usecase

import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.FavoriteRepository
import io.reactivex.rxjava3.core.Maybe

class GetFavoriteUseCase(
    private val favoriteRepository : FavoriteRepository,
) {
    fun selectMovie(id : String) : Maybe<Movie?> {
        return this.favoriteRepository.selectMovie(id)
    }

    fun selectMovies() : Maybe<ArrayList<Movie>> {
        return this.favoriteRepository.selectMovies()
    }

    fun insertMovie(movie : Movie) : Maybe<Long> {
        return this.favoriteRepository.insertMovie(movie)
    }

    fun deleteMovie(movie : Movie) : Maybe<Int> {
        return this.favoriteRepository.deleteMovie(movie)
    }
}