package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.data.network.api.MovieApiService
import com.sunggil.flowandroidtest.data.network.json.mapper
import com.sunggil.flowandroidtest.domain.BaseResult
import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Single

class MovieRemoteDataSource(
    private val movieApiService : MovieApiService
) {

    private val paging = PagingSource()

    fun initPaging() {
        this.paging.init()
    }

    fun checkNextPage(list : ArrayList<*>) {
        this.paging.checkNextPage(list)
    }

    fun searchMovieList(keyword : String) : Single<BaseResult<ArrayList<Movie>, Any>> {
        if (keyword.isEmpty()) {
            return Single.just(BaseResult(null, ErrorCode.EMPTY_KEYWORD))
        }
        if (paging.isEndPage()) {
            return Single.just(BaseResult(null, ErrorCode.LAST_PAGE))
        }

        return this.movieApiService.getMovieList(keyword, paging.getPageIndex()).map {
            //최대 갯수 설정
            this.paging.setTotal(it.total)
            BaseResult(it.mapper())
        }
    }

}