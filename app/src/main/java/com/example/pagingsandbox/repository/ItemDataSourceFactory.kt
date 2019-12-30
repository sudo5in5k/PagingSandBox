package com.example.pagingsandbox.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.pagingsandbox.repository.remote.Item

class ItemDataSourceFactory : DataSource.Factory<Int, Item>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()

    override fun create(): DataSource<Int, Item> {
        val itemDataSource = ItemDataSource()
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}