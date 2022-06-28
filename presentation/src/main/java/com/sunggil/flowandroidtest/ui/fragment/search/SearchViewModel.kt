package com.sunggil.flowandroidtest.ui.fragment.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunggil.flowandroidtest.base.BaseViewModel
import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.domain.BaseException
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.usecase.EditKeywordsUseCase
import com.sunggil.flowandroidtest.domain.usecase.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMovieListUseCase : GetMovieListUseCase,
    private val editKeywordsUseCase : EditKeywordsUseCase,
) : BaseViewModel() {

    private val defaultDispatcher : CoroutineDispatcher = Dispatchers.Default
    private val mainDispatcher : CoroutineDispatcher = Dispatchers.Main

    private var _movieList : MutableLiveData<ArrayList<Movie>?> = MutableLiveData(null)
    val movieList : LiveData<ArrayList<Movie>?> = _movieList

    private var _movieListState : MutableState<ArrayList<Movie>?> = mutableStateOf(null)
    val movieListState : State<ArrayList<Movie>?> = _movieListState

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
     * 영화 검색 data (Flow)
     */
    fun setMovieListState(list : ArrayList<Movie>) {
        this._movieListState.value = list
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

        viewModelScope.launch(mainDispatcher) {
            setLoading(true)

            val result = getMovieListUseCase.searchMovieListByCoroutine(keyword)

            if (result.isSuccess) {
                //이전 리스트 뒤에 생성
                val combineList = _movieList.value ?: arrayListOf()
                combineList.addAll(result.getOrNull() ?: arrayListOf())

                //페이징 체크
                getMovieListUseCase.checkNextPaging(combineList)

                setMovieList(combineList)
                setMovieListState(combineList)
            } else {
                result.exceptionOrNull()?.let {
                    val exception = it as BaseException
                    failCallback?.invoke(exception.errorCode as ErrorCode)
                } ?: failCallback?.invoke(ErrorCode.UNKOWN)
            }
            setLoading(false)
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
                Log.e("SG2", "insertKeyword() : $it")
                if (it == -1L) {
                    //db error?
                    failCallback?.invoke(ErrorCode.DB_FAIL)
                } else {
                    this.search(keyword, failCallback)
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