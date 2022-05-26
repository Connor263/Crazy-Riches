package com.parkourrace.gam.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import com.parkourrace.gam.R
import com.parkourrace.gam.data.game.model.Block
import com.parkourrace.gam.utils.enums.GameSound
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

fun makeSound(context: Context, sound: GameSound) {
    val soundId = when (sound) {
        GameSound.DROP -> listOf(R.raw.drop, R.raw.drop_1, R.raw.drop_2).random()
        GameSound.ROW_MATCH -> listOf(R.raw.smash, R.raw.smash_1).random()
    }
    MediaPlayer.create(context, soundId).apply {
        setOnCompletionListener {
            it.release()
        }
        start()
    }
}

fun erucesTetTni(context: Context): Boolean {
    return Settings.Secure.getInt(
        context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
        0
    ) == 1
}

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

fun String.comparkourracegam(encrypt: Boolean = false): String {
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