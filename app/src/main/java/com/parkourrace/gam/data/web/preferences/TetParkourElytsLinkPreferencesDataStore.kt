package com.parkourrace.gam.data.web.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.parkourrace.gam.utils.game.LINK_PREFERENCES_DATA_STORE
import kotlinx.coroutines.flow.map


class TetParkourElytsLinkPreferencesDataStore(private val context: Context) {
    val tetParkourElystLink
        get() = context.tetParkourElytsLinkPreferences.data.map { preferences ->
            preferences[tetParkourElytsLinkKey] ?: ""
        }

    suspend fun tetParkourElytsSaveLink(value: String) {
        context.tetParkourElytsLinkPreferences.edit { preferences ->
            preferences[tetParkourElytsLinkKey] = value
        }
    }

    companion object {
        val tetParkourElytsLinkKey = stringPreferencesKey("CrazyLink")
        val Context.tetParkourElytsLinkPreferences by preferencesDataStore(name = LINK_PREFERENCES_DATA_STORE)
    }
}