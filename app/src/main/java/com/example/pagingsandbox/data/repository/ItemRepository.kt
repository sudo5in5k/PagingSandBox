package com.example.pagingsandbox.data.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingsandbox.data.paging.ItemDataSource
import com.example.pagingsandbox.data.paging.ItemDataSourceFactory

class ItemRepository {

    private val itemDataSourceFactory = ItemDataSourceFactory()
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(20)
        .setPageSize(ItemDataSource.PAGE_SIZE).build()

    fun getItems() = LivePagedListBuilder(itemDataSourceFactory, config)
        .build()

    fun getItemsWithFilter(query: String) =
        LivePagedListBuilder(ItemDataSourceFactory(query), config).build()
}