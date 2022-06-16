package com.sunggil.flowandroidtest.ui.fragment.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunggil.flowandroidtest.base.BaseViewModel
import com.sunggil.flowandroidtest.domain.usecase.GetKeywordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val getKeywordUseCase : GetKeywordsUseCase,
) : BaseViewModel() {

    private var _recentList : MutableLiveData<ArrayList<String>?> = MutableLiveData(null)
    val recentList : LiveData<ArrayList<String>?> = _recentList

    /**
     * 최근 검색 data
     */
    fun setRecentList(list : ArrayList<String>) {
        this._recentList.value = list
    }

    /**
     * db에서 keywods 조회
     */
    fun selectKeywords(keyword : String?) {
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