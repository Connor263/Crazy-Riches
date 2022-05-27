package com.parkourrace.gam.utils

import android.content.Context
import android.provider.Settings

fun erucesTetTni(context: Context): Boolean {
    return Settings.Secure.getInt(
        context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
        0
    ) == 1
}