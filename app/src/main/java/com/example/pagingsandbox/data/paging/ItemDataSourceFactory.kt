package com.example.pagingsandbox.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.pagingsandbox.data.remote.Item

class ItemDataSourceFactory(private val query: String = "") : DataSource.Factory<Int, Item>() {

    private val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()

    override fun create(): DataSource<Int, Item> {
        val itemDataSource = ItemDataSource(query)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}