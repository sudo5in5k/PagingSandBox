package com.example.pagingsandbox.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pagingsandbox.BuildConfig
import com.example.pagingsandbox.repository.remote.Item
import com.example.pagingsandbox.repository.remote.StackOverFlowApiEntity
import com.example.pagingsandbox.repository.remote.StackOverFlowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDataSource : PageKeyedDataSource<Int, Item>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        StackOverFlowService().getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackOverFlowApiEntity> {
                override fun onFailure(call: Call<StackOverFlowApiEntity>, t: Throwable) {
                    if (BuildConfig.DEBUG) {
                        Log.d("debug", t.toString())
                    }
                }

                override fun onResponse(
                    call: Call<StackOverFlowApiEntity>,
                    response: Response<StackOverFlowApiEntity>
                ) {
                    response.body()?.let {
                        callback.onResult(it.items, null, FIRST_PAGE + 1)
                    }
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        StackOverFlowService().getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackOverFlowApiEntity> {
                override fun onFailure(call: Call<StackOverFlowApiEntity>, t: Throwable) {
                    if (BuildConfig.DEBUG) {
                        Log.d("debug", t.toString())
                    }
                }

                override fun onResponse(
                    call: Call<StackOverFlowApiEntity>,
                    response: Response<StackOverFlowApiEntity>
                ) {
                    val body = response.body()
                    val key = if (body?.has_more == true) params.key + 1 else null
                    body?.let {
                        callback.onResult(it.items, key)
                    }
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        StackOverFlowService().getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackOverFlowApiEntity> {
                override fun onFailure(call: Call<StackOverFlowApiEntity>, t: Throwable) {
                    if (BuildConfig.DEBUG) {
                        Log.d("debug", t.toString())
                    }
                }

                override fun onResponse(
                    call: Call<StackOverFlowApiEntity>,
                    response: Response<StackOverFlowApiEntity>
                ) {
                    val body = response.body()
                    val key = if (params.key > 1) params.key - 1 else null
                    body?.let {
                        callback.onResult(it.items, key)
                    }
                }
            })
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 50
        const val SITE_NAME = "stackoverflow"
    }
}