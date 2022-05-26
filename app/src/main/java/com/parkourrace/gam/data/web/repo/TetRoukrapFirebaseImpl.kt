package com.parkourrace.gam.data.web.repo

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.parkourrace.gam.R
import com.parkourrace.gam.interfaces.TetRoukrapFirebase

class TetRoukrapFirebaseImpl(private val context: Context) : TetRoukrapFirebase {
    var tetRoukrapINSTANCE: FirebaseRemoteConfig? = null
        get() {
            return if (field == null) {
                FirebaseApp.initializeApp(context)
                val tetRoukrapSettings =
                    FirebaseRemoteConfigSettings.Builder().setFetchTimeoutInSeconds(2500).build()
                field = FirebaseRemoteConfig.getInstance().apply {
                    setConfigSettingsAsync(tetRoukrapSettings)
                }
                field
            } else field
        }

    override fun getTetRoukrapUrl(callback: (String) -> Unit) {
        tetRoukrapINSTANCE?.fetchAndActivate()?.addOnCompleteListener {
            callback(
                tetRoukrapINSTANCE!!.getString(
                    context.resources.getString(R.string.firebase_root_url)
                )
            )
        }
    }

    override fun getTetRoukrapSwitch(callback: (Boolean) -> Unit) {
        tetRoukrapINSTANCE?.fetchAndActivate()?.addOnCompleteListener {
            callback(
                tetRoukrapINSTANCE!!.getBoolean(
                    context.resources.getString(R.string.firebase_switch)
                )
            )
        }
    }
}