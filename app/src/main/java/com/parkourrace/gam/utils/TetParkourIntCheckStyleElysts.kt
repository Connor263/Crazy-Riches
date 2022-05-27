package com.parkourrace.gam.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat


fun sLinktetRuokrarElytsLinktetnkSubAllCheckInternet(context: Context): Boolean {
    val sLinktetRuokrarConnectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    val sLinktiveNetwork =
        sLinktetRuokrarConnectivityManager?.activeNetwork ?: return false
    val sLinktNetworkCapabilities =
        sLinktetRuokrarConnectivityManager.getNetworkCapabilities(sLinktiveNetwork)
            ?: return false
    return when {
        sLinktNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        sLinktNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        sLinktNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}