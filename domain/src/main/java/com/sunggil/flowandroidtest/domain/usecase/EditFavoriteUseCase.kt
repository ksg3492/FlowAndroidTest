package com.sunggil.flowandroidtest.domain.usecase

import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.FavoriteRepository
import io.reactivex.rxjava3.core.Maybe

class EditFavoriteUseCase(
    private val favoriteRepository : FavoriteRepository,
) {
    fun insertMovie(movie : Movie) : Maybe<Long> {
        return this.favoriteRepository.insertMovie(movie)
    }

    fun deleteMovie(movie : Movie) : Maybe<Int> {
        return this.favoriteRepository.deleteMovie(movie)
    }
}