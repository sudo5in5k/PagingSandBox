package com.example.pagingsandbox.data.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pagingsandbox.BuildConfig
import com.example.pagingsandbox.data.remote.Item
import com.example.pagingsandbox.data.remote.StackOverFlowApiEntity
import com.example.pagingsandbox.data.remote.StackOverFlowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ItemDataSource(val query: String = "") : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        StackOverFlowService().getAnswers(
            FIRST_PAGE,
            PAGE_SIZE,
            SITE_NAME
        )
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
                        callback.onResult(filteredByQuery(query, it.items), null, FIRST_PAGE + 1)
                    }
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        StackOverFlowService().getAnswers(
            params.key,
            PAGE_SIZE,
            SITE_NAME
        )
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
                        callback.onResult(filteredByQuery(query, it.items), key)
                    }
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        StackOverFlowService().getAnswers(
            params.key,
            PAGE_SIZE,
            SITE_NAME
        )
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
                    response.raw().body().use { responseBody ->
                        if (response.isSuccessful) {
                            val key = if (params.key > 1) params.key - 1 else null
                            response.body()?.let {
                                callback.onResult(filteredByQuery(query, it.items), key)
                            }
                        } else {
                            responseBody?.close()
                        }
                    }
                }
            })
    }

    private fun filteredByQuery(query: String, items: List<Item>): List<Item> {
        val filteredItems = if (query.isEmpty() || query.isBlank()) {
            items
        } else {
            items.filter { it.owner.display_name.toLowerCase(Locale.US).contains(query) }
        }
        if (filteredItems.isNullOrEmpty()) {
            Log.d("debug", "no filtered match!")
        }
        Log.d("debug", "filtered!: ${filteredItems.size}")
        return filteredItems
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
        const val SITE_NAME = "stackoverflow"
    }
}