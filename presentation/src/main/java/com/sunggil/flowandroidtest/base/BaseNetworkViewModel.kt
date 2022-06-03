package com.sunggil.flowandroidtest.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver

/**
 * 서버 통신 기능을 포함한 객체에 통신 cancel을 명시적으로 하기 위함.
 */
abstract class BaseNetworkViewModel: ViewModel(), NetworkInterface {
    private var networkDisposable = HashMap<String, DisposableSingleObserver<*>>(0)

    /**
     * 옵져버 추가
     */
    override fun addObserver(name:String, observer: DisposableSingleObserver<*>){
        this.networkDisposable[name] = observer
    }

    /**
     * 통신 종료
     */
    override fun cancelObserver(name:String){
        val observer = this.networkDisposable[name]
        observer?.let{
            it.dispose()
            this.networkDisposable.remove(name)
        }
    }

    /**
     * 모든 통신 종료
     */
    override fun cancelNetworkAll(){
        this.networkDisposable.forEach {
            it.value.dispose()
        }
    }

    @CallSuper
    override fun onCleared() {
        this.cancelNetworkAll()
        super.onCleared()
    }
}