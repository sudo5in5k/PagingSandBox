package com.example.pagingsandbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pagingsandbox.data.paging.ItemDataSource

class QiitaUserRepository(private val source: ItemDataSource) {

    fun fetchDataFlow() = Pager(PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)) {
        source
    }.flow

    companion object {
        private const val PAGE_SIZE = 20
    }
}