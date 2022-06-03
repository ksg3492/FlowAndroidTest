package com.sunggil.flowandroidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunggil.flowandroidtest.base.BaseNetworkViewModel
import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.network.repository.ErrorCode
import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.usercase.GetMovieListUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieListUserCase : GetMovieListUserCase
) : BaseNetworkViewModel() {

    private val API_NAME_MOVIE_LIST = "API_NAME_MOVIE_LIST"

    private var _movieList : MutableLiveData<ArrayList<Movie>?> = MutableLiveData(null)
    val movieList : LiveData<ArrayList<Movie>?> = _movieList

    private var _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    /**
     * 마지막 포지션 노출 여부
     */
    var lastAlert : Boolean = false

    /**
     * 영화 검색 data
     */
    fun setMovieList(list : ArrayList<Movie>) {
        this._movieList.value = list
    }

    /**
     * 로딩 화면
     */
    fun setLoading(loading : Boolean) {
        this._loading.value = loading
    }

    /**
     * 검색 데이터 초기화
     */
    fun clear() {
        this.getMovieListUserCase.initPaging()
        this._movieList.value?.clear()
    }

    /**
     * 영화 리스트 검색
     */
    fun search(
        keyword : String,
        failCallback : ((ErrorCode) -> Unit)? = {},
        start : Int = ConstValue.PAGING_DEFAULT_INDEX
    ) {
        cancelObserver(this.API_NAME_MOVIE_LIST)
        addObserver(
            this.API_NAME_MOVIE_LIST,
            this.getMovieListUserCase.searchMovieList(keyword, start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { setLoading(true) }
                .doAfterTerminate { setLoading(false) }
                .subscribeWith(object : DisposableSingleObserver<BaseResult<ArrayList<Movie>, Any>>() {
                    override fun onSuccess(t : BaseResult<ArrayList<Movie>, Any>?) {
                        t?.let {
                            if (it.isSuccess) {
                                val list = it.data!!
                                getMovieListUserCase.checkNextPaging(list)

                                //이전 리스트 뒤에 생성
                                val combineList = _movieList.value ?: arrayListOf()
                                combineList.addAll(list)

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
}