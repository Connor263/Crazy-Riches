package com.parkourrace.gam.data.game.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.parkourrace.gam.utils.CRAZY_LINK_PREFERENCES_DATA_STORE
import kotlinx.coroutines.flow.map

class GamePreferencesDataStore(private val context: Context) {
    val level
        get() = context.gamePreferences.data.map { preferences ->
            preferences[levelKey] ?: 1
        }

    val font
        get() = context.gamePreferences.data.map { preferences ->
            preferences[fontFamilyKey] ?: 0
        }

    suspend fun saveLevel(value: Int) {
        context.gamePreferences.edit { preferences ->
            preferences[levelKey] = value
        }
    }

    suspend fun saveFont(value: Int) {
        context.gamePreferences.edit { preferences ->
            preferences[fontFamilyKey] = value
        }
    }

    companion object {
        val levelKey = intPreferencesKey("Level")
        val fontFamilyKey = intPreferencesKey("Font")
        val Context.gamePreferences by preferencesDataStore(name = CRAZY_LINK_PREFERENCES_DATA_STORE)
    }
}

