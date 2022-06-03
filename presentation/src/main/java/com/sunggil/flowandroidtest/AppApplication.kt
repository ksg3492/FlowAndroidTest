package com.sunggil.flowandroidtest

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
@GlideModule
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }


}