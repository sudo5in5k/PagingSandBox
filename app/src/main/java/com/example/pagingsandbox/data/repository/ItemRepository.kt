package com.example.pagingsandbox.data.repository

import android.content.Context
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingsandbox.data.paging.ItemDataSource
import com.example.pagingsandbox.data.paging.ItemDataSourceFactory
import com.example.pagingsandbox.data.remote.StackOverFlowApi
import com.example.pagingsandbox.data.remote.StackOverFlowService
import kotlinx.coroutines.CoroutineScope

class ItemRepository(private val context: Context, private val scope: CoroutineScope) {

    private val api: StackOverFlowApi by lazy { StackOverFlowService(context) }

    private val itemDataSourceFactory = ItemDataSourceFactory("", api, scope)
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(20)
        .setPageSize(ItemDataSource.PAGE_SIZE).build()

    fun getItems() = LivePagedListBuilder(itemDataSourceFactory, config)
        .build()

    fun getItemsWithFilter(query: String) =
        LivePagedListBuilder(ItemDataSourceFactory(query, api, scope), config).build()
}