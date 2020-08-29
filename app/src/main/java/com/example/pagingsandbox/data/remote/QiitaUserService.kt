package com.example.pagingsandbox.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@ExperimentalSerializationApi
object QiitaUserService {
    operator fun invoke(): QiitaUserApi {
        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }
        val builder = Retrofit.Builder()
            .baseUrl(QiitaUserApi.BASE_URL)
            .addConverterFactory(format.asConverterFactory(contentType))
            .build()
        return builder.create(QiitaUserApi::class.java)
    }
}