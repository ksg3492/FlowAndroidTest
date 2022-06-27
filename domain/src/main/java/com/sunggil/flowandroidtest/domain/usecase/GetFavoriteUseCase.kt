package com.sunggil.flowandroidtest.domain.usecase

import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.FavoriteRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

class GetFavoriteUseCase(
    private val favoriteRepository : FavoriteRepository,
) {
    fun selectMovie(id : String) : Maybe<Movie?> {
        return this.favoriteRepository.selectMovie(id)
    }

    fun selectMovies() : Flowable<ArrayList<Movie>> {
        return this.favoriteRepository.selectMovies()
    }
}