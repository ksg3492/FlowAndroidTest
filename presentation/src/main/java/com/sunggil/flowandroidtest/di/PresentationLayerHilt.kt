package com.sunggil.flowandroidtest.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.data.database.MovieDataBase
import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import com.sunggil.flowandroidtest.data.repository.KeywordRepositoryImpl
import com.sunggil.flowandroidtest.data.repository.KeywordLocalDataSource
import com.sunggil.flowandroidtest.data.repository.MovieRemoteDataSource
import com.sunggil.flowandroidtest.data.repository.MovieRepositoryImpl
import com.sunggil.flowandroidtest.domain.repository.KeywordRepository
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import com.sunggil.flowandroidtest.domain.usercase.GetKeywordsUserCase
import com.sunggil.flowandroidtest.domain.usercase.GetMovieListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PresentationLayerHilt {

    @Provides
    fun providesGetMovieListUserCase(repository : MovieRepository) : GetMovieListUseCase {
        return GetMovieListUseCase(repository)
    }

    @Provides
    fun providesGetKeywordsUserCase(repository : KeywordRepository) : GetKeywordsUserCase {
        return GetKeywordsUserCase(repository)
    }

    @Provides
    fun providesMovieRepository(apiService : MovieApiService) : MovieRepository {
        return MovieRepositoryImpl(MovieRemoteDataSource(apiService))
    }

    @Provides
    fun providesKeywordRepository(dbService : MovieDataBase) : KeywordRepository {
        return KeywordRepositoryImpl(KeywordLocalDataSource(dbService))
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

