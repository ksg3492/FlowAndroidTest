package com.sunggil.flowandroidtest.domain.usecase

import com.sunggil.flowandroidtest.domain.repository.KeywordRepository
import io.reactivex.rxjava3.core.Maybe

class EditKeywordsUseCase(
    private val keywordRepository : KeywordRepository,
) {
    fun insertKeyword(keyword : String) : Maybe<Long> {
        return this.keywordRepository.insertKeyword(keyword)
    }

    fun deleteKeywords() : Maybe<Int> {
        return this.keywordRepository.deleteKeywords()
    }
}