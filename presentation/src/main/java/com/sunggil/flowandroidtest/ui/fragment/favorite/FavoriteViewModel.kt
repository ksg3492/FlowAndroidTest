package com.sunggil.flowandroidtest.ui.fragment.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunggil.flowandroidtest.base.BaseViewModel
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.usecase.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase : GetFavoriteUseCase
) : BaseViewModel() {

    private var _favoriteList : MutableLiveData<ArrayList<Movie>?> = MutableLiveData(null)
    val favorites : LiveData<ArrayList<Movie>?> = _favoriteList

    fun setFavoriteList(list : ArrayList<Movie>?) {
        this._favoriteList.value = list
    }

    /**
     * 리스트 조회
     */
    fun getFavoriteList() {
        this.getFavoriteUseCase.selectMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe {
                this.setFavoriteList(it)
            }
    }
}