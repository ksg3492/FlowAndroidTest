package com.sunggil.flowandroidtest.base

import io.reactivex.rxjava3.observers.DisposableSingleObserver

interface NetworkInterface {
    fun addObserver(name:String, observer: DisposableSingleObserver<*>)

    fun cancelObserver(name:String)

    fun cancelNetworkAll()
}