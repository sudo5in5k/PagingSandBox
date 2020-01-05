package com.example.pagingsandbox.data.remote

import android.content.Context
import android.util.Log
import com.example.pagingsandbox.util.Network
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackOverFlowService {
    operator fun invoke(context: Context, useCache: Boolean = false): StackOverFlowApi {
        val builder = Retrofit.Builder()
            .baseUrl(StackOverFlowApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient(context, useCache))
            .build()
        return builder.create(StackOverFlowApi::class.java)
    }

    private fun getClient(context: Context, useCache: Boolean): OkHttpClient {
        val cacheSize = (5 * 5 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor {
                var request = it.request()
                request = if (!Network.hasNetWork(context) || useCache) {
                    Log.d("debug", "from cache")
                    // only 1 minutes
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=${60 * 60 * 24 * 1}"
                    ).build()
                } else {
                    Log.d("debug", "from network")
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=${10}"
                    ).build()
                }
                it.proceed(request)
            }
            .build()
    }
}
