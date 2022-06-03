package com.sunggil.flowandroidtest.data.network.repository

import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import com.sunggil.flowandroidtest.data.network.json.mapper
import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class MovieRepositoryImpl(
    private val movieApiService : MovieApiService
) : MovieRepository {
    private val paging = PagingSource()

    override fun initPaging() {
        this.paging.init()
    }

    override fun checkNextPage(list : ArrayList<*>) {
        this.paging.checkNextPage(list)
    }

    override fun searchMovieList(
        keyword : String,
        start : Int
    ) : Single<BaseResult<ArrayList<Movie>, Any>> {
        //todo 최초검색어와 다음페이징의 검색어가 다를경우?
        if (keyword.isEmpty()) {
            return Single.just(BaseResult(null, ErrorCode.EMPTY_KEYWORD))
        }
        if (paging.isEndPage()) {
            return Single.just(BaseResult(null, ErrorCode.LAST_PAGE))
        }

        return this.movieApiService.getMovieList(keyword, start).map { BaseResult(it.mapper()) }
    }
}