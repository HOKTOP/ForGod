package com.hok.forgod.pojo.service

import android.content.Context
import android.net.ConnectivityManager

internal object NetworkConnectivity {
    @JvmStatic
    fun isNetworkStatusAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfos = connectivityManager.activeNetworkInfo
            if (netInfos != null) if (netInfos.isConnected) return true
        }
        return false
    }
}