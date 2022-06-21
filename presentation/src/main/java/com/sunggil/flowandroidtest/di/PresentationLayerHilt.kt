package com.sunggil.flowandroidtest.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.data.database.MovieDataBase
import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import com.sunggil.flowandroidtest.data.repository.*
import com.sunggil.flowandroidtest.domain.repository.FavoriteRepository
import com.sunggil.flowandroidtest.domain.repository.KeywordRepository
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import com.sunggil.flowandroidtest.domain.usecase.*
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
    fun providesGetMovieListUseCase(repository : MovieRepository) : GetMovieListUseCase {
        return GetMovieListUseCase(repository)
    }

    @Provides
    fun providesGetKeywordsUseCase(repository : KeywordRepository) : GetKeywordsUseCase {
        return GetKeywordsUseCase(repository)
    }

    @Provides
    fun providesEditKeywordsUseCase(repository : KeywordRepository) : EditKeywordsUseCase {
        return EditKeywordsUseCase(repository)
    }

    @Provides
    fun providesGetFavoriteUseCase(repository : FavoriteRepository) : GetFavoriteUseCase {
        return GetFavoriteUseCase(repository)
    }

    @Provides
    fun providesEditFavoriteUseCase(repository : FavoriteRepository) : EditFavoriteUseCase {
        return EditFavoriteUseCase(repository)
    }

    @Provides
    fun providesMovieRepository(dbService : MovieDataBase, apiService : MovieApiService) : MovieRepository {
        return MovieRepositoryImpl(FavoriteLocalDataSource(dbService), MovieRemoteDataSource(apiService))
    }

    @Provides
    fun providesKeywordRepository(dbService : MovieDataBase) : KeywordRepository {
        return KeywordRepositoryImpl(KeywordLocalDataSource(dbService))
    }

    @Provides
    fun providesFavoriteRepository(dbService : MovieDataBase) : FavoriteRepository {
        return FavoriteRepositoryImpl(FavoriteLocalDataSource(dbService))
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

