package com.sunggil.flowandroidtest.ui.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunggil.flowandroidtest.base.BaseNetworkViewModel
import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.domain.BaseException
import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.usecase.EditKeywordsUseCase
import com.sunggil.flowandroidtest.domain.usecase.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieListUseCase : GetMovieListUseCase,
    private val editKeywordsUseCase : EditKeywordsUseCase,
) : BaseNetworkViewModel() {

    private val API_NAME_MOVIE_LIST = "API_NAME_MOVIE_LIST"

    private var _movieList : MutableLiveData<ArrayList<Movie>?> = MutableLiveData(null)
    val movieList : LiveData<ArrayList<Movie>?> = _movieList

    /**
     * api 요청시 keyword
     */
    var searchedKeyword : String = ""

    /**
     * 영화 검색 data
     */
    fun setMovieList(list : ArrayList<Movie>) {
        this._movieList.value = list
    }

    /**
     * 검색 데이터 초기화
     */
    fun clear() {
        this.getMovieListUseCase.initPaging()
        this._movieList.value?.clear()
    }

    /**
     * 영화 리스트 검색
     */
    fun search(
        keyword : String,
        failCallback : ((ErrorCode) -> Unit)? = {},
    ) {
        if (keyword.isEmpty()) {
            failCallback?.invoke(ErrorCode.EMPTY_KEYWORD)
            return
        }

        this.searchedKeyword = keyword

        cancelObserver(this.API_NAME_MOVIE_LIST)
        addObserver(
            this.API_NAME_MOVIE_LIST,
            this.getMovieListUseCase.searchMovieList(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { setLoading(true) }
                .doAfterTerminate { setLoading(false) }
                .subscribeWith(object : DisposableSingleObserver<BaseResult<ArrayList<Movie>, Any>>() {
                    override fun onSuccess(t : BaseResult<ArrayList<Movie>, Any>?) {
                        t?.let {
                            if (it.isSuccess) {
                                //이전 리스트 뒤에 생성
                                val combineList = _movieList.value ?: arrayListOf()
                                combineList.addAll(it.data!!)

                                //페이징 체크
                                getMovieListUseCase.checkNextPaging(combineList)

                                setMovieList(combineList)
                            } else {
                                failCallback?.invoke(it.failCode as ErrorCode)
                            }
                        } ?: failCallback?.invoke(ErrorCode.UNKOWN)
                    }

                    override fun onError(e : Throwable?) {
                        failCallback?.invoke(ErrorCode.UNKOWN)
                    }
                })
        )
    }

    /**
     * 영화 리스트 검색
     */
    fun searchByCoroutine(
        keyword : String,
        failCallback : ((ErrorCode) -> Unit)? = {},
    ) {
        if (keyword.isEmpty()) {
            failCallback?.invoke(ErrorCode.EMPTY_KEYWORD)
            return
        }

        this.searchedKeyword = keyword

        viewModelScope.launch(Dispatchers.IO) {
            val result = getMovieListUseCase.searchMovieListByCoroutine(keyword)

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    //이전 리스트 뒤에 생성
                    val combineList = _movieList.value ?: arrayListOf()
                    combineList.addAll(result.getOrNull() ?: arrayListOf())

                    //페이징 체크
                    getMovieListUseCase.checkNextPaging(combineList)

                    setMovieList(combineList)
                } else {
                    result.exceptionOrNull()?.let {
                        val exception = it as BaseException
                        failCallback?.invoke(exception.errorCode as ErrorCode)
                    } ?: failCallback?.invoke(ErrorCode.UNKOWN)
                }
            }
        }
    }

    /**
     * db에 keyword 추가
     */
    fun insertKeyword(
        keyword : String,
        failCallback : ((ErrorCode) -> Unit)? = {},
    ) {
        if (keyword.isEmpty()) {
            failCallback?.invoke(ErrorCode.EMPTY_KEYWORD)
            return
        }

        //먼저 db에 삽입 후 api 통신
        this.editKeywordsUseCase.insertKeyword(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                if (it == -1L) {
                    //db error?
                    failCallback?.invoke(ErrorCode.DB_FAIL)
                } else {
                    this.searchByCoroutine(keyword, failCallback)
                }
            }
    }

    /**
     * db 10개 이상인 경우 삭제
     */
    private fun deleteKeywords() {
        this.editKeywordsUseCase.deleteKeywords()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.e("SG2", "deleteKeywords() : $it")
            }
    }

    override fun onCleared() {
        this.deleteKeywords()
        super.onCleared()
    }
}