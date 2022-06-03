package com.sunggil.flowandroidtest.domain.usercase

import com.sunggil.flowandroidtest.domain.repository.KeywordRepository
import io.reactivex.rxjava3.core.Maybe

class GetKeywordsUserCase(
    private val keywordRepository : KeywordRepository,
) {

    fun selectKeywords() : Maybe<ArrayList<String>> {
        return this.keywordRepository.selectKeywords()
    }

    fun insertKeyword(keyword : String) : Maybe<Long> {
        return this.keywordRepository.insertKeyword(keyword)
    }

    fun deleteKeywords() : Maybe<Int> {
        return this.keywordRepository.deleteKeywords()
    }
}