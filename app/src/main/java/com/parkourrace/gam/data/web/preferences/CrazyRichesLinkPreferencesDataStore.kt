package com.parkourrace.gam.data.web.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.parkourrace.gam.utils.CRAZY_LINK_PREFERENCES_DATA_STORE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class CrazyRichesLinkPreferencesDataStore(private val context: Context) {
    val crazyRichesLink
        get() = context.crazyLinkPreferences.data.map { preferences ->
            preferences[crazyRichesLinkKey] ?: ""
        }

    suspend fun crazyRichesSaveLink(value: String) {
        context.crazyLinkPreferences.edit { preferences ->
            preferences[crazyRichesLinkKey] = value
        }
    }

    companion object {
        val crazyRichesLinkKey = stringPreferencesKey("CrazyLink")
        val Context.crazyLinkPreferences by preferencesDataStore(name = CRAZY_LINK_PREFERENCES_DATA_STORE)
    }
}