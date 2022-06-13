package com.sunggil.flowandroidtest.domain.usecase

import com.sunggil.flowandroidtest.domain.repository.KeywordRepository
import io.reactivex.rxjava3.core.Maybe

class GetKeywordsUseCase(
    private val keywordRepository : KeywordRepository,
) {

    fun selectKeywords() : Maybe<ArrayList<String>> {
        return this.keywordRepository.selectKeywords()
    }
}