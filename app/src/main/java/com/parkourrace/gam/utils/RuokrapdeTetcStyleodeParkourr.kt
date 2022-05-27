package com.parkourrace.gam.utils


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