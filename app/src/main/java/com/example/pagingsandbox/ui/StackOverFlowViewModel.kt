package com.example.pagingsandbox.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.pagingsandbox.data.paging.ItemDataSource
import com.example.pagingsandbox.data.paging.ItemDataSourceFactory
import com.example.pagingsandbox.data.remote.Item

class StackOverFlowViewModel : ViewModel() {

    private val itemDataSourceFactory =
        ItemDataSourceFactory()
    var itemPagedList: LiveData<PagedList<Item>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>? = null

    init {
        liveDataSource = itemDataSourceFactory.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ItemDataSource.PAGE_SIZE).build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
            .build()
    }
}