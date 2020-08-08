package com.example.pagingsandbox.data.paging

import androidx.paging.PagingSource
import com.example.pagingsandbox.data.entity.QiitaUserEntity
import com.example.pagingsandbox.data.remote.QiitaUserApi

class ItemDataSource(private val api: QiitaUserApi) : PagingSource<Int, QiitaUserEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QiitaUserEntity> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE
            val response = api.getUsers(nextPage)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body() ?: emptyList(),
                    prevKey = null,
                    nextKey = nextPage + 1
                )
            } else {
                LoadResult.Error(Throwable("Response is not success. Error code: ${response.code()}, Error message: ${response.errorBody()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}