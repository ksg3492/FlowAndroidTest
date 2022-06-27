package com.sunggil.flowandroidtest.ui.fragment.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunggil.flowandroidtest.base.BaseNetworkViewModel
import com.sunggil.flowandroidtest.base.BaseViewModel
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.usecase.EditFavoriteUseCase
import com.sunggil.flowandroidtest.domain.usecase.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase : GetFavoriteUseCase,
    private val editFavoriteUseCase : EditFavoriteUseCase
) : BaseNetworkViewModel() {

    private val flowDisposable : CompositeDisposable = CompositeDisposable()

    private var _favoriteList : MutableLiveData<ArrayList<Movie>?> = MutableLiveData(null)
    val favorites : LiveData<ArrayList<Movie>?> = _favoriteList

    fun setFavoriteList(list : ArrayList<Movie>?) {
        this._favoriteList.value = list
    }

    /**
     * 리스트 조회
     */
    fun getFavoriteList() {
        this.flowDisposable.add(
            this.getFavoriteUseCase.selectMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe {
                Log.e("SG2","getFavoriteList subscribe ${it.size}")
                this.setFavoriteList(it)
                setLoading(false)
            }
        )
    }

    /**
     * 즐겨찾기 삭제
     */
    fun deleteFavorite(
        movie : Movie,
        successCallback : ((Movie) -> Unit)?
    ) {
        this.editFavoriteUseCase.deleteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe {
                if (it > 0) {
                    this._favoriteList.value?.remove(movie)
                    successCallback?.invoke(movie)
                }
            }
    }

    override fun onCleared() {
        super.onCleared()

        //flowable dispose
        this.flowDisposable.clear()
    }
}