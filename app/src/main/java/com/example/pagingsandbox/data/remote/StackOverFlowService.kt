package com.example.pagingsandbox.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackOverFlowService {
    operator fun invoke(): StackOverFlowApi {
        val builder = Retrofit.Builder()
            .baseUrl(StackOverFlowApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(StackOverFlowApi::class.java)
    }
}
