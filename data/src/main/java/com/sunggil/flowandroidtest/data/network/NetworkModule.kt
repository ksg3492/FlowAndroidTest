package com.sunggil.flowandroidtest.data.network

import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.Name
import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor(ResponseInterceptor())
            .connectTimeout(5L, TimeUnit.SECONDS)
            .callTimeout(5L, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstValue.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    /**
     * log용 interceptor
     */
    fun getLoggingInterceptor() : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    /**
     * auth token header interceptor
     */
    class HeaderInterceptor() : Interceptor {
        override fun intercept(chain : Interceptor.Chain) : Response {
            var newRequestWithToken = chain.request().newBuilder()
            newRequestWithToken = getHeader(newRequestWithToken)
            return chain.proceed(newRequestWithToken.build())
        }
    }

    /**
     * response encoding
     */
    class ResponseInterceptor() : Interceptor {
        override fun intercept(chain : Interceptor.Chain) : Response {
            val response = chain.proceed(chain.request())
            val modified = response.newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()

            return modified
        }
    }

    /**
     * 헤더 생성
     */
    fun getHeader(requestBuilder : Request.Builder) : Request.Builder {
        requestBuilder
            .header(ConstValue.HEADER_ID, Name.id())
            .header(ConstValue.HEADER_SECRET, Name.key())
        return requestBuilder
    }

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit : Retrofit) : MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}