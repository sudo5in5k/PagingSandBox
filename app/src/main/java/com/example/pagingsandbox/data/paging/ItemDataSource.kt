package com.example.pagingsandbox.data.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pagingsandbox.data.remote.Item
import com.example.pagingsandbox.data.remote.StackOverFlowApi
import com.example.pagingsandbox.data.remote.StackOverFlowApiEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class ItemDataSource(
    private val query: String = "",
    private val api: StackOverFlowApi,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        scope.launch {
            getAnswer(FIRST_PAGE) {
                callback.onResult(filteredByQuery(query, it.items), null, FIRST_PAGE + 1)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        scope.launch {
            getAnswer(params.key) {
                val key = if (it.has_more) params.key + 1 else null
                callback.onResult(filteredByQuery(query, it.items), key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        scope.launch {
            getAnswer(params.key) {
                val key = if (params.key > 1) params.key - 1 else null
                callback.onResult(filteredByQuery(query, it.items), key)
            }
        }
    }

    private fun filteredByQuery(query: String, items: List<Item>) =
        if (query.isEmpty() || query.isBlank()) {
            items
        } else {
            items.filter { it.owner.display_name.toLowerCase(Locale.US).contains(query) }
        }

    /**
     * @see [ErrorCode](https://api.stackexchange.com/docs/error-handling)
     */
    private suspend fun getAnswer(page: Int, callback: (entity: StackOverFlowApiEntity) -> Unit) {
        try {
            val response = api.getAnswers(page, PAGE_SIZE, SITE_NAME)
            if (response.raw().cacheResponse() != null) {
                Log.d("debug", "cache remain")
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    callback(it)
                }
            } else {
                when (response.code()) {
                    400 -> Log.d("debug", "Client Error! Request to: ${response.raw()}")
                    502 -> Log.d("debug", "Server Error for Many Access")
                    else -> Log.d("debug", "Error! Error code: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            Log.d("debug", "Response Exception: ${e.message}")
        }
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
        const val SITE_NAME = "stackoverflow"
    }
}