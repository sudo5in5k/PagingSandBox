package com.example.pagingsandbox.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

object Network {
    fun hasNetWork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
                ?: return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val an = connectivityManager.activeNetwork ?: return false
            val nc = connectivityManager.getNetworkCapabilities(an) ?: return false
            return when {
                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            try {
                val info = connectivityManager.activeNetworkInfo ?: return false
                return info.isConnected
            } catch (e: Exception) {
                Log.d("debug", "NetWork Error: ${e.message}")
                return false
            }
        }
    }
}