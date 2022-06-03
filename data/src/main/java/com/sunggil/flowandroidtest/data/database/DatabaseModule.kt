package com.sunggil.flowandroidtest.data.database

import android.content.Context
import com.sunggil.flowandroidtest.data.database.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context : Context) : MovieDataBase {
        return MovieDataBase(context)
    }
}