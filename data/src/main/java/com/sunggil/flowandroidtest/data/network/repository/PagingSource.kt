package com.sunggil.flowandroidtest.data.network.repository

import com.sunggil.flowandroidtest.data.ConstValue

/**
 * paging 형태 리스트 구현에 사용할 페이지 정보 data set
 */
class PagingSource(val defaultPageSize : Int = ConstValue.PAGING_DEFAULT_SIZE) {
    private var isEndPage = false
    private var pageIndex = 0

    fun isEndPage() = isEndPage
    fun getPageIndex() = pageIndex

    fun init() {
        isEndPage = false
        pageIndex = 0
    }

    /**
     * 다음 페이지 체크
     */
    fun checkNextPage(list : ArrayList<*>) {
        when {
            list.isEmpty() || list.size < defaultPageSize -> {
                //페이징 끝
                isEndPage = true
            }
            list.size >= defaultPageSize -> {
                //다음 페이지 존재
                pageIndex++
            }
        }
    }
}