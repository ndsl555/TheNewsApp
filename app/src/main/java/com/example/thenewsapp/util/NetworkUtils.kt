package com.example.thenewsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI

object NetworkUtils {
    fun hasInternetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(TRANSPORT_WIFI) -> true
                    hasTransport(TRANSPORT_CELLULAR) -> true
                    hasTransport(TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }
}
