package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.domain.repository.KeywordRepository
import io.reactivex.rxjava3.core.Maybe

class KeywordRepositoryImpl(
    private val keywordLocalDataSource : KeywordLocalDataSource,
) : KeywordRepository {

    override fun selectKeywords() : Maybe<ArrayList<String>> {
        return this.keywordLocalDataSource.selectKeywords()
    }

    override fun insertKeyword(keyword : String) : Maybe<Long> {
        return this.keywordLocalDataSource.insertKeyword(keyword)
    }

    override fun deleteKeywords() : Maybe<Int> {
        return this.keywordLocalDataSource.deleteKeywords()
    }
}