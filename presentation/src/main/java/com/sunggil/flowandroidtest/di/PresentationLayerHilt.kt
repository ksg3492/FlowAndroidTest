package com.sunggil.flowandroidtest.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import com.sunggil.flowandroidtest.data.network.repository.MovieRepositoryImpl
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import com.sunggil.flowandroidtest.domain.usercase.GetMovieListUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationLayerHilt {

    @Provides
    fun providesGetMovieListUserCase(repository : MovieRepository) : GetMovieListUserCase {
        return GetMovieListUserCase(repository)
    }

    @Provides
    fun providesMovieRepository(apiService : MovieApiService) : MovieRepository {
        return MovieRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit : Retrofit) : MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}

@Module
@InstallIn(ActivityComponent::class)
object GlideHilt {
    @Provides
    fun providesRequestManager(@ActivityContext context : Context) : RequestManager {
        return Glide.with(context)
    }
}