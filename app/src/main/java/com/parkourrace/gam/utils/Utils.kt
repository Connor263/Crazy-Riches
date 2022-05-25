package com.parkourrace.gam.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import com.parkourrace.gam.data.game.model.Block
import kotlin.random.Random


fun generateBlock(): Block {
    val rand = Random(System.nanoTime())
    return Block(
        drawableId = 0, type = when ((0..15).random(rand)) {
            in 0..3 -> 0
            in 3..6 -> 1
            in 7..10 -> 2
            in 11..12 -> 3
            in 12..14 -> 4
            else -> 0
        }
    )
}

fun crazyRichesCheckInternet(context: Context): Boolean {
    val crazyConnectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    val crazyActiveNetwork = crazyConnectivityManager?.activeNetwork ?: return false
    val crazyNetworkCapabilities =
        crazyConnectivityManager.getNetworkCapabilities(crazyActiveNetwork) ?: return false
    return when {
        crazyNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        crazyNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        crazyNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun String.crazyRichesVigenere(encrypt: Boolean = false): String {
    val afdwafas = StringBuilder()
    val adsgbas = "comparkourracegam"
    var ddfbsqlbl = 0

    this.forEach {
        if (it !in 'a'..'z') {
            afdwafas.append(it)
            return@forEach
        }
        val wlfgflga = if (encrypt) {
            (it.code + adsgbas[ddfbsqlbl].code - 90) % 26
        } else {
            (it.code - adsgbas[ddfbsqlbl].code + 26) % 26
        }
        ddfbsqlbl++
        if (ddfbsqlbl > adsgbas.length - 1) ddfbsqlbl = 0
        afdwafas.append(wlfgflga.plus(97).toChar())
    }
    return afdwafas.toString()
}