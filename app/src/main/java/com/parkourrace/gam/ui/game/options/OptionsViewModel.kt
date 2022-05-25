package com.parkourrace.gam.ui.game.options

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkourrace.gam.data.game.preferences.GamePreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OptionsViewModel : ViewModel() {
    val getOption: (Context) -> Flow<Int> = {
        GamePreferencesDataStore(it).level
    }

    fun saveOption( context: Context,value: Int) = viewModelScope.launch {
        GamePreferencesDataStore(context).saveLevel(value)
    }
}