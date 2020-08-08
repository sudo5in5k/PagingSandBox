package com.example.pagingsandbox.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object QiitaUserService {
    operator fun invoke(): QiitaUserApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val builder = Retrofit.Builder()
            .baseUrl(QiitaUserApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return builder.create(QiitaUserApi::class.java)
    }
}