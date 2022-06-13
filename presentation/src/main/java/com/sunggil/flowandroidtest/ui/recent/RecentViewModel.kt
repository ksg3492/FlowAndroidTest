package com.sunggil.flowandroidtest.ui.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunggil.flowandroidtest.domain.usecase.GetKeywordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val getKeywordUseCase : GetKeywordsUseCase,
) : ViewModel() {

    private var _recentList : MutableLiveData<ArrayList<String>?> = MutableLiveData(null)
    val recentList : LiveData<ArrayList<String>?> = _recentList

    private var _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    /**
     * 최근 검색 data
     */
    fun setRecentList(list : ArrayList<String>) {
        this._recentList.value = list
    }

    /**
     * 로딩 화면
     */
    fun setLoading(loading : Boolean) {
        this._loading.postValue(loading)
    }

    /**
     * db에서 keywods 조회
     */
    fun selectKeywords() {
        this.getKeywordUseCase.selectKeywords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe {
                setRecentList(it)
            }
    }
}