package com.example.pagingsandbox.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @see
 */
interface StackOverFlowApi {
    @GET("answers")
    fun getAnswers(@Query("page") page: Int, @Query("pagesize") pagesize: Int, @Query("site") site: String): Call<StackOverFlowApiEntity>

    companion object {
        const val BASE_URL = "https://api.stackexchange.com/2.2/"
    }
}