package com.sunggil.flowandroidtest.data.repository

import com.sunggil.flowandroidtest.data.ConstValue

/**
 * paging 형태 리스트 구현에 사용할 페이지 정보 data set
 */
class PagingSource(val defaultPageSize : Int = ConstValue.PAGING_DEFAULT_SIZE) {
    private var isEndPage = false
    private var pageIndex = ConstValue.PAGING_DEFAULT_INDEX
    private var total : Int = -1

    fun isEndPage() = isEndPage
    fun getPageIndex() = pageIndex

    fun init() {
        isEndPage = false
        pageIndex = ConstValue.PAGING_DEFAULT_INDEX
        total = -1
    }

    fun setTotal(total : Int) {
        this.total = total
    }

    /**
     * 다음 페이지 체크
     */
    fun checkNextPage(list : ArrayList<*>) {
        if (list.size >= total) {
            //페이징 끝
            isEndPage = true
        } else {
            //다음 페이지 존재
            if (pageIndex + ConstValue.PAGING_DEFAULT_SIZE >= total) {
                //요청 하는 사이즈가 전체 사이즈를 넘어가면?
                pageIndex += (total - pageIndex)
            } else {
                pageIndex += ConstValue.PAGING_DEFAULT_SIZE
            }
        }
    }
}