package com.sunggil.flowandroidtest.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected var _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    /**
     * 로딩 화면
     */
    fun setLoading(loading : Boolean) {
        this._loading.postValue(loading)
    }

    override fun onCleared() {
        Log.e("SG2", "${this.javaClass.name} onCleared()")
        super.onCleared()
    }
}