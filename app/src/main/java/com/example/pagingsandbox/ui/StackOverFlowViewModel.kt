package com.example.pagingsandbox.ui

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingsandbox.data.paging.ItemDataSource
import com.example.pagingsandbox.data.paging.ItemDataSourceFactory

class StackOverFlowViewModel : ViewModel() {

    private val itemDataSourceFactory =
        ItemDataSourceFactory()
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(20)
        .setPageSize(ItemDataSource.PAGE_SIZE).build()
    val itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
        .build()
}