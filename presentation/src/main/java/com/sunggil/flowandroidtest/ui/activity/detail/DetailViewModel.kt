package com.sunggil.flowandroidtest.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunggil.flowandroidtest.base.BaseViewModel
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.usecase.EditFavoriteUseCase
import com.sunggil.flowandroidtest.domain.usecase.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getFavoriteUseCase : GetFavoriteUseCase,
    private val editFavoriteUseCase : EditFavoriteUseCase
) : BaseViewModel() {

    private var _movie : MutableLiveData<Movie?> = MutableLiveData(null)
    var movie : LiveData<Movie?> = _movie

    private var _isFavorite : MutableLiveData<Boolean?> = MutableLiveData(false)
    var isFavorite : LiveData<Boolean?> = _isFavorite

    fun setMovie(movie : Movie) {
        this._movie.value = movie
    }

    fun setIsFavorite(favorite : Boolean) {
        this._isFavorite.value = favorite
    }

    /**
     * 즐겨찾기 토글
     */
    fun toggleFavorite(
        successCallback : (Boolean) -> Unit
    ) {
        val toggle = this._isFavorite.value == true

        if (toggle) {
            this.editFavoriteUseCase.deleteMovie(this._movie.value!!)
        } else {
            this.editFavoriteUseCase.insertMovie(this._movie.value!!)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { this.setLoading(true) }
            .doAfterTerminate { this.setLoading(false) }
            .subscribe {
                if (it.toInt() > 0) {
                    this.setIsFavorite(!toggle)
                    successCallback.invoke(!toggle)
                }
            }
    }

    /**
     * 즐겨찾기 확인
     */
    fun checkFavorite() {
        val id = this._movie.value?.id ?: null

        id?.let {
            this.getFavoriteUseCase.selectMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { this.setLoading(true) }
                .doAfterTerminate { this.setLoading(false) }
                .subscribe {
                    this.setIsFavorite(it != null)
                }
        }
    }
}