package com.example.pagingsandbox.data.remote

import com.example.pagingsandbox.data.entity.QiitaUserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaUserApi {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<List<QiitaUserEntity>>

    companion object {
        const val BASE_URL = "https://qiita.com/api/v2/"
    }
}