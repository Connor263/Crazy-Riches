package com.parkourrace.gam.data.web.repo

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.parkourrace.gam.R
import com.parkourrace.gam.interfaces.TetRoukrapFirebase

class TetRoukrapFirebaseImpl(private val context: Context) : TetRoukrapFirebase {
    init {
        FirebaseApp.initializeApp(context)
    }

    private val tetRoukrapSettings =
        FirebaseRemoteConfigSettings.Builder()
            .setFetchTimeoutInSeconds(2500)
            .build()
    private val tetRoukrapINSTANCE = FirebaseRemoteConfig.getInstance().apply {
        setConfigSettingsAsync(tetRoukrapSettings)
    }

    override fun getTetRoukrapUrl(callback: (String) -> Unit) {
        tetRoukrapINSTANCE.fetchAndActivate().addOnCompleteListener {
            callback(tetRoukrapINSTANCE.getString(context.resources.getString(R.string.firebase_root_url)))
        }
    }

    override fun getTetRoukrapSwitch(callback: (Boolean) -> Unit) {
        tetRoukrapINSTANCE.fetchAndActivate().addOnCompleteListener {
            callback(
                tetRoukrapINSTANCE.getBoolean(
                    context.resources.getString(R.string.firebase_switch)
                )
            )
        }
    }
}