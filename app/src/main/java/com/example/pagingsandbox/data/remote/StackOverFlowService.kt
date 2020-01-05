package com.example.pagingsandbox.data.remote

import android.content.Context
import com.example.pagingsandbox.util.Network
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackOverFlowService {
    operator fun invoke(context: Context): StackOverFlowApi {
        val builder = Retrofit.Builder()
            .baseUrl(StackOverFlowApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient(context))
            .build()
        return builder.create(StackOverFlowApi::class.java)
    }

    private fun getClient(context: Context): OkHttpClient {
        val cacheSize = (5 * 5 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor {
                var request = it.request()
                request = if (Network.hasNetWork(context)) {
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=${5}"
                    ).build()
                } else {
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}"
                    ).build()
                }
                it.proceed(request)
            }
            .build()
    }
}
