package com.example.pagingsandbox.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.pagingsandbox.data.remote.Item
import com.example.pagingsandbox.data.remote.StackOverFlowApi
import kotlinx.coroutines.CoroutineScope

class ItemDataSourceFactory(
    private val query: String = "",
    private val api: StackOverFlowApi,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Item>() {

    private val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()

    override fun create(): DataSource<Int, Item> {
        val itemDataSource = ItemDataSource(query, api, scope)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}