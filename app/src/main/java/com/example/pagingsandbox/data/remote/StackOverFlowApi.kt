package com.example.pagingsandbox.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @see [docs](https://api.stackexchange.com/docs)
 */
interface StackOverFlowApi {
    @GET("answers")
    suspend fun getAnswers(@Query("page") page: Int, @Query("pagesize") pagesize: Int, @Query("site") site: String): Response<StackOverFlowApiEntity>

    companion object {
        const val BASE_URL = "https://api.stackexchange.com/2.2/"
    }
}